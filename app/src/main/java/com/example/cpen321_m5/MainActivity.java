package com.example.cpen321_m5;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    private final static String TAG = "MainActivity";
    private static final int REQUEST_CODE = 101;
    private View recommend_list;
    private View research_list;


    //private ArrayList<String> recommend_result = new ArrayList<String>();
    private ArrayList<HashMap<String, Object>> recommlistItem = new ArrayList<HashMap<String,Object>>();
    private ArrayList<HashMap<String, Object>> searchlistItem = new ArrayList<HashMap<String,Object>>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Button button_retrieve_token;
        ImageView sign_in_image;
        //........................
        View search_image;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);







        //................................


        recommend_list = findViewById(R.id.recomm_list);
        research_list = findViewById(R.id.search_list);
        recommend_list.setVisibility(View.VISIBLE);
        research_list.setVisibility(View.INVISIBLE);

        View recommend_txt = findViewById(R.id.recommtxt);
        View search_txt = findViewById(R.id.searchtxt);
        recommend_txt.setVisibility(View.VISIBLE);
        search_txt.setVisibility(View.INVISIBLE);


        Date start = Calendar.getInstance().getTime();
        long start_ms = start.getTime();

        String url = "http:40.87.45.133:3000/logic";
        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url, response -> {
            Log.i("recomm/return", "success in respones");
            try {
                Log.i("recomm/return", "success get the array");
                if(response.length() == 0){

                    Log.i("recomm/return length", String.valueOf(response.length()));
                }
                else{
                    Date end = Calendar.getInstance().getTime();
                    long end_ms = end.getTime();
                    long durlation= end_ms - start_ms;
                    System.out.println("time consume: " + durlation);

                    Log.i("recomm/return length", String.valueOf(response.length()));
                    recommlistItem.clear();

                    for (int i = 0; i < response.length(); i++) {
                        JSONObject jb = response.getJSONObject(i);
                        Log.i("recomm/one of result",jb.toString());

                        //recommend_result.add(jb.toString());
                        //System.out.println("size of recommresult");
                        //System.out.println(recommend_result.size());


                        HashMap<String,Object> map = new HashMap<String,Object>();
                        map.put("price", jb.getInt("price"));

                        //"price":3421,"location":"Place Vanier","types":"Shared Room","phone":"","email":"","descript":""
                        map.put("location", jb.getString("location"));
                        map.put("types", jb.getString("types"));
                        map.put("phone", jb.getString("phone"));
                        map.put("email", jb.getString("email"));
                        //map.put("descript", jb.getString("descript"));
                        recommlistItem.add(map);

                    }



                    SimpleAdapter mSimpleAdapter = new SimpleAdapter(this, recommlistItem, R.layout.layout,
                            new String[] {"price","location", "types","phone","email"},
                            new int[] {R.id.price,R.id.location,R.id.types,R.id.phone,R.id.email});

                    ListView list= (ListView) findViewById(R.id.recomm_list);
                    list.setAdapter(mSimpleAdapter);

                }


            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> Log.e("recomm", error.toString()));
        jsonArrayRequest.setRetryPolicy(new DefaultRetryPolicy(5000000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(jsonArrayRequest);


    //................................

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
                    default:
                        return false;
                }
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
                                    //TODO: send token to backend
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

        search_image = findViewById(R.id.search_image);
        search_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "Trying to open search page");
                Intent mapsIntent = new Intent(MainActivity.this, MapsActivity.class);
                startActivityForResult(mapsIntent, REQUEST_CODE);
            }
        });

        sign_in_image = findViewById(R.id.sign_in_image);
        sign_in_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Signingactivity.class);
                startActivity(intent);
            }
        });

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK && requestCode == REQUEST_CODE && data != null){
            //String strMessage = data.getStringExtra("keyName");
            ArrayList<String> strMessage = data.getStringArrayListExtra("keyName");
            Log.i(TAG , "Search Result >>" + strMessage);
            //System.out.println(strMessage);
            recommend_list = findViewById(R.id.recomm_list);
            research_list = findViewById(R.id.search_list);
            recommend_list.setVisibility(View.INVISIBLE);
            research_list.setVisibility(View.VISIBLE);

            View recommend_txt = findViewById(R.id.recommtxt);
            View search_txt = findViewById(R.id.searchtxt);
            recommend_txt.setVisibility(View.INVISIBLE);
            search_txt.setVisibility(View.VISIBLE);

            searchlistItem.clear();

            if(strMessage.size() !=0 ){
                for(int i = 0; i < strMessage.size(); i++){

                    JSONObject jb = null;
                    try {
                        jb = new JSONObject(strMessage.get(i));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    System.out.println(jb);

                    HashMap<String,Object> map = new HashMap<String,Object>();
                    try {
                        map.put("price", jb.getInt("price"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    try {
                        map.put("location", jb.getString("location"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    try {
                        map.put("types", jb.getString("types"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    try {
                        map.put("phone", jb.getString("phone"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    try {
                        map.put("email", jb.getString("email"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    //map.put("descript", jb.getString("descript"));
                    searchlistItem.add(map);
                }
            }
            else{
                HashMap<String,Object> map = new HashMap<String,Object>();
                map.put("price", 404);
                map.put("location", "404");
                map.put("types", "404");
                map.put("phone", "404");
                map.put("email", "404");
                searchlistItem.add(map);

            }

            SimpleAdapter mSimpleAdapter = new SimpleAdapter(this, searchlistItem, R.layout.layout,
                    new String[] {"price","location", "types","phone","email"},
                    new int[] {R.id.price,R.id.location,R.id.types,R.id.phone,R.id.email});

            ListView list= (ListView) findViewById(R.id.search_list);
            list.setAdapter(mSimpleAdapter);





        }
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


    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            TextView text_view_notification;

            String message = intent.getStringExtra("message");
            text_view_notification = findViewById(R.id.text_view_notification);
            text_view_notification.setText(message);
        }
    };

    @Override
    public void onStop() {
        super.onStop();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mMessageReceiver);
    }
}