package com.spectra.fieldforce.model.gpon.request;

public class IRCompleteRequest {
    private String Authkey,Action,IRguid,MACShared,Remarks,Ishold,CanId;

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
