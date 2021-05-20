package com.spectra.fieldforce.model.gpon.request;

public class AccountInfoRequest {

    private String Authkey;

   private String Action;

    public String getCanId() {
        return CanId;
    }

    public void setCanId(String canId) {
        CanId = canId;
    }

    private String CanId;

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

}
