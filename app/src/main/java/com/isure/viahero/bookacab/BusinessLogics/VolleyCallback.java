package com.isure.viahero.bookacab.BusinessLogics;
import com.isure.viahero.bookacab.BusinessObjects.DriverInfo;
import com.isure.viahero.bookacab.BusinessObjects.PassengerInfo;

/**
 * Created by nec on 6/25/2016.
 */
public interface VolleyCallback {
    void onGetPassengerInfoSuccess(PassengerInfo result);
    void onSuccess();
    void onGetDriverInfoSuccess(DriverInfo result);
    void onErrorResponse(String error);
}
