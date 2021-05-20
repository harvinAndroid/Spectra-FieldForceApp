package com.spectra.fieldforce.model.gpon.request;

public class UpdatePostHostIrQualityReq {
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

    public String getSpeed() {
        return Speed;
    }

    public void setSpeed(String speed) {
        Speed = speed;
    }

    public String getCable() {
        return Cable;
    }

    public void setCable(String cable) {
        Cable = cable;
    }

    public String getFace() {
        return Face;
    }

    public void setFace(String face) {
        Face = face;
    }

    public String getONT() {
        return ONT;
    }

    public void setONT(String ONT) {
        this.ONT = ONT;
    }

    public String getWifi() {
        return Wifi;
    }

    public void setWifi(String wifi) {
        Wifi = wifi;
    }

    public String getSelfCareAntiVirus() {
        return SelfCareAntiVirus;
    }

    public void setSelfCareAntiVirus(String selfCareAntiVirus) {
        SelfCareAntiVirus = selfCareAntiVirus;
    }

    private String Authkey,Action,WCRguidId,Speed,Cable,Face,ONT,Wifi,SelfCareAntiVirus;

}
