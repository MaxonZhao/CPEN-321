package com.example.cpen321_m5;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
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

        ImageView sign_in_image;

        View search_image;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recommend_list = findViewById(R.id.recomm_list);
        research_list = findViewById(R.id.search_list);
        recommend_list.setVisibility(View.VISIBLE);
        research_list.setVisibility(View.INVISIBLE);

        View recommend_txt = findViewById(R.id.recommtxt);
        View search_txt = findViewById(R.id.searchtxt);
        recommend_txt.setVisibility(View.VISIBLE);
        search_txt.setVisibility(View.INVISIBLE);

        showrecomm();

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

        token();

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

    private void token() {
        View button_retrieve_token = findViewById(R.id.button_retrieve_token);
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

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK && requestCode == REQUEST_CODE && data != null){

            recommend_list = findViewById(R.id.recomm_list);
            research_list = findViewById(R.id.search_list);
            recommend_list.setVisibility(View.INVISIBLE);
            research_list.setVisibility(View.VISIBLE);

            View recommend_txt = findViewById(R.id.recommtxt);
            View search_txt = findViewById(R.id.searchtxt);
            recommend_txt.setVisibility(View.INVISIBLE);
            search_txt.setVisibility(View.VISIBLE);

            searchlistItem.clear();

            ArrayList<String> strMessage = data.getStringArrayListExtra("keyName");
            Log.i(TAG , "Search Result >>" + strMessage);

            showsearch(strMessage);

        }
    }

    public void showsearch(ArrayList<String> strMessage){
        String url = "http:40.87.45.133:3000/search";

        Date start = Calendar.getInstance().getTime();
        long start_ms = start.getTime();

        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);

        requestQueue.add(searchsend(strMessage, url));
        //.......................................................................................................

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url, response -> {
            Log.i("search/return", "success in respones");
            try {
                Log.i("search/return", "success get the array");

                Date end = Calendar.getInstance().getTime();
                long end_ms = end.getTime();
                long durlation= end_ms - start_ms;
                System.out.println("time consume: " + durlation);

                if(response.length() == 0){
                    Log.i("search/return length", String.valueOf(response.length()));

                    HashMap<String,Object> map = new HashMap<String,Object>();
                    map.put("price", 404);
                    map.put("location", "404");
                    map.put("types", "404");
                    map.put("phone", "404");
                    map.put("email", "404");
                    searchlistItem.add(map);
                }
                else{

                    Log.i("search/return length", String.valueOf(response.length()));
                    for (int i = 0; i < response.length(); i++) {
                        JSONObject jb = response.getJSONObject(i);
                        Log.i("search/one of result",jb.toString());

                        HashMap<String,Object> map = new HashMap<String,Object>();

                        map.put("price", jb.getInt("price"));
                        map.put("location", jb.getString("location"));
                        map.put("types", jb.getString("types"));
                        map.put("phone", jb.getString("phone"));
                        map.put("email", jb.getString("email"));

                        String store = jb.getString("image");
                        byte[] decodedString = Base64.decode(store, Base64.DEFAULT);
                        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                        map.put("image", decodedByte);

                        searchlistItem.add(map);
                    }
                    SimpleAdapter mSimpleAdapter = new SimpleAdapter(this, searchlistItem, R.layout.layout,
                            new String[] {"price","location", "types","phone","email", "image"},
                            new int[] {R.id.price,R.id.location,R.id.types,R.id.phone,R.id.email, R.id.image});

                    ListView list= (ListView) findViewById(R.id.search_list);

                    mSimpleAdapter.setViewBinder(new SimpleAdapter.ViewBinder() {
                        @Override
                        public boolean setViewValue(View view, Object data, String textRepresentation) {
                            if(view instanceof ImageView && data instanceof Bitmap){
                                ImageView i = (ImageView)view;
                                i.setImageBitmap((Bitmap) data);
                                return true;
                            }
                            return false;
                        }
                    });
                    list.setAdapter(mSimpleAdapter);
                }

            } catch (JSONException e) {
                Log.i("search/return", "unsuccess get the array");
                e.printStackTrace();
            }
        }, error -> Log.e("search", error.toString()));

        jsonArrayRequest.setRetryPolicy(new DefaultRetryPolicy(500000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(jsonArrayRequest);

    }

    private JsonObjectRequest searchsend(ArrayList<String> strMessage, String url) {

        JSONObject postData = new JSONObject();
        try {
            Log.i("search/send", strMessage.get(0));
            Log.i("search/send", strMessage.get(1));
            Log.i("search/send", strMessage.get(2));

            postData.put("price", Integer.valueOf(strMessage.get(0)));
            postData.put("location", strMessage.get(1));
            postData.put("types", strMessage.get(2));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, postData, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                System.out.println(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        return jsonObjectRequest;

    }

    public void showrecomm(){
        String url = "http:40.87.45.133:3000/logic";

        Date start = Calendar.getInstance().getTime();
        long start_ms = start.getTime();

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

                        HashMap<String,Object> map = new HashMap<String,Object>();
                        map.put("price", jb.getInt("price"));
                        map.put("location", jb.getString("location"));
                        map.put("types", jb.getString("types"));
                        map.put("phone", jb.getString("phone"));
                        map.put("email", jb.getString("email"));

                        try {
                            String store = jb.getString("image");
                            byte[] decodedString = Base64.decode(store, Base64.DEFAULT);
                            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                            map.put("image", decodedByte);
                        } catch (JSONException e) {
                            map.put("image", null);
                        }
                        recommlistItem.add(map);
                    }
                    SimpleAdapter mSimpleAdapter = new SimpleAdapter(this, recommlistItem, R.layout.layout,
                            new String[] {"price","location", "types","phone","email", "image"},
                            new int[] {R.id.price,R.id.location,R.id.types,R.id.phone,R.id.email, R.id.image});

                    ListView list= (ListView) findViewById(R.id.recomm_list);
                    mSimpleAdapter.setViewBinder(new SimpleAdapter.ViewBinder() {
                        @Override
                        public boolean setViewValue(View view, Object data, String textRepresentation) {
                            if(view instanceof ImageView && data instanceof Bitmap){
                                ImageView i = (ImageView)view;
                                i.setImageBitmap((Bitmap) data);
                                return true;
                            }
                            return false;

                        }
                    });
                    list.setAdapter(mSimpleAdapter);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> Log.e("recomm", error.toString()));
        jsonArrayRequest.setRetryPolicy(new DefaultRetryPolicy(5000000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(jsonArrayRequest);
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