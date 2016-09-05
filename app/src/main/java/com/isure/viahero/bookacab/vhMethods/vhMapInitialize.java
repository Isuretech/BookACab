package com.isure.viahero.bookacab.vhMethods;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;

/**
 * Created by nec on 6/14/2016.
 */
public class vhMapInitialize extends Activity {
    private static final int GPS_ERRORDIALOG_REQUEST = 9001;

    public boolean servicesOK(Context context) {
        boolean ret = false;
        int isAvailable = GooglePlayServicesUtil.isGooglePlayServicesAvailable(context);

        if (isAvailable == ConnectionResult.SUCCESS) {
            ret = true;
        }
        else if (GooglePlayServicesUtil.isUserRecoverableError(isAvailable)) {
            Dialog dialog = GooglePlayServicesUtil.getErrorDialog(isAvailable, this, GPS_ERRORDIALOG_REQUEST);
            dialog.show();
        }
        else {
            Toast.makeText(this, "Can't connect to Google Play services", Toast.LENGTH_SHORT).show();
        }
        return ret;
    }
}
