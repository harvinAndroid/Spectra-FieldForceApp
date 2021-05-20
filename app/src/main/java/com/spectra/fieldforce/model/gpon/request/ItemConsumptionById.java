package com.spectra.fieldforce.model.gpon.request;

public class ItemConsumptionById {
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



    private String Authkey;
    private String Action;
    private String Id;

    public void setItemId(String itemId) {
        ItemId = itemId;
    }

    private String ItemId;

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }
}
