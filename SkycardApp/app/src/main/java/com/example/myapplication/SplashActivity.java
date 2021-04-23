package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class SplashActivity extends AppCompatActivity {

    //permissions
    String permissionCamera = Manifest.permission_group.CAMERA;
    String permissionMedia = Manifest.permission_group.STORAGE;
    String permisionSMS = Manifest.permission_group.SMS;
    String permisssionContacts = Manifest.permission_group.CONTACTS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
        String token = pref.getString("token", "");

        if (token.equals("")) {
            // open login
        } else {
            startApp();
        }
    }

    private void startApp() {
        String url = LoginActivity.baseUrl+"";
        JSONObject obj = new JSONObject();

        try {

//            obj.put("password", etPass.getText().toString());
//            obj.put("email", ete_mail.getText().toString());
            obj.put("remember_me", 1);

        } catch (JSONException e) {
            e.printStackTrace();
//            Toast.makeText(LoginActivity.this, "Internet Connection Failed",
//                    Toast.LENGTH_SHORT).show();
        }

        Log.d("Send Login Request", "login request " + obj);

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                url, obj, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("Response", response.toString());

                try {
                    if (response.getBoolean("key")) {
                        //ewana key
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }



            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();

                // if 401 error - open login screen
                Log.d("Error", "error " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();

                //app eke ona thenakin
                SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
                String token = pref.getString("token", "");
                params.put("Authorization", "Bearer " + token);
                return params;

            }
        };

        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(jsonObjReq);

    }


}