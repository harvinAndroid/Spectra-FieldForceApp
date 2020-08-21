package com.spectra.fieldforce.Model;

public class SendChangeBinRequest {
    String Authkey;
    String Action;
    String SrNumber;

    public String getBinId() {
        return BinId;
    }

    public void setBinId(String binId) {
        BinId = binId;
    }

    String BinId;

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
        SrNumber = srNumber;
    }
}
