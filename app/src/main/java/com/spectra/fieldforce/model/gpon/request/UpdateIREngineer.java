package com.spectra.fieldforce.model.gpon.request;

public class UpdateIREngineer {

    private String Authkey;
    private String Action;
    private String IRguid;

    public void setAuthkey(String authkey) {
        Authkey = authkey;
    }

    public void setAction(String action) {
        Action = action;
    }

    public void setIRguid(String IRguid) {
        this.IRguid = IRguid;
    }

    public void setEngName(String engName) {
        EngName = engName;
    }

    public void setOTP(String OTP) {
        this.OTP = OTP;
    }

    public void setInstattionDate(String instattionDate) {
        InstattionDate = instattionDate;
    }

    private String EngName;
    private String OTP;
    private String InstattionDate;

}
