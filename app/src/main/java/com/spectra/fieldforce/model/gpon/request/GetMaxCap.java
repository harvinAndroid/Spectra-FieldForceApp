package com.spectra.fieldforce.model.gpon.request;

public class GetMaxCap {
    public String getAuthkey() {
        return Authkey;
    }

    public void setAuthkey(String authkey) {
        Authkey = authkey;
    }

    public String getAction() {
        return Action;
    }

    public void setAction(String action) {
        Action = action;
    }

    public String getItemName() {
        return ItemName;
    }

    public void setItemName(String itemName) {
        ItemName = itemName;
    }

    public String getSubItemName() {
        return SubItemName;
    }

    public void setSubItemName(String subItemName) {
        SubItemName = subItemName;
    }

    public String getCanId() {
        return CanId;
    }

    public void setCanId(String canId) {
        CanId = canId;
    }

    private String Authkey,Action,ItemName,SubItemName,CanId;

}
