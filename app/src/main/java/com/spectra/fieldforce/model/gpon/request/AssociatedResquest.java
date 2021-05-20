package com.spectra.fieldforce.model.gpon.request;

public class AssociatedResquest {
   private String Action;

    public void setAction(String action) {
        Action = action;
    }

    public void setAuthkey(String authkey) {
        Authkey = authkey;
    }

    public void setIdb(String idb) {
        Idb = idb;
    }

    public void setLink(String link) {
        Link = link;
    }

    public void setWCRguidId(String WCRguidId) {
        this.WCRguidId = WCRguidId;
    }

    private String Authkey;
    private String Idb;
    private String Link;
    private String WCRguidId;




}
