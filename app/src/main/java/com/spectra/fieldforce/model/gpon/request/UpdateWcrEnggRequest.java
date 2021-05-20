package com.spectra.fieldforce.model.gpon.request;

public class UpdateWcrEnggRequest {
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

    public String getWCRguidId() {
        return WCRguidId;
    }

    public void setWCRguidId(String WCRguidId) {
        this.WCRguidId = WCRguidId;
    }

    public String getEngName() {
        return EngName;
    }

    public void setEngName(String engName) {
        EngName = engName;
    }

    public String getInstcode() {
        return Instcode;
    }

    public void setInstcode(String instcode) {
        Instcode = instcode;
    }

    private String Authkey,Action,WCRguidId,EngName,Instcode;
}


