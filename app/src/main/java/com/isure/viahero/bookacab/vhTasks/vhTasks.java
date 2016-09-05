package com.isure.viahero.bookacab.vhTasks;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;

import com.isure.viahero.bookacab.BusinessLogics.DataControl;
import com.isure.viahero.bookacab.BusinessLogics.TaskListener;
import com.isure.viahero.bookacab.BusinessLogics.VolleyCallback;
import com.isure.viahero.bookacab.BusinessObjects.DriverInfo;
import com.isure.viahero.bookacab.BusinessObjects.PassengerInfo;
import com.isure.viahero.bookacab.DAL.AccessServiceAPI;
import com.isure.viahero.bookacab.MainActivity;
import com.isure.viahero.bookacab.appConfig.common;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Rob on 5/29/2016.
 */

public class vhTasks {

    public static class TaskLogin extends AsyncTask<String,Void,Object>{

        private final Context context;
        private String userName, Password;
        private ProgressDialog mProgressDialog;
        private AccessServiceAPI mServiceAccess;

        public TaskLogin(Context context,String _userName,String _Password){
            this.context=context;
            userName = _userName;
            Password = _Password;
            mServiceAccess = new AccessServiceAPI();
        }

        @Override
        protected void onPreExecute(){
            super.onPreExecute();
            mProgressDialog = ProgressDialog.show(context,"Please wait..", "Processing...",true);
            mProgressDialog.setIndeterminate(false);
            mProgressDialog.setCancelable(true);
        }
        @Override
        protected Object doInBackground(String... params) {

            Map<String,String> param = new HashMap<>();
            param.put("action","login");
            param.put("Username",params[0]);
            param.put("password",params[1]);
            param.put("type",params[2]);

            JSONObject JResult;
            try{
                JResult = mServiceAccess.convertJSONString2Obj(mServiceAccess.getJSONStringWithParam_POST(common.SERVICE_API_URL, param));
                return JResult.getInt("result");
//                return 0;
            }catch (Exception ex){
                return common.RESULT_USER_EXISTS;
            }
        }
        @Override
        protected void onPostExecute(Object result){
            super.onPostExecute(result);
            mProgressDialog.dismiss();
            if (common.RESULT_SUCCESS == result){
                Intent i = new Intent(context, MainActivity.class);
                i.putExtra("Username", userName);
                i.putExtra("Password",Password);
                context.startActivity(i);
            }else {
                Toast.makeText(context, "Login failed.", Toast.LENGTH_LONG).show();
            }

        }
    }
//    public static class TaskGetCurrenttrip extends AsyncTask<PassengerInfo, Void, DriverInfo[]> {
//        DataControl dataControl = new DataControl();
//        Context context;
//        TaskListener _taskListener;
//        public TaskGetCurrenttrip(Context context, TaskListener taskListener){
//            this.context = context;
//            _taskListener = taskListener;
//        }
//        @Override
//        protected DriverInfo[] doInBackground(PassengerInfo... params) {
//            try {
//                String ID = params[0].get_passengerId();
//                final DriverInfo[] ret = new DriverInfo[1];
//                dataControl.getCurrentBooking(context, ID, new VolleyCallback() {
//                    @Override
//                    public void onGetPassengerInfoSuccess(PassengerInfo result) {
//
//                    }
//
//                    @Override
//                    public void onSuccess() {
//
//                    }
//
//                    @Override
//                    public void onGetDriverInfoSuccess(DriverInfo result) {
//                        ret[0] = result;
//                    }
//                });
//
//                return ret;
//            }catch (Exception ex){
//                return null;
//            }
//
//        }
//        @Override
//        protected void onPostExecute(DriverInfo[] result){
//            _taskListener.onTaskGetCurrenttripSuccess(result[0]);
//        }
//    }
}
