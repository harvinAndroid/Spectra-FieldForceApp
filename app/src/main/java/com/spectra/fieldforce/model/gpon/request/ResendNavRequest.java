package com.spectra.fieldforce.model.gpon.request;

public class ResendNavRequest {
    private String Authkey;
    private String Action;
    private String WCRguidId;
    private String Segment;
    private String ItemType;
    public ResendNavRequest(String authkey, String action, String WCRguidId, String segment, String itemType) {
        Authkey = authkey;
        Action = action;
        this.WCRguidId = WCRguidId;
        Segment = segment;
        ItemType = itemType;
    }



}
