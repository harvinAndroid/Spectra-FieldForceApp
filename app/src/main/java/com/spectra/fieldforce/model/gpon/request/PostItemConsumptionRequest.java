package com.spectra.fieldforce.model.gpon.request;

public class PostItemConsumptionRequest {
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

    public String getItem() {
        return Item;
    }

    public void setItem(String item) {
        Item = item;
    }

    public String getSubItem() {
        return SubItem;
    }

    public void setSubItem(String subItem) {
        SubItem = subItem;
    }

    public String getWCRguidId() {
        return WCRguidId;
    }

    public void setWCRguidId(String WCRguidId) {
        this.WCRguidId = WCRguidId;
    }

    public String getConsumptionType() {
        return ConsumptionType;
    }

    public void setConsumptionType(String consumptionType) {
        ConsumptionType = consumptionType;
    }

    public String getQuantity() {
        return Quantity;
    }

    public void setQuantity(String quantity) {
        Quantity = quantity;
    }

    public String getSerialNumber() {
        return SerialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        SerialNumber = serialNumber;
    }

    public String getMacId() {
        return MacId;
    }

    public void setMacId(String macId) {
        MacId = macId;
    }

    public String getItemType() {
        return ItemType;
    }

    public void setItemType(String itemType) {
        ItemType = itemType;
    }

    private String Authkey;
    private String Action;
    private String Item;
    private String SubItem;
    private String WCRguidId;
    private String ConsumptionType;
    private String Quantity;
    private String SerialNumber;
    private String MacId;
    private String ItemType;
}

