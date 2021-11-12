package com.spectra.fieldforce.model.gpon.request;

public class ResendNavRequest {
    private String Authkey;
    private String Action;
    private String WCRguidId;
    private String Segment;
    private String ItemType;
    private String IRguid;
    public ResendNavRequest(String authkey, String action, String WCRguidId, String segment, String itemType,String IRguid) {
        Authkey = authkey;
        Action = action;
        this.WCRguidId = WCRguidId;
        Segment = segment;
        ItemType = itemType;
        this.IRguid = IRguid;
    }



}
