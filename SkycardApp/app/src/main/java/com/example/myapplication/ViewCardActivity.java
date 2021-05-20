package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.myapplication.helper.AppProgressDialog;
import com.example.myapplication.models.Model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.myapplication.MyCardsActivity.shareUrl;
import static com.example.myapplication.SplashActivity.BASE_URL;

public class ViewCardActivity extends AppCompatActivity {

    WebView wvCard;
    String newUrl;
    String url;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_card);



        wvCard = findViewById(R.id.wvCard);
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        AppProgressDialog.showProgressDialog(this, "Loading...", true);
        newUrl = extras.getString("web_url");
        url = (BASE_URL + "c/" + newUrl);
        wvCard.loadUrl(url);
        AppProgressDialog.hideProgressDialog();

    }

}