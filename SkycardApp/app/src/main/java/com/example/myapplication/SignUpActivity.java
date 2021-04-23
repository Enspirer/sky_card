package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class SignUpActivity extends AppCompatActivity {

    EditText userName, userEmail, userPassword;
    Button buttonLogin, buttonSignup;

    String name, password, email;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        userName = findViewById(R.id.etName);
        userEmail = findViewById(R.id.etEmail);
        userPassword = findViewById(R.id.etPassword);

        buttonLogin = findViewById(R.id.btnLogin);
        buttonSignup = findViewById(R.id.btnSignUp);

        name = userName.getText().toString();
        password = userPassword.getText().toString();
        email = userEmail.getText().toString();

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
            }
        });

        buttonSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendSignupData();
            }
        });
    }

    private void sendSignupData() {

        String url = "http://thechaptersrilanka.com/sky_card_backend/public/api/auth/signup";
        JSONObject obj = new JSONObject();

        try {
            obj.put("first_name", userName.getText().toString());
            obj.put("last_name", "MyLastName");
            obj.put("password", userPassword.getText().toString());
            obj.put("email", userEmail.getText().toString());

        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(SignUpActivity.this, "Network connection failed",
                    Toast.LENGTH_SHORT).show();
        }
        Log.d("Send data", "send " + obj);

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                url, obj, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("Response", response.toString());

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Error", "error " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(jsonObjReq);
    }

}
