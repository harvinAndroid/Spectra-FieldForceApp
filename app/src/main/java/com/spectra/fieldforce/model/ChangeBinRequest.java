package com.spectra.fieldforce.model;

public class ChangeBinRequest {
    private String Authkey;
    private String Action;
    private String SrNumber;

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    private String Status;

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
