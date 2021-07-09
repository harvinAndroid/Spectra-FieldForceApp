package com.spectra.fieldforce.model.gpon.request;

public class UpdateQualityParamRequest {
    private String Authkey;
    private String Action;
    private String WCRguidId;
    private String Speed;
    private String Cable;
    private String Face;
    private String ONT;
    private String Wifi;
    private String SelfCare;

    public void setIRguid(String IRguid) {
        this.IRguid = IRguid;
    }

    private String IRguid;

    public void setAuthkey(String authkey) {
        Authkey = authkey;
    }

    public void setAction(String action) {
        Action = action;
    }

    public void setWCRguidId(String WCRguidId) {
        this.WCRguidId = WCRguidId;
    }

    public void setSpeed(String speed) {
        Speed = speed;
    }

    public void setCable(String cable) {
        Cable = cable;
    }

    public void setFace(String face) {
        Face = face;
    }

    public void setONT(String ONT) {
        this.ONT = ONT;
    }

    public void setWifi(String wifi) {
        Wifi = wifi;
    }

    public void setSelfCare(String selfCare) {
        SelfCare = selfCare;
    }

    public void setAntiVirus(String antiVirus) {
        AntiVirus = antiVirus;
    }

    private String AntiVirus;

}

