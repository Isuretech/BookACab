package com.isure.viahero.bookacab.vhMethods;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;

/**
 * Created by nec on 6/15/2016.
 */
public class vhCommunication extends Activity {
    public Intent CallNumber(String contactNum) {
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse("tel:" + contactNum));
        return callIntent;

    }
    public Intent TextNumber(String contactNum) {
        Intent smsIntent = new Intent(Intent.ACTION_VIEW);

        smsIntent.setData(Uri.parse("smsto:"));
        smsIntent.setType("vnd.android-dir/mms-sms");
        smsIntent.putExtra("address"  , contactNum);
        smsIntent.putExtra("sms_body"  , "");

        return smsIntent;

    }
}
