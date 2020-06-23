package com.spectra.fieldforce.Model;

public class EndtimeRequest {

    String Authkey = "";
    String Action = "";
    String srNumber = "";
    String endLongitude = "";
    String endLatitude = "";
    String endAddress = "";
    String endTime = "";
    String engEmailId = "";

    public String getAuthkey() {
        return Authkey;
    }

    public void setAuthkey(String authkey) {
        Authkey = authkey;
    }

    public String getAction() {
        return Action;
    }

    public void setAction(String action) {
        Action = action;
    }

    public String getSrNumber() {
        return srNumber;
    }

    public void setSrNumber(String srNumber) {
        this.srNumber = srNumber;
    }

    public String getEndLongitude() {
        return endLongitude;
    }

    public void setEndLongitude(String endLongitude) {
        this.endLongitude = endLongitude;
    }

    public String getEndLatitude() {
        return endLatitude;
    }

    public void setEndLatitude(String endLatitude) {
        this.endLatitude = endLatitude;
    }

    public String getEndAddress() {
        return endAddress;
    }

    public void setEndAddress(String endAddress) {
        this.endAddress = endAddress;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getEngEmailId() {
        return engEmailId;
    }

    public void setEngEmailId(String engEmailId) {
        this.engEmailId = engEmailId;
    }




}
