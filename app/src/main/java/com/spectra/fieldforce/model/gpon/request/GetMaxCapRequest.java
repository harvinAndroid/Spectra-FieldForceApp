package com.spectra.fieldforce.model.gpon.request;

public class GetMaxCapRequest {
    private String Authkey;
    private String Action;
    private String ItemName;

    public void setAuthkey(String authkey) {
        Authkey = authkey;
    }

    public void setAction(String action) {
        Action = action;
    }

    public void setItemName(String itemName) {
        ItemName = itemName;
    }

    public void setSubItemName(String subItemName) {
        SubItemName = subItemName;
    }

    public void setCanId(String canId) {
        CanId = canId;
    }

    private String SubItemName;
    private String CanId;

}
