package com.spectra.fieldforce.model.gpon.request;

public class UpdateFmsRequest {
    private String Action;
    private String Authkey;
    private String FmsFirst;
    private String FmsSecond;
    private String FmsPODno;

    public void setAction(String action) {
        Action = action;
    }

    public void setAuthkey(String authkey) {
        Authkey = authkey;
    }

    public void setFmsFirst(String fmsFirst) {
        FmsFirst = fmsFirst;
    }

    public void setFmsSecond(String fmsSecond) {
        FmsSecond = fmsSecond;
    }

    public void setFmsPODno(String fmsPODno) {
        FmsPODno = fmsPODno;
    }

    public void setFmsPortCX(String fmsPortCX) {
        FmsPortCX = fmsPortCX;
    }

    public void setFmsPortPOD(String fmsPortPOD) {
        FmsPortPOD = fmsPortPOD;
    }

    public void setWCRguidId(String WCRguidId) {
        this.WCRguidId = WCRguidId;
    }

    private String FmsPortCX;
    private String FmsPortPOD;
    private String WCRguidId;



}
