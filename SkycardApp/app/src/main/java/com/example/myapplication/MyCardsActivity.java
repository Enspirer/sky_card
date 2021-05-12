package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.myapplication.helper.AppProgressDialog;
import com.example.myapplication.models.Model;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.JsonArray;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MyCardsActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    MyRecyclerViewAdapter myRecyclerViewAdapter;
    MyRecyclerViewAdapter adapter;
    List<Model> models;
    private BottomNavigationView bottomNavigationView;

    Button buttonShareCard, buttonViewCard;
    public static String shareUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_cards);

        bottomNavigationView = findViewById(R.id.botom_navigation);

        recyclerView = findViewById(R.id.rvMyCards);
        models = new ArrayList<>();
        myCards();

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                if (item.getItemId() == R.id.naviAddacrd) {
                    Intent intent = new Intent(getApplicationContext(), CameraActivity.class);
                    startActivity(intent);
                }
                if (item.getItemId() == R.id.naviHome) {
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                }
                if (item.getItemId() == R.id.navicardholder) {
                    Intent intent = new Intent(getApplicationContext(), ComingSoonActivity.class);
                    startActivity(intent);
                }
                if (item.getItemId() == R.id.navisharecards) {
                    Intent intent = new Intent(getApplicationContext(), ComingSoonActivity.class);
                    startActivity(intent);
                }

                return false;
            }
        });

    }

    private void myCards() {

        AppProgressDialog.showProgressDialog(this, "Loading your Cards...", false);
        String url = "http://thechaptersrilanka.com/sky_card_backend/public/api/auth/my_cards";
        RequestQueue queue = Volley.newRequestQueue(this);

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.POST, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        AppProgressDialog.hideProgressDialog();

                        for (int i = 0; i < response.length(); i++) {

                            try {
                                JSONObject cardObject = response.getJSONObject(i);
                                Model model = new Model();

                                List<String> phone_number = new ArrayList<>();

                                String phoneNumber = cardObject.getString("phone_number");
                                JSONObject jsonObject = new JSONObject(phoneNumber);
                                Log.d("ssss","aaaa "+jsonObject);
                                model.setPhoneNumber1(jsonObject.getString("phone_number1"));
                                model.setPhoneNumber2(jsonObject.getString("phone_number2"));
//                                Log.d("ssss","ssss"+jsonObject.getString("phone_number1"));
//                                Log.d("ssss","ssss"+jsonObject.getString("phone_number2"));

                                model.setName(cardObject.getString("name"));
                                model.setTitle(cardObject.getString("position"));
                                model.setEmail(cardObject.getString("email"));
                                model.setWebsite(cardObject.getString("website"));
                                model.setAddress(cardObject.getString("address"));
                                model.setViewCardURL(cardObject.getString("slug"));
                                model.setCoverImage(cardObject.getString("cover_image"));
                                model.setProfilePicture(cardObject.getString("avatar_image"));
                                shareUrl = model.setViewCardURL(cardObject.getString("slug"));
                                ImageView iv = new ImageView(getApplicationContext());
                                models.add(model);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                        adapter = new MyRecyclerViewAdapter(MyCardsActivity.this,
                                (ArrayList<Model>) models);
                        recyclerView.setAdapter(adapter);

                    }

                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Intent intent = new Intent(getApplicationContext(), PageNotFoundActivity.class);
                startActivity(intent);

            }

        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();

                SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
                String token = pref.getString("token", "");
                params.put("Authorization", "Bearer " + token);
                return params;
            }
        };
        queue.add(jsonArrayRequest);
    }


}