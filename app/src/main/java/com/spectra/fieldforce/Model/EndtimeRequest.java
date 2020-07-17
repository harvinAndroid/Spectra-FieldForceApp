package com.spectra.fieldforce.Model;

public class EndtimeRequest {
    String Authkey = "";
    String Action = "";
    String SrNumber = "";
    String EndLongitude = "";
    String EndLatitude = "";
    String EndAddress = "";
    String EndTime = "";
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

    public String getEndLongitude() {
        return EndLongitude;
    }

    public void setEndLongitude(String endLongitude) {
        this.EndLongitude = endLongitude;
    }

    public String getEndLatitude() {
        return EndLatitude;
    }

    public void setEndLatitude(String endLatitude) {
        this.EndLatitude = endLatitude;
    }

    public String getEndAddress() {
        return EndAddress;
    }

    public void setEndAddress(String endAddress) {
        this.EndAddress = endAddress;
    }

    public String getEndTime() {
        return EndTime;
    }

    public void setEndTime(String endTime) {
        this.EndTime = endTime;
    }

    public String getEngEmailId() {
        return EngEmailId;
    }

    public void setEngEmailId(String engEmailId) {
        this.EngEmailId = engEmailId;
    }


}
