package com.isure.viahero.bookacab.BusinessObjects;
/**
 * Created by nec on 7/2/2016.
 */
public class DriverInfo {
    //region Private Variables
    private String _operatorName;
    private String _driverName;
    private String _plateNo;
    private String _pickupPoint;
    private String _destination;
    private String _remarks;
    private Float _currentLng;
    private Float _currentLat;
    private String _contactNumber;
    //endregion

    //region Operator Name
    public String get_operatorName() {
        return _operatorName;
    }

    public void set_operatorName(String _operatorName) {
        this._operatorName = _operatorName;
    }
    //endregion
    //region Driver Name
    public String get_driverName(){
        return _driverName;
    }
    public void set_driverName(String _driverName) {
        this._driverName = _driverName;
    }
    //endregion
    //region Plate Number
    public String get_plateNo() {
        return _plateNo;
    }
    public void set_plateNo(String _plateNo) {
        this._plateNo = _plateNo;
    }
    //endregion
    //region Pickup Point
    public String get_pickupPoint() {
        return _pickupPoint;
    }
    public void set_pickupPoint(String _pickupPoint) {
        this._pickupPoint = _pickupPoint;
    }
    //endregion
    //region Destination
    public String get_destination() {
        return _destination;
    }
    public void set_destination(String _destination) {
        this._destination = _destination;
    }
    //endregion
    //region Remarks
    public String get_remarks() {
        return _remarks;
    }
    public void set_remarks(String _remarks) {
        this._remarks = _remarks;
    }
    //endregion
    //region CurrentLng
    public Float get_currentLng() {
        return _currentLng;
    }
    public void set_currentLng(Float _currentLng) {
        this._currentLng = _currentLng;
    }
    //endregion
    //region CurrentLat
    public Float get_currentLat() {
        return _currentLat;
    }
    public void set_currentLat(Float _currentLat) {
        this._currentLat = _currentLat;
    }
    //endregion
    //region Contact Number
    public String get_contactNumber() {
        return _contactNumber;
    }
    public void set_contactNumber(String _contactNumber) {
        this._contactNumber = _contactNumber;
    }
    //endregion
}
