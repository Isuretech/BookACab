package com.isure.viahero.bookacab;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.isure.viahero.bookacab.BusinessLogics.DataControl;
import com.isure.viahero.bookacab.BusinessLogics.VolleyCallback;
import com.isure.viahero.bookacab.BusinessObjects.DriverInfo;
import com.isure.viahero.bookacab.BusinessObjects.PassengerInfo;
import com.isure.viahero.bookacab.mFragments.AcceptRequestFragment;
import com.isure.viahero.bookacab.mFragments.CurrentBookingFragment;
import com.isure.viahero.bookacab.mFragments.Login;
import com.isure.viahero.bookacab.mFragments.MapFragment;
import com.isure.viahero.bookacab.mFragments.MyTransactionsFragment;
import com.isure.viahero.bookacab.mFragments.NewBookingFragment;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity implements
        NavigationView.OnNavigationItemSelectedListener {
    DataControl dataControl = new DataControl();
    PassengerInfo passengerInfo = new PassengerInfo();

    private ScheduledExecutorService scheduleTaskExecutor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportParentActivityIntent();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawer,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        dataControl.getPassengerInfo(getApplicationContext(), getIntent().getStringExtra("Username"), getIntent().getStringExtra("Password"), new VolleyCallback() {
            @Override
            public void onGetPassengerInfoSuccess(PassengerInfo result) {
                passengerInfo = result;
                NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
                navigationView.setNavigationItemSelectedListener(MainActivity.this);
                View headView = navigationView.getHeaderView(0);
                ((TextView)headView.findViewById(R.id.txtUser)).setText(passengerInfo.get_passengerFirstName());

                passengerInfo.set_userName(getIntent().getStringExtra("Username"));
                final MapFragment mapFragment = new MapFragment(passengerInfo);
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,mapFragment).commit();
            }

            @Override
            public void onSuccess(int response) {

            }

            @Override
            public void onGetDriverInfoSuccess(DriverInfo result) {

            }

            @Override
            public void onErrorResponse(String error) {

            }
        });
        //region for testing code in this region
//        scheduleTaskExecutor = Executors.newScheduledThreadPool(5);
//        scheduleTaskExecutor.scheduleAtFixedRate(new Runnable() {
//            @Override
//            public void run() {
//                dataControl.postAcceptRequest(getApplicationContext(), passengerInfo.get_passengerId(), new VolleyCallback() {
//                    @Override
//                    public void onGetPassengerInfoSuccess(PassengerInfo result) {
//
//                    }
//
//                    @Override
//                    public void onSuccess(int response) {
//                        if(response == 1 ){
//                            Toast.makeText(getApplication(),"Ok",Toast.LENGTH_SHORT).show();
//                        }
//                    }
//
//                    @Override
//                    public void onGetDriverInfoSuccess(DriverInfo result) {
//
//                    }
//
//                    @Override
//                    public void onErrorResponse(String error) {
//                        Toast.makeText(getApplication(),error,Toast.LENGTH_SHORT).show();
//                    }
//                });
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        //for testing
//                    }
//                });
//            }
//        },0,30, TimeUnit.SECONDS);
        //endregion
    }


    @Override
    public void onBackPressed() {
        scheduleTaskExecutor.shutdown();
        finish();
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        MapFragment mapFragment = new MapFragment(passengerInfo);
        switch (id){
            case R.id.nav_home:
                Toast.makeText(this, "Home", Toast.LENGTH_SHORT).show();
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,mapFragment).commit();
                break;
//            case R.id.nav_booking:
//                Toast.makeText(this, "New Booking", Toast.LENGTH_SHORT).show();
//                NewBookingFragment newbookingFragment = new NewBookingFragment(passengerInfo);
//                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,newbookingFragment).commit();
//                break;
//            case R.id.nav_acceptrequest:
//                Toast.makeText(this, "Accept Request", Toast.LENGTH_SHORT).show();
//                AcceptRequestFragment acceptRequestFragment = new AcceptRequestFragment();
//                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,acceptRequestFragment).commit();
//                break;
            case R.id.nav_currenttrip:
                Toast.makeText(this, "Current trip", Toast.LENGTH_SHORT).show();
                CurrentBookingFragment currentBookingFragment = new CurrentBookingFragment(passengerInfo);
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,currentBookingFragment).commit();
                break;
            case R.id.nav_transactions:
                Toast.makeText(this, "my transactions", Toast.LENGTH_SHORT).show();
                MyTransactionsFragment myTransactionsFragment = new MyTransactionsFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,myTransactionsFragment).commit();
                break;
            case R.id.nav_editprofiles:
                Toast.makeText(this, "Edit profile", Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_logout:
                Toast.makeText(this, "Logout", Toast.LENGTH_SHORT).show();
                break;
            default:
                Toast.makeText(this, "Home", Toast.LENGTH_SHORT).show();
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,mapFragment).commit();
                break;
        }
        return true;
    }
}
