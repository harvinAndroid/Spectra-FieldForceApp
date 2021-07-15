package com.spectra.fieldforce.model.gpon.request;

public class IRCompleteRequest {
    private String Authkey;
    private String Action;
    private String IRguid;
    private String MACShared;
    private String Remarks;
    private String Ishold;
    private String CanId;
    private String Source;
    private String Lat;

    public void setSource(String source) {
        Source = source;
    }

    public void setLat(String lat) {
        Lat = lat;
    }

    public void setLong(String aLong) {
        Long = aLong;
    }

    private String Long;


    public void setAuthkey(String authkey) {
        Authkey = authkey;
    }

    public void setAction(String action) {
        Action = action;
    }

    public void setIRguid(String IRguid) {
        this.IRguid = IRguid;
    }

    public void setMACShared(String MACShared) {
        this.MACShared = MACShared;
    }

    public void setRemarks(String remarks) {
        Remarks = remarks;
    }

    public void setIshold(String ishold) {
        Ishold = ishold;
    }

    public void setCanId(String canId) {
        CanId = canId;
    }
}
