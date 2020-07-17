package com.spectra.fieldforce.Model;

public class StarttimeRequest {
    String Authkey = "";
    String Action = "";
    String SrNumber = "";
    String StartLongitude = "";
    String StartLatitude = "";
    String StartAddress = "";
    String StartTime = "";
    String EngEmailId = "";

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
        return SrNumber;
    }

    public void setSrNumber(String srNumber) {
        this.SrNumber = srNumber;
    }

    public String getStartLongitude() {
        return StartLongitude;
    }

    public void setStartLongitude(String startLongitude) {
        this.StartLongitude = startLongitude;
    }

    public String getStartLatitude() {
        return StartLatitude;
    }

    public void setStartLatitude(String startLatitude) {
        this.StartLatitude = startLatitude;
    }

    public String getStartAddress() {
        return StartAddress;
    }

    public void setStartAddress(String startAddress) {
        this.StartAddress = startAddress;
    }

    public String getStartTime() {
        return StartTime;
    }

    public void setStartTime(String startTime) {
        this.StartTime = startTime;
    }

    public String getEngEmailId() {
        return EngEmailId;
    }

    public void setEngEmailId(String engEmailId) {
        this.EngEmailId = engEmailId;
    }

}
