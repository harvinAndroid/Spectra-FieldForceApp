package com.spectra.fieldforce.Model;

public class StarttimeRequest {
    String Authkey = "";
    String Action = "";
    String srNumber = "";
    String startLongitude = "";
    String startLatitude = "";
    String startAddress = "";
    String startTime = "";
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

    public String getStartLongitude() {
        return startLongitude;
    }

    public void setStartLongitude(String startLongitude) {
        this.startLongitude = startLongitude;
    }

    public String getStartLatitude() {
        return startLatitude;
    }

    public void setStartLatitude(String startLatitude) {
        this.startLatitude = startLatitude;
    }

    public String getStartAddress() {
        return startAddress;
    }

    public void setStartAddress(String startAddress) {
        this.startAddress = startAddress;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEngEmailId() {
        return engEmailId;
    }

    public void setEngEmailId(String engEmailId) {
        this.engEmailId = engEmailId;
    }

}
