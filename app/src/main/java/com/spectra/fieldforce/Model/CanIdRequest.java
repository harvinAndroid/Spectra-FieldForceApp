package com.spectra.fieldforce.Model;

public class CanIdRequest {

    private String Authkey;

   private String Action;

    private String canID;

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
