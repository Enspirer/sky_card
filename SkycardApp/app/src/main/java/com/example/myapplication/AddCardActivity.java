package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
import com.google.gson.JsonArray;

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

    Button buttonSaveCard, buttonReScanCard;

    EditText etName, etTitle, etcompanyName, etmobileNumber, etotherMobileNumber, etEmail,
            etwebSite,
            etaddressLine1,
            etadressLine2;

    private static final int CONTACT_PERMISSION_CODE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_card);

        iViewAddCard = findViewById(R.id.ivAddCardPrev);
        iViewAddCard.setImageURI(Uri.fromFile(CameraActivity.imageFile));
        etName = findViewById(R.id.etName);
        etTitle = findViewById(R.id.etTitle);
        etcompanyName = findViewById(R.id.etCompanyName);
        etmobileNumber = findViewById(R.id.etMobileNum);
        etotherMobileNumber = findViewById(R.id.etOtherMobile);
        etEmail = findViewById(R.id.etEmail);
        etwebSite = findViewById(R.id.etWebSite);
        etaddressLine1 = findViewById(R.id.etAddLine1);
        etadressLine2 = findViewById(R.id.etAddLine2);

        buttonSaveCard = findViewById(R.id.btnSaveCard);
        buttonReScanCard = findViewById(R.id.btnReScan);

        base64file = CameraActivity.resizedImage;

        sendCard();

//        buttonSaveCard.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });

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
//                Log.d("ddddddd", "ffffffffffffffffffffff");
//                Log.d("ddddddd", "ccccc " + response);

                try {
//                    if (!response.isNull("image")) {
//                        String image = response.getString("image");
//
//                    }

                    if (!response.isNull("email")) {
                        JSONArray emailArray = response.getJSONArray("email");
                        if (emailArray.length() > 0) {
                            String email = emailArray.getString(0);
                            etEmail.setText(email);
                        }
                    }

                    if (!response.isNull("phone_number")) {
                        JSONArray numArray = response.getJSONArray("phone_number");
                        if (numArray.length() > 0) {
                            String mobileNum1 = numArray.getString(0);
                            etmobileNumber.setText(mobileNum1);
                            Log.d("ssss", "ssss" + mobileNum1);

                        }
                        if (numArray.length() > 1) {
                            String mobileNum2 = numArray.getString(1);
                            etotherMobileNumber.setText(mobileNum2);
                        }
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
                AppProgressDialog.hideProgressDialog();
//                Toast.makeText(getApplicationContext(),
//                        error.getMessage(), Toast.LENGTH_SHORT).show();
            }


        }) {
            @Override
            protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
//                Log.d("dddddddddddddddd", "error 2 " + response.statusCode);
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



