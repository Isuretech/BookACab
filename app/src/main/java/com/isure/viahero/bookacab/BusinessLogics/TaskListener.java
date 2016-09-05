package com.isure.viahero.bookacab.BusinessLogics;
import com.isure.viahero.bookacab.BusinessObjects.DriverInfo;
/**
 * Created by nec on 8/31/2016.
 */
public interface TaskListener {
    void onTaskGetCurrenttripSuccess(DriverInfo driverInfo);
}
