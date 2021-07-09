package com.spectra.fieldforce.model.gpon.request;

public class UpdateQualityParamIRequest {
    private String Authkey,Action,IRguid,AntiVirus,SelfCare,DNS,MRTG,IP,Speed;

    public void setAuthkey(String authkey) {
        Authkey = authkey;
    }

    public void setAction(String action) {
        Action = action;
    }

    public void setIRguid(String IRguid) {
        this.IRguid = IRguid;
    }

    public void setAntiVirus(String antiVirus) {
        AntiVirus = antiVirus;
    }

    public void setSelfCare(String selfCare) {
        SelfCare = selfCare;
    }

    public void setDNS(String DNS) {
        this.DNS = DNS;
    }

    public void setMRTG(String MRTG) {
        this.MRTG = MRTG;
    }

    public void setIP(String IP) {
        this.IP = IP;
    }

    public void setSpeed(String speed) {
        Speed = speed;
    }
}
