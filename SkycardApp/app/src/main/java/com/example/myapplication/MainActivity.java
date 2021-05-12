package com.example.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.example.myapplication.helper.AppProgressDialog;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

public class MainActivity extends AppCompatActivity {

    public static String BASE_URL = "";

    private BottomNavigationView bottomNavigationView;
    private NavigationView topNavigationView;
    private DrawerLayout drawer;
    private CardView cvAddCard, cvCardHolder, cvMyCards, cvSkyDaily, cvSkyNetwork, cvShareCards,
            cvSkyVip, cvSkySmiles, cvSkyChats;
    private ImageView profilePic;
    private Toolbar toolbar;
    private TextView tvFullName;
    private TextView tvEmail;
    private View navHeader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.botom_navigation);

        topNavigationView = findViewById(R.id.nav_view);
        navHeader = topNavigationView.getHeaderView(0);
        profilePic = navHeader.findViewById(R.id.ivProfilePic);
        tvFullName = navHeader.findViewById(R.id.tvProfileName);
        tvEmail = navHeader.findViewById(R.id.tvMemberEmail);

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

        requestprofiledetails();

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

        topNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {

                if (item.getItemId() == R.id.naviNotifications) {
                    Intent intent = new Intent(getApplicationContext(), ComingSoonActivity.class);
                    startActivity(intent);
                }
                if (item.getItemId() == R.id.naviAddcard) {
                    Intent intent = new Intent(getApplicationContext(), CameraActivity.class);
                    startActivity(intent);

                }
                if (item.getItemId() == R.id.naviCardHolder) {
                    Intent intent = new Intent(getApplicationContext(), ComingSoonActivity.class);
                    startActivity(intent);

                }
                if (item.getItemId() == R.id.naviMyCards) {
                    Intent intent = new Intent(getApplicationContext(), MyCardsActivity.class);
                    startActivity(intent);

                }
                if (item.getItemId() == R.id.naviSKyDaily) {
                    Intent intent = new Intent(getApplicationContext(), ComingSoonActivity.class);
                    startActivity(intent);

                }
                if (item.getItemId() == R.id.naviSkyNetwork) {
                    Intent intent = new Intent(getApplicationContext(), ComingSoonActivity.class);
                    startActivity(intent);

                }
                if (item.getItemId() == R.id.naviShareCards) {
                    Intent intent = new Intent(getApplicationContext(), ComingSoonActivity.class);
                    startActivity(intent);

                }
                if (item.getItemId() == R.id.naviSettings) {
                    Intent intent = new Intent(getApplicationContext(), ComingSoonActivity.class);
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
                Intent intent = new Intent(getApplicationContext(), CameraActivity.class);
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

        cvCardHolder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ComingSoonActivity.class);
                startActivity(intent);
            }
        });


        cvSkyDaily.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ComingSoonActivity.class);
                startActivity(intent);

            }
        });

        cvCardHolder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ComingSoonActivity.class);
                startActivity(intent);

            }
        });


        cvShareCards.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ComingSoonActivity.class);
                startActivity(intent);

            }
        });


        cvSkyVip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ComingSoonActivity.class);
                startActivity(intent);

            }
        });

        cvSkySmiles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ComingSoonActivity.class);
                startActivity(intent);

            }
        });

        cvSkyChats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ComingSoonActivity.class);
                startActivity(intent);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_bell, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.bell:
                Intent intent = new Intent(getApplicationContext(), ComingSoonActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    public void requestprofiledetails() {

        String url = "http://thechaptersrilanka.com/sky_card_backend/public/api/auth/userDetails";
        JSONObject obj = new JSONObject();

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                url, obj, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                AppProgressDialog.hideProgressDialog();
//                Log.d("ddddddd", "ffffffffffffffffffffff");
//                Log.d("ddddddd", "ccccc " + response);

                try {
                    if (!response.isNull("profile_picture")) {
                        String imageUrl = response.getString("profile_picture");
                        Glide.with(MainActivity.this)
                                .load(imageUrl)
                                .listener(new RequestListener<Drawable>() {
                            @Override
                            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                return false;
                            }

                            @Override
                            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                return false;
                            }
                        }).transition(withCrossFade())
                                .apply(new RequestOptions()
                                        .transform(new RoundedCorners(100))
                                        .error(R.drawable.avatar)
                                        .skipMemoryCache(true)
                                        .diskCacheStrategy(DiskCacheStrategy.NONE))
                                .override(100, 75).into(profilePic);
                    }

                    if (!response.isNull("email")) {
                        String email = response.getString("email");
                        tvEmail.setText(email);
                        Log.d("uuuuuu", "name " + email);
                    }

                    if (!response.isNull("full_name")) {
                        String fullName = response.getString("full_name");
                        tvFullName.setText(fullName);
                        Log.d("uuuuuu", "name " + fullName);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("resposneerror ", "error ");
                error.printStackTrace();
//                Toast.makeText(getApplicationContext(),
//                        error.getMessage(), Toast.LENGTH_SHORT).show();
            }

        }) {
            @Override
            protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
                Log.d("dddddddddddddddd", "error 2 " + response.statusCode);
                if (response.statusCode != 200) {
                    return Response.error(new ParseError(response));
                } else {
                    try {
                        if (response.data.length == 0) {
                            byte[] responseData = "{}".getBytes("UTF8");
                            response = new NetworkResponse(response.statusCode, responseData, response.headers, response.notModified);
                            Log.d("ddddddddddddddddd", "cccc " + responseData.toString());
                        }
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                    return super.parseNetworkResponse(response);
                }
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();

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