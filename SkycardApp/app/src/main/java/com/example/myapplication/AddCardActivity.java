package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.myapplication.helper.AppProgressDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class AddCardActivity extends AppCompatActivity {

    ImageView iViewAddCard;
    String base64file;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_card);

        iViewAddCard = findViewById(R.id.ivAddCardPrev);
        iViewAddCard.setImageURI(Uri.fromFile(CameraActivity.imageFile));

        base64file = CameraActivity.resizedImage;
        Log.d("ddddddddddddddddddd", "dddddddddddddd " + base64file);

        sendCard();

    }

    private void sendCard() {
        AppProgressDialog.showProgressDialog(this, "Scanning..", false);

        String url = "http://thechaptersrilanka.com/sky_card_backend/public/api/auth/upload_card";
        JSONObject obj = new JSONObject();

        try {
            obj.put("upload_file", base64file);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                url, obj, new Response.Listener<JSONObject>() {


            @Override
            public void onResponse(JSONObject response) {
                AppProgressDialog.hideProgressDialog();
                Log.d("ddddddd", "ffffffffffffffffffffff");
                Log.d("ddddddd", "ccccc " + response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("ddddddddddddd ", "error ");
                error.printStackTrace();
                AppProgressDialog.hideProgressDialog();
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


