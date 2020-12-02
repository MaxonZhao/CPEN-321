package com.example.cpen321_m5;
import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class Post extends AppCompatActivity {
    private int IMAGE_REQUEST_ID = 1;
    private Bitmap image;
    private String notification_token;
    private static final String TAG = "POST";
    private ImageView img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        View pickImg_Button;

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

        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "getInstanceId failed", task.getException());
                            return;
                        }
                        // 3
                        notification_token = task.getResult().getToken();

                        Log.d(TAG, "TOKEN --------------------->"  + notification_token);
                    }
                });

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED)
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 10);

        backtomain();

        pickImg_Button = findViewById(R.id.pickImg_post);
        pickImg_Button.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("image/*");

            startActivityForResult(Intent.createChooser(intent, "Pick image"), IMAGE_REQUEST_ID);
        });
    }

    public void backtomain(){
        Button submit = (Button) findViewById(R.id.submit);
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

                String postUrl = "http:40.87.45.133:3000";
                RequestQueue requestQueue = Volley.newRequestQueue(Post.this);
                JSONObject postData = new JSONObject();
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                image.compress(Bitmap.CompressFormat.JPEG, 50, outputStream);

                String base64String = Base64.encodeToString(outputStream.toByteArray(),
                        Base64.DEFAULT);

                try {
                    postData.put("price", Integer.valueOf(pricestring));
                    postData.put("location", retspinner(R.id.location_spi));
                    postData.put("types", retspinner(R.id.types_spi));
                    postData.put("phone", rettext(R.id.editphone));
                    postData.put("email", rettext(R.id.editemail));
                    postData.put("descript", rettext(R.id.editdescrip));
                    postData.put("image", base64String);
                    postData.put("token", notification_token);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data){
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == IMAGE_REQUEST_ID && resultCode == RESULT_OK) {

            try {
                InputStream is = getContentResolver().openInputStream(data.getData());
                Bitmap image = BitmapFactory.decodeStream(is);

                this.image = image;
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                image.compress(Bitmap.CompressFormat.JPEG, 50, outputStream);

                String base64String = Base64.encodeToString(outputStream.toByteArray(),
                        Base64.DEFAULT);

                img = findViewById(R.id.imageView_post);
                byte[] decodedString = Base64.decode(base64String, Base64.DEFAULT);
                Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                img.setImageBitmap(decodedByte);

                //Log.d("TAG", base64String);

            } catch (FileNotFoundException e) {
                //Log.d("TAG", "LINE 155 --------------------------->");
                e.printStackTrace();
            }

        }
    }



    public String retspinner(int id_number){
        Spinner goal_spi = (Spinner) findViewById(id_number);
        String resultstring = goal_spi.getSelectedItem().toString();
        Log.v("post", resultstring);
        return resultstring;
    }

    public String rettext(int id_number){
        TextInputLayout goal_til = (TextInputLayout) findViewById(id_number);
        String resultstring = goal_til.getEditText().getText().toString();
        Log.v("post", resultstring);
        return resultstring;
    }
}