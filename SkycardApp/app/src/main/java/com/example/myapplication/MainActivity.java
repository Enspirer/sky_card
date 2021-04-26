package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import org.json.JSONException;

public class MainActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageView logoLogout = findViewById(R.id.imageViewlogo);

        logoLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearPref();

            }
        });

    }

    public void clearPref(){

        SharedPreferences sharedPref = getApplicationContext().getSharedPreferences(
                "MyPref", MODE_PRIVATE);
        sharedPref.edit().remove("Mypref").commit();

        Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
        startActivity(intent);
        finish();

    }


}