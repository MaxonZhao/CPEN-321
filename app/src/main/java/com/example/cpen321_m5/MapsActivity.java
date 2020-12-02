package com.example.cpen321_m5;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import android.content.Intent;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.Toast;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import java.util.ArrayList;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, LocationListener {

    private final static String TAG = "MapActivity";
    private SeekBar simpleSeekBar;
    private EditText ed;
    private String search_price;
    private int price_test = 0;
    private ArrayList<String> jsonresult = new ArrayList<String>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        ed = (EditText) findViewById(R.id.search_price_edi);
        simpleSeekBar=(SeekBar)findViewById(R.id.seekBar);

        ed.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                price_test = 0;
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                price_test = 0;
            }
            @Override
            public void afterTextChanged(Editable s) {
                try{
                    search_price = String.valueOf(ed.getText());
                    price_test = Integer.parseInt(search_price);

                    if(price_test < 10000){
                        simpleSeekBar.setProgress(price_test);
                    }
                }catch (Exception ex){}

            }
        });

        final int[] i = new int[1];
        simpleSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int progressChangedValue = 0;
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                progressChangedValue = progress;
            }
            public void onStartTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
                i[0] = 0;
            }
            public void onStopTrackingTouch(SeekBar seekBar) {
                Toast.makeText(MapsActivity.this, "MAX Price :" + progressChangedValue,
                        Toast.LENGTH_SHORT).show();

                String price_enter = Integer.toString(progressChangedValue);
                ed.setText(price_enter);
                price_test = Integer.parseInt(ed.getText().toString());
                search_price = String.valueOf(price_test);
            }
        });

        backtomain();

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.nav_chat);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener(){
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId()) {
                    case R.id.nav_home:
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        overridePendingTransition(0,0);
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


    }
    public void backtomain(){

        Button btnReturn1 = (Button) findViewById(R.id.search_submit_but);
        btnReturn1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Spinner search_loc_spi;
                String search_loc;                          //location that use enter in
                Spinner search_typs_spi;
                String search_typs;                             //types that use enter in

                search_loc_spi = (Spinner)findViewById(R.id.search_loc_spi);
                search_loc = search_loc_spi.getSelectedItem().toString();
                Log.v("search/location:", search_loc);

                search_typs_spi = (Spinner)findViewById(R.id.search_types_spi);
                search_typs = search_typs_spi.getSelectedItem().toString();
                Log.v("search/types:", search_typs);

                Log.v("search/price:", String.valueOf(price_test));

                jsonresult.add(search_price);
                jsonresult.add(search_loc);
                jsonresult.add(search_typs);
                Intent mIntent = new Intent();
                mIntent.putExtra("keyName", jsonresult);
                setResult(RESULT_OK, mIntent);
                finish();

            }
        });

    }

    //public ArrayList<String> getmess(String search_loc, String search_typs) throws InterruptedException {

    //}

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
        GoogleMap mMap;
        mMap = googleMap;

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
        LatLng loc_tp = new LatLng(49.257969, -123.253107);
        LatLng loc_oc = new LatLng(49.260087, -123.251039);
        LatLng loc_ruh = new LatLng(49.259862, -123.252906);
        LatLng loc_wg = new LatLng(49.269579, -123.249641);
        LatLng loc_fc = new LatLng(49.263254, -123.239600);
        LatLng loc_md = new LatLng(49.261430, -123.256699);
        LatLng loc_fh = new LatLng(49.261550, -123.240755);
        LatLng loc_pc = new LatLng(49.263831, -123.255598);
        LatLng loc_th = new LatLng(49.259333, -123.250730);
        LatLng loc_lh = new LatLng(49.265565, -123.257267);
        LatLng loc_bcth = new LatLng(49.269507, -123.251333);
        LatLng loc_ex = new LatLng(49.268426, -123.247527);

        markandcircle(mMap, loc_pv, num_pv);
        markandcircle(mMap, loc_tp, num_tp);
        markandcircle(mMap, loc_oc, num_oc);
        markandcircle(mMap, loc_ruh, num_ruh);
        markandcircle(mMap, loc_wg, num_wg);
        markandcircle(mMap, loc_fc, num_fc);
        markandcircle(mMap, loc_md, num_md);
        markandcircle(mMap, loc_fh, num_fh);
        markandcircle(mMap, loc_pc, num_pc);
        markandcircle(mMap, loc_th, num_th);
        markandcircle(mMap, loc_lh, num_lh);
        markandcircle(mMap, loc_bcth, num_bcth);
        markandcircle(mMap, loc_ex, num_ex);



    }
    public void markandcircle(GoogleMap mMap, LatLng location, int size){

        mMap.addMarker(new MarkerOptions().position(location).title("Exchange: "+String.valueOf(size)));
        mMap.addCircle(new CircleOptions()
                .center(location)
                .radius(size)
                .strokeColor(Color.parseColor("#cee397"))
                .fillColor(Color.parseColor("#fcf876")));
    }

    @Override
    public void onLocationChanged(@NonNull Location location) {
        Log.d(TAG, "Lat: " + location.getLatitude() + "| Long: " + location.getLongitude());
    }

//    @Override
//    public void onStatusChanged(String provider, int status, Bundle extras) {
//
//    }
//
//    @Override
//    public void onProviderEnabled(@NonNull String provider) {
//
//    }
//
//    @Override
//    public void onProviderDisabled(@NonNull String provider) {
//
//    }
}