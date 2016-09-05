package com.isure.viahero.bookacab.mFragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.isure.viahero.bookacab.BusinessLogics.DataControl;
import com.isure.viahero.bookacab.BusinessLogics.TaskListener;
import com.isure.viahero.bookacab.BusinessLogics.VolleyCallback;
import com.isure.viahero.bookacab.BusinessObjects.DriverInfo;
import com.isure.viahero.bookacab.BusinessObjects.PassengerInfo;
import com.isure.viahero.bookacab.R;
import com.isure.viahero.bookacab.vhTasks.vhTasks;

/**
 * A simple {@link Fragment} subclass.
 */
public class CurrentBookingFragment extends Fragment{
    View rootView;
    TextView txtOperatorName;
    TextView txtDriverName;
    TextView txtPlateNo;
    TextView txtPick;
    TextView txtDrop;
    TextView txtRemarks;


    PassengerInfo passengerInfo;
    //final DriverInfo[] _driverInfo = new DriverInfo[1];

    public CurrentBookingFragment(PassengerInfo _passengerInfo) {
        passengerInfo = _passengerInfo;
    }
    private void Initialize(){
        txtOperatorName = (TextView) rootView.findViewById(R.id.txtOperatorName);
        txtDriverName= (TextView) rootView.findViewById(R.id.txtDriverName);
        txtPlateNo= (TextView) rootView.findViewById(R.id.txtPlateNo);
        txtPick= (TextView) rootView.findViewById(R.id.txtPick);
        txtDrop= (TextView) rootView.findViewById(R.id.txtDrop);
        txtRemarks= (TextView) rootView.findViewById(R.id.txtRemarks);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        DataControl dataControl = new DataControl();
        rootView = inflater.inflate(R.layout.fragment_current_booking, container, false);
        Initialize();

        dataControl.getCurrentBooking(getContext(), passengerInfo.get_passengerId(), new VolleyCallback() {
            @Override
            public void onGetPassengerInfoSuccess(PassengerInfo result) {

            }

            @Override
            public void onSuccess() {

            }

            @Override
            public void onGetDriverInfoSuccess(DriverInfo result) {
                txtOperatorName.setText(result.get_operatorName());
                txtDriverName.setText(result.get_driverName());
                txtPlateNo.setText(result.get_plateNo());
                txtPick.setText(result.get_pickupPoint());
                txtDrop.setText(result.get_destination());
                txtRemarks.setText(result.get_remarks());

            }

            @Override
            public void onErrorResponse(String error) {

            }
        });
//        vhTasks.TaskGetCurrenttrip mCurrentTrip = new vhTasks.TaskGetCurrenttrip(getContext(), new TaskListener() {
//            @Override
//            public void onTaskGetCurrenttripSuccess(DriverInfo driverInfo) {
//                _driverInfo[0] =driverInfo;
//                txtOperatorName.setText(driverInfo.get_operatorName());
//                txtDriverName.setText(driverInfo.get_driverName());
//                txtPick.setText(driverInfo.get_pickupPoint());
//                txtDrop.setText(driverInfo.get_destination());
//                txtRemarks.setText(driverInfo.get_remarks());
//            }
//        });
//        mCurrentTrip.execute(passengerInfo);
        return rootView;
    }
}
