package com.spectra.fieldforce.model.gpon.request;

public class BucketListRequest {
    private String Authkey;
    private String Action;

    public void setEngineerID(String engineerID) {
        EngineerID = engineerID;
    }

    private String EngineerID;

    public void setAuthkey(String authkey) {
        Authkey = authkey;
    }

    public void setAction(String action) {
        Action = action;
    }

    public void setVendorCode(String vendorCode) {
        VendorCode = vendorCode;
    }

    private String VendorCode;

}
