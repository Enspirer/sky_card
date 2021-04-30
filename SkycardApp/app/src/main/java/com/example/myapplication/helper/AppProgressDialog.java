package com.example.myapplication.helper;

import android.app.ProgressDialog;
import android.content.Context;

public class AppProgressDialog {
    private static ProgressDialog pDialog;

    public static ProgressDialog showProgressDialog(Context context, String message, boolean cancellable) {
        pDialog = new ProgressDialog(context);
        pDialog.setMessage(message);
        pDialog.setCancelable(cancellable);
        pDialog.show();
        return pDialog;
    }

    public static void hideProgressDialog() {
        if (pDialog!=null && pDialog.isShowing()) {
            pDialog.dismiss();
        }
    }
}
