package com.spectra.fieldforce.model.gpon.request;

public class WcrCompleteRequest {
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

    public void setIsHold(String isHold) {
        IsHold = isHold;
    }

    public void setRemarks(String remarks) {
        Remarks = remarks;
    }

    public void setProductSegment(String productSegment) {
        this.productSegment = productSegment;
    }

    public void setCanId(String canId) {
        CanId = canId;
    }

    private String Authkey,Action,WCRguidId,Segment,IsHold,Remarks,productSegment,CanId,Source,Lat,Long;

    public void setSource(String source) {
        Source = source;
    }

    public void setLat(String lat) {
        Lat = lat;
    }

    public void setLong(String aLong) {
        Long = aLong;
    }

    ;

}
