package com.example.cpen321_m5;

import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, LocationListener {


//    private LocationManager locationManager;
    final static String TAG = "MapActivity";

    //.......................
    private SeekBar simpleSeekBar;
    private EditText ed;

    private String search_price;
    private Spinner search_loc_spi;
    private String search_loc;                          //location that use enter in
    private Spinner search_typs_spi;
    private String search_typs;                             //types that use enter in


    int price_test;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

//        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
//        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            // TODO: Consider calling
//            //    ActivityCompat#requestPermissions
//            // here to request the missing permissions, and then overriding
//            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//            //                                          int[] grantResults)
//            // to handle the case where the user grants the permission. See the documentation
//            // for ActivityCompat#requestPermissions for more details.
//            return;
//        }
//        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 0, this);


        //.....................................

        ed = (EditText) findViewById(R.id.search_price_edi);
        simpleSeekBar=(SeekBar)findViewById(R.id.seekBar);

        ed.setOnClickListener(new EditText.OnClickListener(){

            @Override
            public void onClick(View v) {
                price_test = Integer.parseInt(ed.getText().toString());

                search_price = String.valueOf(price_test);



                if(price_test <= 10000){
                    simpleSeekBar.setProgress(price_test);
                }
            }
        });


        simpleSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int progressChangedValue = 0;

            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                progressChangedValue = progress;
            }

            public void onStartTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }

            public void onStopTrackingTouch(SeekBar seekBar) {
                Toast.makeText(MapsActivity.this, "MAX Price :" + progressChangedValue,
                        Toast.LENGTH_SHORT).show();

                String price_enter = Integer.toString(progressChangedValue);
                ed.setText(price_enter);
            }
        });

        Button btnReturn1 = (Button) findViewById(R.id.search_submit_but);
        btnReturn1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // TODO Auto-generated method stub
//                Intent returnBtn = new Intent("Mainactivity");
//                startActivity(returnBtn);


                search_loc_spi = (Spinner)findViewById(R.id.search_loc_spi);
                search_loc = search_loc_spi.getSelectedItem().toString();
                Log.v("search/location:", search_loc);

                search_typs_spi = (Spinner)findViewById(R.id.search_types_spi);
                search_typs = search_typs_spi.getSelectedItem().toString();
                Log.v("search/types:", search_typs);

                Log.v("search/price:", search_price);


                String url = "http:20.185.220.227:3000/price";
                //List<String> jsonResponses = new ArrayList<>();

                //RequestQueue requestQueue = Volley.newRequestQueue(MapsActivity.this);


//                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
//                    @Override
//                    public void onResponse(JSONObject response) {
//                        try {
//
//                            String result = response.getString("_id");
//                            System.out.println("sdhjfvjhvbf   " + result);
//
//
//                            JSONArray jsonArray = response.getJSONArray("Value");
//                            for(int i = 0; i < jsonArray.length(); i++){
//                                JSONObject jsonObject = jsonArray.getJSONObject(i);
//                                String email = jsonObject.getString("_id");
//
//                                System.out.println("__________******__________" + email);
//
//                                jsonResponses.add(email);
//                            }
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                }, new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        error.printStackTrace();
//                    }
//                });

                //requestQueue.add(jsonArrayRequest);



                JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url,  new Response.Listener < JSONArray > () {

                    @Override
                    public void onResponse(JSONArray response) {

                        System.out.println("success in respones");
                        try {

                            System.out.println("success get the array");
                            for (int i = 0; i < response.length(); i++) {


                                JSONObject jb = response.getJSONObject(i);
                                String id = jb.getString("_id");
                                System.out.println(id);


                                Toast.makeText(MapsActivity.this, "Num" + i +":"+"Post ID number is: "+ id,
                                        Toast.LENGTH_SHORT).show();

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("research", error.toString());
                    }
                });



                jsonArrayRequest.setRetryPolicy(new DefaultRetryPolicy(500000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

                RequestQueue requestQueue = Volley.newRequestQueue(MapsActivity.this);
                requestQueue.add(jsonArrayRequest);





                //finish();




            }
        });

        //.........................................................................

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);

        // set Home Selected
        bottomNavigationView.setSelectedItemId(R.id.nav_chat);

        // perform ItemSelectedListener
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener(){

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId()) {
                    case R.id.nav_home:
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.nav_chat:
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


    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        GoogleMap mMap = googleMap;

        // Add a marker in Sydney and move the camera
        //LatLng my_home = new LatLng(24, 109);
        //mMap.addMarker(new MarkerOptions().position(my_home).title("My home: Liuzhou"));
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(my_home));
        LatLng vancouver = new LatLng(49.264365, -123.243392);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(vancouver, 14));



        int num_pv = 50;
        int num_tp = 145;
        int num_oc = 80;
        int num_ruh = 54;
        int num_wg = 67;
        int num_fc = 40;
        int num_md = 80;
        int num_fh = 10;
        int num_pc = 4;
        int num_th = 88;
        int num_lh = 45;
        int num_bcth = 93;
        int num_ex = 258;

        LatLng loc_pv = new LatLng(49.264746, -123.258670);
        mMap.addMarker(new MarkerOptions().position(loc_pv).title("Place Vanier: "+String.valueOf(num_pv)));
        LatLng loc_tp = new LatLng(49.257969, -123.253107);
        mMap.addMarker(new MarkerOptions().position(loc_tp).title("Totem Park: "+String.valueOf(num_tp)));
        LatLng loc_oc = new LatLng(49.260087, -123.251039);
        mMap.addMarker(new MarkerOptions().position(loc_oc).title("Orchard Commons: "+String.valueOf(num_oc)));
        LatLng loc_ruh = new LatLng(49.259862, -123.252906);
        mMap.addMarker(new MarkerOptions().position(loc_ruh).title("Ritsumeikan-UBC House: "+String.valueOf(num_ruh)));
        LatLng loc_wg = new LatLng(49.269579, -123.249641);
        mMap.addMarker(new MarkerOptions().position(loc_wg).title("Walter Gage: "+String.valueOf(num_wg)));
        LatLng loc_fc = new LatLng(49.263254, -123.239600);
        mMap.addMarker(new MarkerOptions().position(loc_fc).title("Fairview Crescent: "+String.valueOf(num_fc)));
        LatLng loc_md = new LatLng(49.261430, -123.256699);
        mMap.addMarker(new MarkerOptions().position(loc_md).title("Marine Driver: "+String.valueOf(num_md)));
        LatLng loc_fh = new LatLng(49.261550, -123.240755);
        mMap.addMarker(new MarkerOptions().position(loc_fh).title("Fraser Hall: "+String.valueOf(num_fh)));
        LatLng loc_pc = new LatLng(49.263831, -123.255598);
        mMap.addMarker(new MarkerOptions().position(loc_pc).title("Ponderosa Commons: "+String.valueOf(num_pc)));
        LatLng loc_th = new LatLng(49.259333, -123.250730);
        mMap.addMarker(new MarkerOptions().position(loc_th).title("Thunderbird: "+String.valueOf(num_th)));
        LatLng loc_lh = new LatLng(49.265565, -123.257267);
        mMap.addMarker(new MarkerOptions().position(loc_lh).title("lona House: "+String.valueOf(num_lh)));
        LatLng loc_bcth = new LatLng(49.269507, -123.251333);
        mMap.addMarker(new MarkerOptions().position(loc_bcth).title("Brock Commons - Tallwood House: "+String.valueOf(num_bcth)));
        LatLng loc_ex = new LatLng(49.268426, -123.247527);
        mMap.addMarker(new MarkerOptions().position(loc_ex).title("Exchange: "+String.valueOf(num_ex)));



//        Circle Place_Vanier = mMap.addCircle(new CircleOptions()
//                .center(loc_pv)
//                .radius(num_pv)
//                .strokeColor(Color.parseColor("#cee397"))
//                .fillColor(Color.parseColor("#fcf876")));
//
//        Circle Totem_Park = mMap.addCircle(new CircleOptions()
//                .center(loc_tp)
//                .radius(num_tp)
//                .strokeColor(Color.parseColor("#cee397"))
//                .fillColor(Color.parseColor("#fcf876"))
//        );
//        Circle Orchard_Commons = mMap.addCircle(new CircleOptions()
//                .center(loc_oc)
//                .radius(num_oc)
//                .strokeColor(Color.parseColor("#cee397"))
//                .fillColor(Color.parseColor("#fcf876"))
//        );
//        Circle Ritsumeikan_UBC_House = mMap.addCircle(new CircleOptions()
//                .center(loc_ruh)
//                .radius(num_ruh)
//                .strokeColor(Color.parseColor("#cee397"))
//                .fillColor(Color.parseColor("#fcf876"))
//        );
//        Circle Walter_Gage = mMap.addCircle(new CircleOptions()
//                .center(loc_wg)
//                .radius(num_wg)
//                .strokeColor(Color.parseColor("#cee397"))
//                .fillColor(Color.parseColor("#fcf876"))
//        );
//        Circle Fairview_Crescent = mMap.addCircle(new CircleOptions()
//                .center(loc_fc)
//                .radius(num_fc)
//                .strokeColor(Color.parseColor("#cee397"))
//                .fillColor(Color.parseColor("#fcf876"))
//        );
//        Circle Marine_Driver = mMap.addCircle(new CircleOptions()
//                .center(loc_md)
//                .radius(num_md)
//                .strokeColor(Color.parseColor("#cee397"))
//                .fillColor(Color.parseColor("#fcf876"))
//        );
//        Circle Fraser_Hall = mMap.addCircle(new CircleOptions()
//                .center(loc_fh)
//                .radius(num_fh)
//                .strokeColor(Color.parseColor("#cee397"))
//                .fillColor(Color.parseColor("#fcf876"))
//        );
//        Circle Ponderosa_Commons = mMap.addCircle(new CircleOptions()
//                .center(loc_pc)
//                .radius(num_pc)
//                .strokeColor(Color.parseColor("#cee397"))
//                .fillColor(Color.parseColor("#fcf876"))
//        );
//        Circle Thunderbird = mMap.addCircle(new CircleOptions()
//                .center(loc_th)
//                .radius(num_th)
//                .strokeColor(Color.parseColor("#cee397"))
//                .fillColor(Color.parseColor("#fcf876"))
//        );
//        Circle lona_House = mMap.addCircle(new CircleOptions()
//                .center(loc_lh)
//                .radius(num_lh)
//                .strokeColor(Color.parseColor("#cee397"))
//                .fillColor(Color.parseColor("#fcf876"))
//        );
//        Circle Brock_Commons_Tallwood_House = mMap.addCircle(new CircleOptions()
//                .center(loc_bcth)
//                .radius(num_bcth)
//                .strokeColor(Color.parseColor("#cee397"))
//                .fillColor(Color.parseColor("#fcf876"))
//        );
//        Circle Exchange = mMap.addCircle(new CircleOptions()
//                .center(loc_ex)
//                .radius(num_ex)
//                .strokeColor(Color.parseColor("#cee397"))
//                .fillColor(Color.parseColor("#fcf876"))
//        );

    }

    @Override
    public void onLocationChanged(@NonNull Location location) {
        Log.d(TAG, "Lat: " + location.getLatitude() + "| Long: " + location.getLongitude());
    }

}