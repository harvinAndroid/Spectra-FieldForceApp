package com.spectra.fieldforce.model.gpon.request;

public class SubmitApprovalRequest {
    private  String Authkey,Action,ItemId,ItemType;

    public void setAuthkey(String authkey) {
        Authkey = authkey;
    }

    public void setAction(String action) {
        Action = action;
    }

    public void setItemId(String itemId) {
        ItemId = itemId;
    }

    public void setItemType(String itemType) {
        ItemType = itemType;
    }
}
