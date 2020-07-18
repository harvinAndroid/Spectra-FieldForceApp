package com.spectra.fieldforce.Model;

public class RCRequest {
    String Authkey = "";
    String Action = "";
    String RConeId = "";
    String RCtwoId = "";
    String CanId = "";

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

    public String getRC1() {
        return RConeId;
    }

    public void setRC1(String rc1) {
        RConeId = rc1;
    }

    public String getRC2() {
        return RCtwoId;
    }

    public void setRC2(String rc2) {
        RCtwoId = rc2;
    }

    public String getCanId() {
        return CanId;
    }

    public void setCanId(String canId) {
        this.CanId = canId;
    }
}
