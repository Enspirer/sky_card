package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.myapplication.helper.AppProgressDialog;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.example.myapplication.SplashActivity.BASE_URL;

public class LoginActivity extends AppCompatActivity {


    Button buttonLogin, buttonSignUp;

    private EditText etPassword, etUserName;
    private String userName, password;
    private CheckBox saveLoginCheckBox;
    private SharedPreferences loginPreferences;
    private SharedPreferences.Editor loginPrefEditor;
    private Boolean saveLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etPassword = findViewById(R.id.etPassword);
        etUserName = findViewById(R.id.etEmail);

        buttonSignUp = findViewById(R.id.btnSignUp);
        buttonLogin = findViewById(R.id.btnLogin);

        userName = etUserName.getText().toString();
        password = etPassword.getText().toString();

        saveLoginCheckBox = findViewById(R.id.checkBoxRemeber);
        loginPreferences = getSharedPreferences("loginPrefs",MODE_PRIVATE);
        loginPrefEditor = loginPreferences.edit();
        saveLogin = loginPreferences.getBoolean("saveLogin",false);

        if (saveLogin==true){
            etUserName.setText(loginPreferences.getString("username",""));
            etPassword.setText(loginPreferences.getString("password",""));
        }


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
                if (v==buttonLogin){
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(etUserName.getWindowToken(),0);

                    userName = etUserName.getText().toString();
                    password = etPassword.getText().toString();

                    if (saveLoginCheckBox.isChecked()){
                        loginPrefEditor.putBoolean("saveLogin",true);
                        loginPrefEditor.putString("username",userName);
                        loginPrefEditor.putString("password",password);
                        loginPrefEditor.commit();
                    }else {
                        loginPrefEditor.clear();
                        loginPrefEditor.commit();
                    }
                }
                requestLogin();
            }
        });

    }
    private void requestLogin() {

        AppProgressDialog.showProgressDialog(this, "Login...", true);

        String url = BASE_URL + "api/auth/login";
        JSONObject obj = new JSONObject();

        try {

            obj.put("password", etPassword.getText().toString());
            obj.put("email", etUserName.getText().toString());
            obj.put("remember_me", 1);

        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(LoginActivity.this, "Internet Connection Failed",
                    Toast.LENGTH_SHORT).show();
        }

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                url, obj, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("Response", response.toString());

                SharedPreferences sharedPref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();

                try {
                    editor.putString("token", response.getString("access_token"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                editor.commit();

                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();
                Toast.makeText(getApplicationContext(), "Login Successful", Toast.LENGTH_SHORT).show();

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                AppProgressDialog.hideProgressDialog();
                Log.d("Error", "Invalid Username and the password " + error.getMessage());
                Toast.makeText(getApplicationContext(), "Invalid Username or Password",
                        Toast.LENGTH_SHORT).show();
            }
        });

        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(jsonObjReq);
    }
}

