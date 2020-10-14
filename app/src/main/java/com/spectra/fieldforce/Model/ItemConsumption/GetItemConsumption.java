package com.spectra.fieldforce.Model.ItemConsumption;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class GetItemConsumption {

    @SerializedName("Status")
    private Integer status;
    @SerializedName("Response")
    private Response response;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Response getResponse() {
        return response;
    }

    public void setResponse(Response response) {
        this.response = response;
    }

    public class Item {

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

        public String getNRGPItemID() {
            return NRGPItemID;
        }

        public void setNRGPItemID(String NRGPItemID) {
            this.NRGPItemID = NRGPItemID;
        }

        private String NRGPItemID;

        private String ItemconsumptionID;

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

        private String NRGP;
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

        private String ItemName;
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


    public class Response {
        @SerializedName("item")
        private ArrayList<Item> item ;
        @SerializedName("equipment")
        private ArrayList<Equipment> equipment;

        public ArrayList<Item> getItem() {
            return item;
        }

        public void setItem(ArrayList<Item> item) {
            this.item = item;
        }

        public ArrayList<Equipment> getEquipment() {
            return equipment;
        }

        public void setEquipment(ArrayList<Equipment> equipment) {
            this.equipment = equipment;
        }

    }

    public class Equipment {

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

        public String getNRGPItemID() {
            return NRGPItemID;
        }

        public void setNRGPItemID(String NRGPItemID) {
            this.NRGPItemID = NRGPItemID;
        }

        private String NRGPItemID;
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
}
