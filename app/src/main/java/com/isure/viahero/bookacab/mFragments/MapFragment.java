package com.isure.viahero.bookacab.mFragments;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
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
import android.widget.RelativeLayout;
import android.widget.Toast;


import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.BooleanResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceBuffer;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
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
import com.isure.viahero.bookacab.vhMethods.vhMapInitialize;
import com.isure.viahero.bookacab.vhMethods.vhMapStateManager;

import java.util.Locale;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class MapFragment extends Fragment implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,GoogleApiClient.OnConnectionFailedListener,
        View.OnClickListener {

    //Map fragment constructor
    public MapFragment(PassengerInfo passengerInfo) {
        _passengerInfo = passengerInfo;
    }

    //region Variable declarations
    private static final int GOOGLE_API_CLIENT = 0;
    private AutoCompleteTextView txtPickupPoint;
    private AutoCompleteTextView txtDropoffPoint;
    private EditText txtRemarks, txtTip;
    private Button btnSendReq;
    private PlaceArrayAdapter mPlaceArrayAdapter;
    PassengerInfo _passengerInfo;
    vhLocate vhlocate = new vhLocate();

    private View rootView;
    GoogleMap mMap;
    GoogleApiClient mGoogleApiClient;
    MapView mMapView;
    vhMapInitialize initializeMap = new vhMapInitialize();
    vhLocate vhMapLocation = new vhLocate();

    private ScheduledExecutorService scheduleTaskExecutor;

    //This line is for auto complete of places.
    private static final LatLngBounds BOUNDS_MOUNTAIN_VIEW =
            new LatLngBounds(new LatLng(4.6145711,119.6272661),new LatLng(19.966096,124.173694));
    //endregion

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        try {
            if (initializeMap.servicesOK(getContext())) {
                rootView = inflater.inflate(R.layout.fragment_map, container, false);
                MapsInitializer.initialize(getActivity().getApplicationContext());
                mMapView = (MapView) rootView.findViewById(R.id.map);
                mMapView.onCreate(savedInstanceState);
                mMapView.onResume();
                mMapView.getMapAsync(this);
                View locationButton = ((View)mMapView.findViewById(Integer.parseInt("1")).getParent()).findViewById(Integer.parseInt("2"));
                RelativeLayout.LayoutParams rlp = (RelativeLayout.LayoutParams) locationButton.getLayoutParams();
                rlp.addRule(RelativeLayout.ALIGN_PARENT_TOP,0);
                rlp .addRule(RelativeLayout.ALIGN_PARENT_TOP,RelativeLayout.TRUE);
                rlp.setMargins(0, 180, 180, 0);

                if(mMapView!= null){
                    mMap = mMapView.getMap();
                    if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                            ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        return rootView;
                    }
                    mMap.setMyLocationEnabled(true);

                    mGoogleApiClient = new GoogleApiClient.Builder(getContext())
                            .addApi(Places.GEO_DATA_API)
                            .addApi(Places.PLACE_DETECTION_API)
                            .addApi(LocationServices.API)
                            .enableAutoManage(getActivity(), GOOGLE_API_CLIENT, this)
                            .addConnectionCallbacks(this)
                            .addOnConnectionFailedListener(this)
                            .build();

                    mPlaceArrayAdapter = new PlaceArrayAdapter(getContext(),android.R.layout.simple_list_item_1,BOUNDS_MOUNTAIN_VIEW,null);
                    mPlaceArrayAdapter = new PlaceArrayAdapter(getContext(),android.R.layout.simple_list_item_1,BOUNDS_MOUNTAIN_VIEW,null);
                    txtPickupPoint = (AutoCompleteTextView) rootView.findViewById(R.id.autoCompleteTextView);
                    txtDropoffPoint = (AutoCompleteTextView) rootView.findViewById(R.id.autoCompleteTextView1);
                    txtPickupPoint.setThreshold(3);
                    txtDropoffPoint.setThreshold(3);
                    txtPickupPoint.setOnItemClickListener(mAutoCompleteClickListener);
                    txtDropoffPoint.setOnItemClickListener(mAutoCompleteClickListener);
                    txtPickupPoint.setAdapter(mPlaceArrayAdapter);
                    txtDropoffPoint.setAdapter(mPlaceArrayAdapter);

                    btnSendReq = (Button) rootView.findViewById(R.id.btnSendReq);
                    btnSendReq.setOnClickListener(this);
                    txtRemarks = (EditText) rootView.findViewById(R.id.txtRemarks);
                    txtTip = (EditText) rootView.findViewById(R.id.txtTip);
                }else{
                    Toast.makeText(getContext(),"No Map to view!",Toast.LENGTH_SHORT).show();
                }
            }
        }catch (Exception ex){
            Toast.makeText(getContext(),ex.getMessage().toString(),Toast.LENGTH_SHORT).show();
        }
        return rootView;
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
        mGoogleApiClient.stopAutoManage(getActivity());
        mGoogleApiClient.disconnect();
    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
        vhMapStateManager mgr = new vhMapStateManager(getContext());
        CameraPosition position = mgr.getSavedCameraPosistion();
        try{
            if(position != null){
                CameraUpdate update = CameraUpdateFactory.newCameraPosition(position);
                mMap.moveCamera(update);
            }
        }catch (Exception ex){
            Toast.makeText(getContext(),ex.getMessage().toString(),Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mMapView.onSaveInstanceState(outState);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        try{
            vhMapLocation.gotoCurrentLocation(getContext(),mGoogleApiClient,mMap);
            mPlaceArrayAdapter.setmGoogleApiClient(mGoogleApiClient);
        }catch (Exception ex){
            Toast.makeText(getContext(), ex.getMessage().toString(),Toast.LENGTH_SHORT).show();
        }
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
    public void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }

    @Override
    public void onStop() {
        super.onStop();
        mGoogleApiClient.disconnect();
        vhMapStateManager mgr = new vhMapStateManager(getContext());
        mgr.saveMapState(mMap);
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
                            Toast.makeText(getContext(),"Booking request posted!",Toast.LENGTH_SHORT).show();
                            scheduleTaskExecutor = Executors.newScheduledThreadPool(5);
                            scheduleTaskExecutor.scheduleAtFixedRate(new Runnable() {
                                @Override
                                public void run() {
                                    dataControl.postAcceptRequest(getContext(), _passengerInfo.get_passengerId(), new VolleyCallback() {
                                        @Override
                                        public void onGetPassengerInfoSuccess(PassengerInfo result) {

                                        }
                                        @Override
                                        public void onSuccess(int response) {
                                            //condition here if the return value is not null
                                            //scheduleTaskExecutor.shutdown();
                                            Toast.makeText(getContext(),response,Toast.LENGTH_SHORT).show();
                                            if(response == 1 ){
                                                Toast.makeText(getContext(),"We have booked you a cab!",Toast.LENGTH_SHORT).show();
                                                scheduleTaskExecutor.shutdown();
                                            }
                                        }
                                        @Override
                                        public void onGetDriverInfoSuccess(DriverInfo result) {

                                        }
                                        @Override
                                        public void onErrorResponse(String error) {
                                            Toast.makeText(getContext(),error,Toast.LENGTH_SHORT).show();
                                        }
                                    });
//                                    runOnUiThread(new Runnable() {
//                                        @Override
//                                        public void run() {
//                                            //for testing
//                                        }
//                                    });
                                }
                            },0,30, TimeUnit.SECONDS);
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
    //region Places auto-complete related methods
    private AdapterView.OnItemClickListener mAutoCompleteClickListener = new AdapterView.OnItemClickListener(){
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id){
            final PlaceArrayAdapter.PlaceAutocomplete item = mPlaceArrayAdapter.getItem(position);
            final String placeId = String.valueOf(item.placeId);
            PendingResult<PlaceBuffer> placeResult = Places.GeoDataApi.getPlaceById(mGoogleApiClient,placeId);
            placeResult.setResultCallback(mUpdatePlaceDetailsCallback);
        }
    };
    private ResultCallback<PlaceBuffer> mUpdatePlaceDetailsCallback = new ResultCallback<PlaceBuffer>() {
        @Override
        public void onResult(@NonNull PlaceBuffer places) {
            try{
                float pickupLat,pickupLng, destLat, destLng;
                String fullAddress, fullDestination;

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
                    fullDestination = (String) place.getAddress();
                    _passengerInfo.set_destLat(destLat);
                    _passengerInfo.set_destLng(destLng);
                    _passengerInfo.set_destination(fullDestination.toString());
                }
                if(attributions!= null){
                    Toast.makeText(getContext(),attributions.toString(),Toast.LENGTH_SHORT).show();
                }

            }catch (Exception ex){
                Toast.makeText(getContext(),ex.getMessage(),Toast.LENGTH_SHORT).show();
            }
        }
    };
    //endregion
}