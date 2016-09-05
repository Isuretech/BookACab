package com.isure.viahero.bookacab.BusinessObjects;
/**
 * Created by nec on 6/23/2016.
 */
public class PassengerInfo {
    //region Private Variables
    private String _userName;
    private String _password;
    private String _passengerId;
    private String _passengerFirstName;
    private String _passengerMiddleName;
    private String _passengerLastName;
    private String _pickupTime;
    private String _pickupPoint;
    private String _destination;
    private String _contactNumber;
    private String _remarks;
    private double _tip;

    private Float _pickupLng;
    private Float _pickupLat;
    private Float _destLng;
    private Float _destLat;

    private double _points;


    //endregion

    //region UserName
    public String get_userName() {
        return _userName;
    }
    public void set_userName(String _userName) {
        this._userName = _userName;
    }
    //endregion
    //region Password
    public String get_password() {
        return _password;
    }
    public void set_password(String _password) {
        this._password = _password;
    }
    //endregion
    //region Passenger ID
    public String get_passengerId() {
        return _passengerId;
    }
    public void set_passengerId(String _passengerId) {
        this._passengerId = _passengerId;
    }
    //endregion
    //region Passenger First Name
    public  String get_passengerFirstName(){return _passengerFirstName;}
    public void set_passengerFirstName(String _passengerFirstName) {
        this._passengerFirstName = _passengerFirstName;
    }
    //endregion
    //region Passenger Middle Name
    public String get_passengerMiddleName(){
        return _passengerMiddleName;
    }
    public void set_passengerMiddleName(String _passengerMiddleName){
        this._passengerMiddleName = _passengerMiddleName;
    }
    //endregion
    //region Passenger Last Name
    public String get_passengerLastName(){
        return _passengerLastName;
    }
    public void set_passengerLastName(String _passengerLastName){
        this._passengerLastName = _passengerLastName;
    }
    //endregion
    //region Pickup Time
    public String get_pickupTime() {
        return _pickupTime;
    }
    public void set_pickupTime(String _pickupTime) {
        this._pickupTime = _pickupTime;
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
    //region Contact Number
    public String get_contactNumber() {
        return _contactNumber;
    }
    public void set_contactNumber(String _contactNumber) {
        this._contactNumber = _contactNumber;
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
    //region Tip
    public double get_tip() {
        return _tip;
    }
    public void set_tip(double _tip) {
        this._tip = _tip;
    }
    //endregion
    //region Pickup LNG
    public Float get_pickupLng() {
        return _pickupLng;
    }
    public void set_pickupLng(Float _pickupLng) {
        this._pickupLng = _pickupLng;
    }
    //endregion
    //region Pickup LAT
    public Float get_pickupLat() {
        return _pickupLat;
    }
    public void set_pickupLat(Float _pickupLat) {
        this._pickupLat = _pickupLat;
    }
    //endregion
    //region Destination LNG
    public Float get_destLng() {
        return _destLng;
    }
    public void set_destLng(Float _destLng) {
        this._destLng = _destLng;
    }
    //endregion
    //region Destination LAT
    public Float get_destLat() {
        return _destLat;
    }
    public void set_destLat(Float _destLat) {
        this._destLat = _destLat;
    }
    //endregion

    //region Points
    public double get_points() {
        return _points;
    }
    public void set_points(double _points) {
        this._points = _points;
    }
    //endregion
}
