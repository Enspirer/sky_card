package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class LoginActivity extends AppCompatActivity {


    Button buttonLogin, buttonSignUp;

    EditText etPass, ete_mail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etPass = findViewById(R.id.etPassword);
        ete_mail = findViewById(R.id.etEmail);

        buttonSignUp = findViewById(R.id.btnSignUp);
        buttonLogin = findViewById(R.id.btnLogin);

        buttonSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SignUpActivity.class);
                startActivity(intent);
            }
        });

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestLogin();
            }
        });
    }

    private void requestLogin() {

        String url = "http://thechaptersrilanka.com/sky_card_backend/public/api/auth/login";
        JSONObject obj = new JSONObject();

        try {

            obj.put("password", etPass.getText().toString());
            obj.put("email", ete_mail.getText().toString());
            obj.put("remember_me", 1);

        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(LoginActivity.this, "Internet Connection Failed",
                    Toast.LENGTH_SHORT).show();
        }

        Log.d("Send Login Request", "login request " + obj);

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                url, obj, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("Response", response.toString());

                //stored as mypref
                SharedPreferences sharedPref = getApplicationContext().getSharedPreferences(
                        "MyPref", MODE_PRIVATE);

                SharedPreferences.Editor editor = sharedPref.edit();

                try {
                    //store the key and its values
                    editor.putString("token", response.getString("access_token"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                editor.commit();

                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
               startActivity(intent);
               finish();

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();

                Log.d("Error", "error " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

//        {
//            @Override
//            public Map<String, String> getHeaders() throws AuthFailureError {
//                Map<String, String> params = new HashMap<String, String>();
//
//                SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
//                String token = pref.getString("token", "");
//                params.put("Authorization", "Bearer " + token);
//
//                //Log.d("fdgdfgfd", "msg" + token);
//                return params;
//            }
//        };

        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(jsonObjReq);

    }


}