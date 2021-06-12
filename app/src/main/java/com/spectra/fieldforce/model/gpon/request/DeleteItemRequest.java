package com.spectra.fieldforce.model.gpon.request;

public class DeleteItemRequest {
    public void setAuthkey(String authkey) {
        Authkey = authkey;
    }

    public void setAction(String action) {
        Action = action;
    }

    public void setItemId(String itemId) {
        ItemId = itemId;
    }

    private String Authkey,Action,ItemId;

}
