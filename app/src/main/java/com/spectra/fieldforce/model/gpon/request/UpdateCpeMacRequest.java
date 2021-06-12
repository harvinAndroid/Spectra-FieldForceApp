package com.spectra.fieldforce.model.gpon.request;

public class UpdateCpeMacRequest {
    private String Authkey;
    private String Action;
    private String ItemId;

    public void setItemType(String itemType) {
        ItemType = itemType;
    }

    private String ItemType;

    public void setAuthkey(String authkey) {
        Authkey = authkey;
    }

    public void setAction(String action) {
        Action = action;
    }

    public void setItemId(String itemId) {
        ItemId = itemId;
    }

    public void setMACShared(String MACShared) {
        this.MACShared = MACShared;
    }

    private String MACShared;

}
