package com.example.cpen321_m5;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONException;
import org.json.JSONObject;

public class Post extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Button submit;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);
        // initialize And Assign Variable
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        // set Home Selected
        bottomNavigationView.setSelectedItemId(R.id.nav_post);
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
                        startActivity(new Intent(getApplicationContext(), Chat.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.nav_post:
                        return true;
                    default:
                        return false;
                }
            }
        });

        //......................................................................................

        submit = (Button) findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                EditText priceedit;
                String pricestring;                             //price that use enter in

                // TODO Auto-generated method stub
                // Intent returnBtn = new Intent("Mainactivity");
                // startActivity(returnBtn);
                priceedit   = (EditText)findViewById(R.id.editprice);
                pricestring = priceedit.getText().toString();
                Log.v("post/price", pricestring);

                String postUrl = "http:40.76.20.105:3000";
                RequestQueue requestQueue = Volley.newRequestQueue(Post.this);
                JSONObject postData = new JSONObject();

                try {
                    postData.put("price", Integer.valueOf(pricestring));
                    postData.put("location", ret_spinner_val(R.id.location_spi));
                    postData.put("types", ret_spinner_val(R.id.types_spi));
                    postData.put("phone", ret_text_val(R.id.editphone));
                    postData.put("email", ret_text_val(R.id.editemail));
                    postData.put("descript", ret_text_val(R.id.editdescrip));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, postUrl, postData, new Response.Listener<JSONObject>() {
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
                requestQueue.add(jsonObjectRequest);

                finish();
            }
        });
    }


    public String ret_spinner_val(int id_number){
        Spinner goal_spi = (Spinner) findViewById(id_number);
        String resultstring = goal_spi.getSelectedItem().toString();
        Log.v("post", resultstring);
        return resultstring;
    }

    public String ret_text_val(int id_number){
        TextInputLayout goal_til = (TextInputLayout) findViewById(id_number);
        String resultstring = goal_til.getEditText().getText().toString();
        Log.v("post", resultstring);
        return resultstring;
    }
}