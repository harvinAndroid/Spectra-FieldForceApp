package com.spectra.fieldforce.model.gpon.request;

public class ResendActivationCodeRequest {
    private String Authkey,Action,WCRguidId,Segment,ItemType;

    public void setAuthkey(String authkey) {
        Authkey = authkey;
    }

    public void setAction(String action) {
        Action = action;
    }

    public void setWCRguidId(String WCRguidId) {
        this.WCRguidId = WCRguidId;
    }

    public void setSegment(String segment) {
        Segment = segment;
    }

    public void setItemType(String itemType) {
        ItemType = itemType;
    }
}
