package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.SurfaceControl;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.myapplication.helper.AppProgressDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.sql.Time;
import java.util.HashMap;
import java.util.Map;

public class AddCardActivity extends AppCompatActivity {

    ImageView iViewAddCard, ivCloseCard, ivbackCard;
    String base64file;
    Integer card_type;
    Button buttonSaveCard, buttonReScanCard;
    View popUpWindow;

    EditText etUserName, etTitle, etcompanyName, etmobileNumber, etotherMobileNumber, etEmail,
            etwebSite,
            etaddressLine1,
            etaddressLine2;

    private static final int CONTACT_PERMISSION_CODE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_card);

        iViewAddCard = findViewById(R.id.ivAddCardPrev);
        iViewAddCard.setImageURI(Uri.fromFile(CameraActivity.imageFile));
        ivbackCard = findViewById(R.id.ivbackCard);
        ivCloseCard = findViewById(R.id.ivCloseCard);

        etUserName = findViewById(R.id.etName);
        etTitle = findViewById(R.id.etTitle);
        etcompanyName = findViewById(R.id.etCompanyName);
        etmobileNumber = findViewById(R.id.etMobileNum);
        etotherMobileNumber = findViewById(R.id.etOtherMobile);
        etEmail = findViewById(R.id.etEmail);
        etwebSite = findViewById(R.id.etWebSite);
        etaddressLine1 = findViewById(R.id.etAddLine1);
        etaddressLine2 = findViewById(R.id.etAddLine2);

        buttonSaveCard = findViewById(R.id.btnSaveCard);
        buttonReScanCard = findViewById(R.id.btnReScan);

        base64file = CameraActivity.resizedImage;

        sendCard();

        ivCloseCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        ivbackCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CameraActivity.class);
                startActivity(intent);
                finish();
            }
        });

        buttonSaveCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!etUserName.getText().toString().isEmpty() && (!etmobileNumber.getText().toString().isEmpty() || !etotherMobileNumber.getText().toString().isEmpty())) {
                    savecard();
//                    Toast.makeText(getApplicationContext(), "Contact Saved!", Toast.LENGTH_SHORT).show();
                    saveButtonShowPopup();
                    clearfileds();
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(getApplicationContext(), "Name and Contact Number Can not " +
                            "Empty!", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    private void clearfileds() {
        etUserName.setText(null);
        etTitle.setText(null);
        etcompanyName.setText(null);
        etmobileNumber.setText(null);
        etotherMobileNumber.setText(null);
        etEmail.setText(null);
        etwebSite.setText(null);
        etaddressLine1.setText(null);
        etaddressLine2.setText(null);
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
//                            Log.d("ssss", "ssss" + mobileNum1);

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
//                            Log.d("ddddddddddddddddd", "cccc " + responseData.toString());
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

    private void savecard() {
        card_type = 1;

        AppProgressDialog.showProgressDialog(this, "Saving..", false);
        String url = "http://thechaptersrilanka.com/sky_card_backend/public/api/auth/save_cards";
        JSONObject obj = new JSONObject();

        try {
            obj.put("name", etUserName.getText().toString());
            obj.put("title", etTitle.getText().toString());
            obj.put("email", etEmail.getText().toString());
            obj.put("address_line1", etaddressLine1.getText().toString());
            obj.put("address_line2", etaddressLine2.getText().toString());
            obj.put("phone_number1", etmobileNumber.getText().toString());
            obj.put("phone_number2", etotherMobileNumber.getText().toString());
            obj.put("website", etwebSite.getText().toString());
            obj.put("card_type", card_type);
            obj.put("card_image", "abcd");
            obj.put("company_name", etcompanyName.getText().toString());

        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                url, obj, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                AppProgressDialog.hideProgressDialog();
//                Log.d("ddddddd", "ffffffffffffffffffffff");
                Log.d("ssssss", "sssss " + response);
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("resposneerror ", "error ");
                error.printStackTrace();
                AppProgressDialog.hideProgressDialog();
            }

        }) {
            @Override
            protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
                Log.d("dddddddddddddddd", "network Response " + response.statusCode);
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

    public void saveButtonShowPopup() {

        // inflate the layout of the popup window
        LayoutInflater inflater = (LayoutInflater)
                getSystemService(LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.save_popup_window, null);

        // create the popup window
        int width = LinearLayout.LayoutParams.WRAP_CONTENT;
        int height = LinearLayout.LayoutParams.WRAP_CONTENT;
        boolean focusable = true; // lets taps outside the popup also dismiss it
        final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);

        // show the popup window
        // which view you pass in doesn't matter, it is only used for the window tolken
//        popupWindow.showAtLocation(view, Gravity.CENTER, 10, 20);
        popupWindow.showAtLocation(popupView, Gravity.CENTER, 0, 0);


        // dismiss the popup window when touched
        popupView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                popupWindow.dismiss();
                return true;
            }
        });
    }

}



