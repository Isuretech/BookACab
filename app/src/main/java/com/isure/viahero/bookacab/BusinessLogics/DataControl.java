package com.isure.viahero.bookacab.BusinessLogics;

import android.content.Context;
import android.telecom.Call;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.isure.viahero.bookacab.BusinessObjects.DriverInfo;
import com.isure.viahero.bookacab.BusinessObjects.PassengerInfo;
import com.isure.viahero.bookacab.appConfig.config;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Rob on 6/15/2016.
 * Modified by nec on 6/24/2016
 */
public class DataControl {
    String url;
    public void getCurrentBooking(final Context context, String ID, final VolleyCallback callback){
        final DriverInfo ret = new DriverInfo();
        url = null;
        url = (config.DATA_URL + "action=Curr" +"&ID=" + ID + "&type=Passenger").replace(" ","%20");

        StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONObject extractedJSON = extractJSON(response);
                try {
                    ret.set_driverName(extractedJSON.getString(config.KEY_DRVNAME).toString());
                    ret.set_pickupPoint(extractedJSON.getString(config.KEY_PICK).toString());
                    ret.set_destination(extractedJSON.getString(config.KEY_DROP).toString());
//                    ret.set_currentLng(Float.parseFloat(extractedJSON.getString(config.KEY_LAT).toString()));
//                    ret.set_currentLat(Float.parseFloat(extractedJSON.getString(config.KEY_LONG).toString()));
                    ret.set_contactNumber(extractedJSON.getString(config.KEY_CONTNUM).toString());
                    ret.set_operatorName(extractedJSON.getString(config.KEY_DRVNAME));
                    callback.onGetDriverInfoSuccess(ret);
                } catch (JSONException e) {
                    e.printStackTrace();
                }catch (Exception ex){
                    Toast.makeText(context,ex.getMessage().toString(),Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context,error.getMessage().toString(),Toast.LENGTH_LONG).show();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);
    }
    public void getPassengerInfo(final Context context, String Username, String Password, final VolleyCallback callback){
        final PassengerInfo ret = new PassengerInfo();
        url = null;
        url = (config.DATA_URL+"action=view&Username="+Username+"&Password="+Password+"&type=Passenger").replace(" ","%20");

        StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONObject extractedJSON = extractJSON(response);
                try {
                    ret.set_passengerId(extractedJSON.getString(config.KEY_PID).toString());
                    ret.set_passengerFirstName(extractedJSON.getString(config.KEY_FNAM).toString());
                    ret.set_passengerMiddleName(extractedJSON.getString(config.KEY_MNAM).toString());
                    ret.set_passengerLastName(extractedJSON.getString(config.KEY_LNAM).toString());
                    ret.set_contactNumber(extractedJSON.getString(config.KEY_CONTNUM).toString());
                    ret.set_points(Double.parseDouble(extractedJSON.getString(config.KEY_PTS)));
                    callback.onGetPassengerInfoSuccess(ret);
                } catch (JSONException e) {
                    e.printStackTrace();
                }catch (Exception ex){
                    Toast.makeText(context,ex.getMessage().toString(),Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context,error.getMessage().toString(),Toast.LENGTH_LONG).show();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);
    }
    public void postBookPassenger(final Context context, PassengerInfo passengerInfo, final VolleyCallback callback){
        url = null;
        url = (config.DATA_URL+"action=Post&UserID=" + passengerInfo.get_passengerId() + "&Pick=" + passengerInfo.get_pickupPoint() + "&Drop=" + passengerInfo.get_destination() + "&Remarks=" + passengerInfo.get_remarks() +
                "&PickLat=" + passengerInfo.get_pickupLat() + "&PickLon="+ passengerInfo.get_pickupLng() + "&DropLat=" + passengerInfo.get_destLat() + "&DropLon="+ passengerInfo.get_destLng() + "&Tip=" + passengerInfo.get_tip() ).replace(" ","%20");

        StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONObject extractedJSON = extractJSON(response);
                try {
                    //callback.onSuccess(extractedJSON.getString(config.KEY_USER).toString(),extractedJSON.getString(config.KEY_PASS).toString());
                    callback.onSuccess();
//                } catch (JSONException e) {
//                    e.printStackTrace();
                }catch (Exception ex){
                    Toast.makeText(context,ex.getLocalizedMessage(),Toast.LENGTH_SHORT).show();
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context,error.getMessage().toString(),Toast.LENGTH_LONG).show();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);
    }
    public void postAcceptRequest(Context context, String _passengerID,final VolleyCallback callback){
        url = null;
        url = (config.DATA_URL + "action=accept&PID="+ _passengerID).replace(" ","%20");

        StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>(){
            @Override
            public void onResponse(String response){
                callback.onSuccess();
            }

        },
                new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error){
                        callback.onErrorResponse(error.getMessage());
                    }
                });
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);
    }

//    public void postBookPassenger(final Context context, String ID, String PickupTime,String PickupPoint, String Destination,double pickLng,double pickLat,
//                                  double destLng,double destLat, final VolleyCallback callback){
//        url = null;
//        url = (config.DATA_URL+"action=send&ID=" + ID + "&time=" + PickupTime + "&pick=" + PickupPoint + "&drop=" + Destination +
//                "&picklng=" + pickLng + "&picklat="+ pickLat+ "&destlng=" + destLng + "&destlat="+ destLat).replace(" ","%20");
//
//        StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//                JSONObject extractedJSON = extractJSON(response);
//                try {
//                    callback.onSuccess(extractedJSON.getString(config.KEY_USER).toString(),extractedJSON.getString(config.KEY_PASS).toString());
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//        },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        Toast.makeText(context,error.getMessage().toString(),Toast.LENGTH_LONG).show();
//                    }
//                });
//
//        RequestQueue requestQueue = Volley.newRequestQueue(context);
//        requestQueue.add(stringRequest);
//    }

    private JSONObject extractJSON(String response){
        JSONObject ret = null;
        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray result = jsonObject.getJSONArray(config.JSON_ARRAY);
            ret = result.getJSONObject(0);
        } catch (JSONException e) {
            e.printStackTrace();
        }catch (Exception ex){
            System.out.println(ex.getMessage().toString());
        }
        return ret;
    }

}
