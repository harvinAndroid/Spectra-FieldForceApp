package com.spectra.fieldforce.model.gpon.request;

public class EditItemConsumptionNavRequest {
    private String Authkey;

    public void setAuthkey(String authkey) {
        Authkey = authkey;
    }

    public void setAction(String action) {
        Action = action;
    }

    public void setSerialNumber(String serialNumber) {
        SerialNumber = serialNumber;
    }

    public void setQuantity(String quantity) {
        Quantity = quantity;
    }

    public void setMacId(String macId) {
        MacId = macId;
    }

    public void setWCRguidId(String WCRguidId) {
        this.WCRguidId = WCRguidId;
    }

    public void setItemID(String itemID) {
        ItemID = itemID;
    }

    private String Action;
    private String SerialNumber;
    private String Quantity;
    private String MacId;
    private String WCRguidId;
    private String ItemID;

    public void setSubItem(String subItem) {
        SubItem = subItem;
    }

    private String SubItem;
}


