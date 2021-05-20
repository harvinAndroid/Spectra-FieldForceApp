package com.spectra.fieldforce.model.ItemConsumption;

import com.google.gson.annotations.SerializedName;

public class ItemEquipment {

    @SerializedName("Itemcode")
    private String itemcode;
    @SerializedName("ItemType")
    private String itemType;
    @SerializedName("SerialNumber")
    private String serialNumber;
    @SerializedName("Quantity")
    private String quantity;
    @SerializedName("ItemGUID")
    private String itemGUID;
    @SerializedName("SubitemGUID")
    private String subitemGUID;
    @SerializedName("ConsumptionType")
    private String consumptionType;
    @SerializedName("MacId")
    private String macId;
    private String ItemconsumptionID;
    private String ItemName;

    public String getDefaultKey() {
        return DefaultKey;
    }

    public void setDefaultKey(String defaultKey) {
        DefaultKey = defaultKey;
    }

    private String DefaultKey;


    private String NRGP;

    public String getNRGP() {
        return NRGP;
    }

    public void setNRGP(String NRGP) {
        this.NRGP = NRGP;
    }

    public String getNRGPSerialNumber() {
        return NRGPSerialNumber;
    }

    public void setNRGPSerialNumber(String NRGPSerialNumber) {
        this.NRGPSerialNumber = NRGPSerialNumber;
    }

    public String getNRGPItem() {
        return NRGPItem;
    }

    public void setNRGPItem(String NRGPItem) {
        this.NRGPItem = NRGPItem;
    }

    private String NRGPSerialNumber;
    private String NRGPItem;

    public String getItemconsumptionID() {
        return ItemconsumptionID;
    }

    public void setItemconsumptionID(String itemconsumptionID) {
        ItemconsumptionID = itemconsumptionID;
    }

    public String getItemName() {
        return ItemName;
    }

    public void setItemName(String itemName) {
        ItemName = itemName;
    }

    public String getSubitemName() {
        return SubitemName;
    }

    public void setSubitemName(String subitemName) {
        SubitemName = subitemName;
    }

    public String getMAXCAP() {
        return MAXCAP;
    }

    public void setMAXCAP(String MAXCAP) {
        this.MAXCAP = MAXCAP;
    }

    private String SubitemName;
    private String MAXCAP;

    public String getItemcode() {
        return itemcode;
    }

    public void setItemcode(String itemcode) {
        this.itemcode = itemcode;
    }

    public String getItemType() {
        return itemType;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getItemGUID() {
        return itemGUID;
    }

    public void setItemGUID(String itemGUID) {
        this.itemGUID = itemGUID;
    }

    public String getSubitemGUID() {
        return subitemGUID;
    }

    public void setSubitemGUID(String subitemGUID) {
        this.subitemGUID = subitemGUID;
    }

    public String getConsumptionType() {
        return consumptionType;
    }

    public void setConsumptionType(String consumptionType) {
        this.consumptionType = consumptionType;
    }

    public String getMacId() {
        return macId;
    }

    public void setMacId(String macId) {
        this.macId = macId;
    }
}
