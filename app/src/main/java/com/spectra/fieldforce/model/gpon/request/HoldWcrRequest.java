package com.spectra.fieldforce.model.gpon.request;

public class HoldWcrRequest {
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

    public void setCategory(String category) {
        Category = category;
    }

    public void setReason(String reason) {
        Reason = reason;
    }

    private String Authkey;
    private String Action;
    private String WCRguidId;
    private String Segment;
    private String Category;
    private String Reason;

    public void setIRguidId(String IRguidId) {
        this.IRguidId = IRguidId;
    }

    private String IRguidId;

}

