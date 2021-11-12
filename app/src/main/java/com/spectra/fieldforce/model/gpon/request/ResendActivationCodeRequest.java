package com.spectra.fieldforce.model.gpon.request;

public class ResendActivationCodeRequest {
    private String Authkey;
    private String Action;
    private String IsIR;
    private String GUID;

    public void setId(String id) {
        Id = id;
    }

    private String Id;

    public void setAuthkey(String authkey) {
        Authkey = authkey;
    }

    public void setAction(String action) {
        Action = action;
    }

    public void setIsIR(String isIR) {
        IsIR = isIR;
    }

    public void setGUID(String GUID) {
        this.GUID = GUID;
    }

    public void setType(String type) {
        Type = type;
    }

    private String Type;


}

