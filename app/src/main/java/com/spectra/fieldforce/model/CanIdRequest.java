package com.spectra.fieldforce.model;

public class CanIdRequest {

    private String Authkey;

   private String Action;

    private String canID;

    public String getSrNumber() {
        return SrNumber;
    }

    public void setSrNumber(String srNumber) {
        SrNumber = srNumber;
    }

    private String SrNumber;

    public String getAuthkey () {
        return Authkey;
    }

    public void setAuthkey ( String authkey ) {
        Authkey = authkey;
    }

    public String getAction () {
        return Action;
    }

    public void setAction ( String action ) {
        Action = action;
    }
    public String getCainId ( String canID ) {
        return canID;
    }
    public void setCanId ( String canID ) {
        this.canID = canID;
    }
}
