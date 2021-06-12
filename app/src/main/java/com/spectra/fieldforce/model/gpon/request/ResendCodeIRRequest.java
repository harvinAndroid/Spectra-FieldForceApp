package com.spectra.fieldforce.model.gpon.request;

public class ResendCodeIRRequest {
    private String Authkey;

    public void setAuthkey(String authkey) {
        Authkey = authkey;
    }

    public void setAction(String action) {
        Action = action;
    }

    public void setIRguid(String IRguid) {
        this.IRguid = IRguid;
    }

    public void setSegment(String segment) {
        Segment = segment;
    }

    public void setItemType(String itemType) {
        ItemType = itemType;
    }

    private String Action;
    private String IRguid;
    private String Segment;
    private String ItemType;

}
