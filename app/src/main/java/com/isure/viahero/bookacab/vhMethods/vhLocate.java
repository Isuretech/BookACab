package com.isure.viahero.bookacab.vhMethods;


import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.v4.app.ActivityCompat;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.isure.viahero.bookacab.BusinessObjects.LatLongInfo;


/**
 * Created by nec on 6/14/2016.
 */
public class vhLocate {
    @SuppressWarnings("unused")
    private static final float DEFAULT_ZOOM = (float) 17.5;

    public void gotoCurrentLocation(Context context, GoogleApiClient mGoogleApiClient, GoogleMap mMap) throws Exception {

        if (ActivityCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        Location currentLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);

        if (currentLocation == null) {
            throw new Exception("Current location isn't available");
        } else {
            LatLng ll = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());
            CameraUpdate update = CameraUpdateFactory.newLatLngZoom(ll, DEFAULT_ZOOM);
            mMap.animateCamera(update);

        }
    }

    private void gotoLocation(GoogleMap mMap, double lat, double lng) {
        LatLng ll = new LatLng(lat, lng);
        CameraUpdate update = CameraUpdateFactory.newLatLngZoom(ll, DEFAULT_ZOOM);
        mMap.moveCamera(update);
    }

    public LatLongInfo getLatLong(Context context, GoogleApiClient mGoogleApiClient) {
        LatLongInfo ret = new LatLongInfo();
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return null;
        }
        Location currentLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        ret.set_Lat((float) currentLocation.getLatitude());
        ret.set_Long((float) currentLocation.getLongitude());
        return ret;
    }
}
