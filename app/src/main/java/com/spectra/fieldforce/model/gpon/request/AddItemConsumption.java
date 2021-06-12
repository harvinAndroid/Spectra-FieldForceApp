package com.spectra.fieldforce.model.gpon.request;

public class AddItemConsumption {
    private String Authkey;

    public void setAuthkey(String authkey) {
        Authkey = authkey;
    }

    public void setAction(String action) {
        Action = action;
    }

    public void setItem(String item) {
        Item = item;
    }

    public void setSubItem(String subItem) {
        SubItem = subItem;
    }

    public void setWCRguidId(String WCRguidId) {
        this.WCRguidId = WCRguidId;
    }

    public void setConsumptionType(String consumptionType) {
        ConsumptionType = consumptionType;
    }

    public void setQuantity(String quantity) {
        Quantity = quantity;
    }

    public void setSerialNumber(String serialNumber) {
        SerialNumber = serialNumber;
    }

    public void setMacId(String macId) {
        MacId = macId;
    }

    public void setItemType(String itemType) {
        ItemType = itemType;
    }

    private String Action;
    private String Item;
    private String SubItem;
    private String WCRguidId;
    private String ConsumptionType;
    private String Quantity;
    private String SerialNumber;
    private String MacId;
    private String ItemType;


    public void setItemID(String itemID) {
        ItemID = itemID;
    }

    private String ItemID;




}
