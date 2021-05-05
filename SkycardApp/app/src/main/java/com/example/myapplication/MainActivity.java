package com.example.myapplication;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.myapplication.other.CircleTransform;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private NavigationView topNavigationView;
    private DrawerLayout drawer;
    private LinearLayout cvAddCard, cvCardHolder, cvMyCards, cvSkyDaily, cvSkyNetwork, cvShareCards,
            cvSkyVip, cvSkySmiles, cvSkyChats;

    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.botom_navigation);
        topNavigationView = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);

        cvAddCard = findViewById(R.id.cvAddCard);
        cvCardHolder = findViewById(R.id.cvCardHolder);
        cvMyCards = findViewById(R.id.cvMyCards);
        cvSkyDaily = findViewById(R.id.cvSkyDaily);
        cvSkyNetwork = findViewById(R.id.cvSkyNetwork);
        cvShareCards = findViewById(R.id.cvShareCards);
        cvSkyVip = findViewById(R.id.cvSkyVip);
        cvSkySmiles = findViewById(R.id.cvSkySmiles);
        cvSkyChats = findViewById(R.id.cvSkyChat);

        drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,
                drawer,
                toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                if (item.getItemId() == R.id.naviAddacrd) {
                    Intent intent = new Intent(getApplicationContext(), CameraActivity.class);
                    startActivity(intent);
                }
                if (item.getItemId() == R.id.navimycards) {
                    Intent intent = new Intent(getApplicationContext(), MyCardsActivity.class);
                    startActivity(intent);
                }
                if (item.getItemId() == R.id.navicardholder) {
                    Intent intent = new Intent(getApplicationContext(), CardHolderActivity.class);
                    startActivity(intent);
                }
                if (item.getItemId() == R.id.navisharecards) {
                    Intent intent = new Intent(getApplicationContext(), ShareCardsActivity.class);
                    startActivity(intent);
                }

                return false;
            }
        });

        topNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {

                if (item.getItemId() == R.id.naviNotifications) {
                    Intent intent = new Intent(getApplicationContext(), NotificationsActivity.class);
                    startActivity(intent);
                }
                if (item.getItemId() == R.id.naviAddcard) {
                    Intent intent = new Intent(getApplicationContext(), AddCardsActivity.class);
                    startActivity(intent);

                }
                if (item.getItemId() == R.id.naviCardHolder) {
                    Intent intent = new Intent(getApplicationContext(), CardHolderActivity.class);
                    startActivity(intent);

                }
                if (item.getItemId() == R.id.naviMyCards) {
                    Intent intent = new Intent(getApplicationContext(), MyCardsActivity.class);
                    startActivity(intent);

                }
                if (item.getItemId() == R.id.naviSKyDaily) {
                    Intent intent = new Intent(getApplicationContext(), SkyDailyActivity.class);
                    startActivity(intent);

                }
                if (item.getItemId() == R.id.naviSkyNetwork) {
                    Intent intent = new Intent(getApplicationContext(), SkyNetworkActivity.class);
                    startActivity(intent);

                }
                if (item.getItemId() == R.id.naviShareCards) {
                    Intent intent = new Intent(getApplicationContext(), ShareCardsActivity.class);
                    startActivity(intent);

                }
                if (item.getItemId() == R.id.naviSettings) {
                    Intent intent = new Intent(getApplicationContext(), SettingsActivity.class);
                    startActivity(intent);

                }
                if (item.getItemId() == R.id.naviLogOut) {

                    clearPref();
                    finish();
                }
                return false;
            }
        });

        cvAddCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AddCardsActivity.class);
                startActivity(intent);

            }
        });
        cvMyCards.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MyCardsActivity.class);
                startActivity(intent);

            }
        });

        cvSkyDaily.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SkyDailyActivity.class);
                startActivity(intent);

            }
        });

        cvCardHolder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CardHolderActivity.class);
                startActivity(intent);

            }
        });
        cvSkyNetwork.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SkyNetworkActivity.class);
                startActivity(intent);

            }
        });

        cvShareCards.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ShareCardsActivity.class);
                startActivity(intent);

            }
        });


        cvSkyVip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });

        cvSkySmiles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });

        cvSkyChats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });


    }


    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    public void clearPref() {
        SharedPreferences sharedPref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
        sharedPref.edit().remove("token").apply();

        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(intent);
        finish();
        Toast.makeText(getApplicationContext(), "Logout Successful", Toast.LENGTH_SHORT).show();

    }


}