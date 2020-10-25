package com.example.cpen321_m5;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import android.content.IntentFilter;

import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.BreakIterator;

import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity {
    private Button locationButton;
    private Button mapsButton;
    private Button timeButton;
    private Button button_retrieve_token;
    private ImageView sign_in_image;

    private int RC_SIGN_IN = 1;
    final static String TAG = "MainActivity";
    static final private String request_time_URL = "https://timedisplayer.azurewebsites.net/time";
    private TextView TimeTextView;
    private TextView text_view_notification;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver, new IntentFilter("MyData"));

        // initialize And Assign Variable
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);

        // set Home Selected
        bottomNavigationView.setSelectedItemId(R.id.nav_home);

        // perform ItemSelectedListener
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener(){

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId()) {
                    case R.id.nav_home:
                        return true;
                    case R.id.nav_chat:
                        startActivity(new Intent(getApplicationContext(), Chat.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.nav_post:
                        startActivity(new Intent(getApplicationContext(), Post.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });

        button_retrieve_token = findViewById(R.id.button_retrieve_token);
        if (checkGooglePlayServices()) {
            button_retrieve_token.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FirebaseInstanceId.getInstance().getInstanceId()
                            .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                                @Override
                                public void onComplete(@NonNull Task<InstanceIdResult> task) {
                                    if (!task.isSuccessful()) {
                                        Log.w(TAG, "getInstanceId failed", task.getException());
                                        return;
                                    }
                                    // 3
                                    String token = task.getResult().getToken();
                                    // 4
//                               val msg = getString(R.string.token_prefix, token)
                                    Log.d(TAG, token);
                                    Toast.makeText(MainActivity.this, token, Toast.LENGTH_LONG).show();
                                }
                            });
                }
            });
        } else {
            // you won't be able to send notifications to this device
            Log.w(TAG, "Device doesn't have google play services");
        }

        locationButton = findViewById(R.id.location_button);
        locationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkLocationPermission();
                Log.d(TAG, "Trying to request location permissions");
                Toast.makeText(MainActivity.this,"Trying to request location permissions", Toast.LENGTH_LONG).show();

            }
        });
        mapsButton = findViewById(R.id.maps_button);
        mapsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "Trying to open google maps");
                Intent mapsIntent = new Intent(MainActivity.this, MapsActivity.class);
                startActivity(mapsIntent);
            }
        });



        timeButton = findViewById(R.id.request_time_button);
        timeButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                requestTime(request_time_URL);
            }
        });

        TimeTextView = findViewById(R.id.time_displayer);

        sign_in_image = findViewById(R.id.sign_in_image);
        sign_in_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Signin_activity.class);
                startActivity(intent);
            }
        });


    }


    private boolean checkGooglePlayServices() {
        // 1
        int status = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(this);
        // 2
        if (status != ConnectionResult.SUCCESS) {
            Log.e(TAG, "Error");
            // ask user to update google play services and manage the error.
            return false;
        } else {
            // 3
            Log.i(TAG, "Google play services updated");
            return true;
        }
    }


    private void requestTime(String url) {
        Log.d(TAG, "requesting Time");
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(url, new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

                            String time = new String(responseBody);
                            Log.d(TAG, time);
                            TimeTextView.setText(time);
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                        Log.d(TAG, "Request fail! Status code: " + statusCode);
                        Log.d(TAG, "Fail response: " + new String(responseBody));
                        Log.d(TAG, error.toString());

                        Toast.makeText(MainActivity.this, "Request Failed",
                                Toast.LENGTH_SHORT).show();
                    }
                });
                /* reference
                 1.https://stackoverflow.com/questions/36912389/converting-byte-to-jsonobject
                 2.https://blog.hyperiondev.com/index.php/2019/02/18/android-http/
                 */

    }

    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String message = intent.getStringExtra("message");
            Log.d("receiver", "Got message: ???? " + message);
            Bundle bundle;
            text_view_notification = findViewById(R.id.text_view_notification);
            text_view_notification.setText(message);
        }
    };



    private void updateUI(GoogleSignInAccount account) {
        if (account == null) {
            Log.d(TAG, "There is no user signed in!");
        } else {
            Log.d(TAG, "Pref Name: " + account.getDisplayName());
            Log.d(TAG, "Email Name: " + account.getEmail());
            Log.d(TAG, "Given Name: " + account.getGivenName());
            Log.d(TAG, "Family Name: " + account.getFamilyName());
            Log.d(TAG, "Display URL: " + account.getPhotoUrl());

            // Send token to your back-end
            account.getIdToken();
            // Move to another activity
        }
    }

    private void checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
        && ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(MainActivity.this,"We have these permissions yay!", Toast.LENGTH_LONG).show();
            return;
        } else {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                    || ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_COARSE_LOCATION)) {
                Toast.makeText(MainActivity.this,"We need these location permissions to run", Toast.LENGTH_LONG).show();
                new AlertDialog.Builder(this)
                        .setTitle("Need Location Permissions")
                        .setMessage("We need the location permissions to mark your location on a map")
                        .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(MainActivity.this,"We need these location permissions to run", Toast.LENGTH_LONG).show();
                                DialogInterface dialogInterface;
//                                dialogInterface.dismiss();
                            }
                        })
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                ActivityCompat.requestPermissions(MainActivity.this, new String[] {Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION}, 1);
                            }
                        })
                        .create()
                        .show();


            } else {
                ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            }
        }
    }



    @Override
    public void onStart() {
        super.onStart();
    }
    @Override
    public void onStop() {
        super.onStop();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mMessageReceiver);
    }
}