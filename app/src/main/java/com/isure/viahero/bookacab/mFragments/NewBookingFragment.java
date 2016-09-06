package com.isure.viahero.bookacab.mFragments;


import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceBuffer;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.isure.viahero.bookacab.BusinessLogics.DataControl;
import com.isure.viahero.bookacab.BusinessLogics.VolleyCallback;
import com.isure.viahero.bookacab.BusinessObjects.DriverInfo;
import com.isure.viahero.bookacab.BusinessObjects.LatLongInfo;
import com.isure.viahero.bookacab.BusinessObjects.PassengerInfo;
import com.isure.viahero.bookacab.R;
import com.isure.viahero.bookacab.vhMethods.PlaceArrayAdapter;
import com.isure.viahero.bookacab.vhMethods.vhLocate;

import java.util.Locale;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class NewBookingFragment extends Fragment implements
        GoogleApiClient.OnConnectionFailedListener,
        GoogleApiClient.ConnectionCallbacks,
        View.OnClickListener {

    private static final int GOOGLE_API_CLIENT = 0;
    private AutoCompleteTextView txtPickupPoint;
    private AutoCompleteTextView txtDropoffPoint;
    private EditText txtRemarks, txtTip;

    private GoogleApiClient mGoogleApiClient;
    private PlaceArrayAdapter mPlaceArrayAdapter;

    //private static final LatLngBounds BOUNDS_MOUNTAIN_VIEW = new LatLngBounds(new LatLng(37.398160,-122.180831),new LatLng(37.430610,-121.972090));
    private static final LatLngBounds BOUNDS_MOUNTAIN_VIEW = new LatLngBounds(new LatLng(4.6145711,119.6272661),new LatLng(19.966096,124.173694));


    vhLocate vhlocate = new vhLocate();

    PassengerInfo _passengerInfo;
    View rootView;
    Button btnSendReq;
    private ScheduledExecutorService scheduleTaskExecutor;

    public NewBookingFragment(PassengerInfo passengerInfo) {
        // Required empty public constructor
        _passengerInfo = passengerInfo;
    }

    @SuppressLint("WrongViewCast")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_new_booking, container, false);
        Toast.makeText(getContext(), _passengerInfo.get_userName().toString(), Toast.LENGTH_SHORT).show();
        mGoogleApiClient = new GoogleApiClient.Builder(getContext())
                .addApi(Places.GEO_DATA_API)
                .addApi(Places.PLACE_DETECTION_API)
                .addApi(LocationServices.API)
                .enableAutoManage(getActivity(), GOOGLE_API_CLIENT, this)
                .addConnectionCallbacks(this).build();

        mPlaceArrayAdapter = new PlaceArrayAdapter(getContext(),android.R.layout.simple_list_item_1,BOUNDS_MOUNTAIN_VIEW,null);
        txtPickupPoint = (AutoCompleteTextView) rootView.findViewById(R.id.autoCompleteTextView);
        txtDropoffPoint = (AutoCompleteTextView) rootView.findViewById(R.id.autoCompleteTextView1);
        txtPickupPoint.setThreshold(3);
        txtDropoffPoint.setThreshold(3);
        txtPickupPoint.setOnItemClickListener(mAutoCompleteClickListener);
        txtDropoffPoint.setOnItemClickListener(mAutoCompleteClickListener);
        txtPickupPoint.setAdapter(mPlaceArrayAdapter);
        txtDropoffPoint.setAdapter(mPlaceArrayAdapter);
//        txtPickupPoint.setText("Current Location");
//        txtPickupPoint.selectAll();
        btnSendReq = (Button) rootView.findViewById(R.id.btnSendReq);
        btnSendReq.setOnClickListener(this);
        txtRemarks = (EditText) rootView.findViewById(R.id.txtRemarks);
        txtTip = (EditText) rootView.findViewById(R.id.txtTip);
//        txtPickupPoint.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                if(txtPickupPoint.getText().toString().equals(null) || txtPickupPoint.getText().toString().isEmpty()){
//                    txtPickupPoint.setText("Current Location");
//                    txtPickupPoint.selectAll();
//                }
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//
//            }
//        });
        return rootView;
    }

    private AdapterView.OnItemClickListener mAutoCompleteClickListener = new AdapterView.OnItemClickListener(){
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id){
            final PlaceArrayAdapter.PlaceAutocomplete item = mPlaceArrayAdapter.getItem(position);
            final String placeId = String.valueOf(item.placeId);
            PendingResult<PlaceBuffer> placeResult = Places.GeoDataApi.getPlaceById(mGoogleApiClient,placeId);
            placeResult.setResultCallback(mUpdatePlaceDetailsCallback);
        }
    };

    private ResultCallback<PlaceBuffer>mUpdatePlaceDetailsCallback = new ResultCallback<PlaceBuffer>() {
        @Override
        public void onResult(@NonNull PlaceBuffer places) {
            try{
                float pickupLat,pickupLng, destLat, destLng;
                String fullAddress, fullDestionation;

                if(!places.getStatus().isSuccess()){
                    return;
                }
                final Place place = places.get(0);
                CharSequence attributions = places.getAttributions();

                if(txtPickupPoint.hasFocus()){
                    if(txtPickupPoint.getText().equals("Current Location")){
                        LatLongInfo latlongInfo = vhlocate.getLatLong(getContext(),mGoogleApiClient);
                        Geocoder AddressFromLatLong = new Geocoder(getContext(), Locale.getDefault());
                        Address currentAddress = (Address) AddressFromLatLong.getFromLocation(latlongInfo.get_Lat(),latlongInfo.get_Long(),1);
                        pickupLat =(float) currentAddress.getLatitude();
                        pickupLng = (float) currentAddress.getLongitude();
                        fullAddress = currentAddress.getAddressLine(0) + " " + currentAddress.getLocality();
                    }else {
                        pickupLat = (float) place.getLatLng().latitude;
                        pickupLng = (float) place.getLatLng().longitude;
                        fullAddress = place.getAddress().toString();
                    }
                    _passengerInfo.set_pickupLat(pickupLat);
                    _passengerInfo.set_pickupLng(pickupLng);
                    _passengerInfo.set_pickupPoint(fullAddress.trim());
                }
                if(txtDropoffPoint.hasFocus()){
                    destLat = (float) place.getLatLng().latitude;
                    destLng = (float) place.getLatLng().longitude;
                    fullDestionation = (String) place.getAddress();
                    _passengerInfo.set_destLat(destLat);
                    _passengerInfo.set_destLng(destLng);
                    _passengerInfo.set_destination(fullDestionation.toString());
                }
                if(attributions!= null){
                    Toast.makeText(getContext(),attributions.toString(),Toast.LENGTH_SHORT).show();
                }

            }catch (Exception ex){
                Toast.makeText(getContext(),ex.getMessage(),Toast.LENGTH_SHORT).show();
            }
        }
    };

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        mPlaceArrayAdapter.setmGoogleApiClient(mGoogleApiClient);
    }

    @Override
    public void onConnectionSuspended(int i) {
        mPlaceArrayAdapter.setmGoogleApiClient(null);
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Toast.makeText(getContext(), "Error: " + connectionResult.getErrorMessage(),Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v){
        try{
            final DataControl dataControl = new DataControl();
            switch (v.getId()){
                case R.id.btnSendReq:
                    _passengerInfo.set_remarks(txtRemarks.getText().toString().trim());
                    _passengerInfo.set_tip(Double.parseDouble(txtTip.getText().toString()));
                    dataControl.postBookPassenger(getContext(),_passengerInfo, new VolleyCallback() {
                        @Override
                        public void onGetPassengerInfoSuccess(PassengerInfo result) {
                        }

                        @Override
                        public void onSuccess(int response) {
                            Toast.makeText(getContext(),"Booking request sent!",Toast.LENGTH_SHORT).show();
//                            scheduleTaskExecutor = Executors.newScheduledThreadPool(5);
//                            scheduleTaskExecutor.scheduleAtFixedRate(new Runnable() {
//                                @Override
//                                public void run() {
//                                    dataControl.postAcceptRequest(getContext(), _passengerInfo.get_passengerId(), new VolleyCallback() {
//                                        @Override
//                                        public void onGetPassengerInfoSuccess(PassengerInfo result) {
//
//                                        }
//
//                                        @Override
//                                        public void onSuccess(int response) {
//                                            Toast.makeText(getContext(),"Ok",Toast.LENGTH_SHORT).show();
//                                        }
//
//                                        @Override
//                                        public void onGetDriverInfoSuccess(DriverInfo result) {
//
//                                        }
//
//                                        @Override
//                                        public void onErrorResponse(String error) {
//                                            Toast.makeText(getContext(),error,Toast.LENGTH_SHORT).show();
//                                        }
//                                    });
////                                    runOnUiThread(new Runnable() {
////                                        @Override
////                                        public void run() {
////                                            //for testing
////                                        }
////                                    });
//                                }
//                            },0,30, TimeUnit.SECONDS);
                        }

                        @Override
                        public void onGetDriverInfoSuccess(DriverInfo result) {

                        }

                        @Override
                        public void onErrorResponse(String error) {

                        }
                    });
                    break;
                default:
                    break;
            }
        }catch (Exception ex){
            Toast.makeText(getContext(),ex.getMessage(),Toast.LENGTH_SHORT).show();
        }
    }

}
