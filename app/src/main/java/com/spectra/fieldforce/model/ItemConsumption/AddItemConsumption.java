package com.spectra.fieldforce.model.ItemConsumption;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AddItemConsumption {
    @SerializedName("Authkey")
    private String authkey;
    @SerializedName("Action")
    private String action;
    @SerializedName("srNumber")
    private String srNumber;
    @SerializedName("canId")
    private String canId;
    @SerializedName("item")
    private List<Item> item;
    @SerializedName("equipment")
    private List<Equipment> equipment;

    public String getAuthkey() {
        return authkey;
    }

    public void setAuthkey(String authkey) {
        this.authkey = authkey;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getSrNumber() {
        return srNumber;
    }

    public void setSrNumber(String srNumber) {
        this.srNumber = srNumber;
    }

    public String getCanId() {
        return canId;
    }

    public void setCanId(String canId) {
        this.canId = canId;
    }

    public List<Item> getItem() {
        return item;
    }

    public void setItem(List<Item> item) {
        this.item = item;
    }

    public List<Equipment> getEquipment() {
        return equipment;
    }

    public void setEquipment(List<Equipment> equipment) {
        this.equipment = equipment;
    }

    public class Equipment {

        @SerializedName("ItemType")
        private String itemType;
        @SerializedName("SerialNumber")
        private String serialNumber;
        @SerializedName("Quantity")
        private String quantity;
        @SerializedName("Item")
        private String item;
        @SerializedName("SubItem")
        private String subItem;
        @SerializedName("ConsumptionType")
        private Object consumptionType;
        @SerializedName("MacId")
        private String macId;

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

        public String getItem() {
            return item;
        }

        public void setItem(String item) {
            this.item = item;
        }

        public String getSubItem() {
            return subItem;
        }

        public void setSubItem(String subItem) {
            this.subItem = subItem;
        }

        public Object getConsumptionType() {
            return consumptionType;
        }

        public void setConsumptionType(Object consumptionType) {
            this.consumptionType = consumptionType;
        }

        public String getMacId() {
            return macId;
        }

        public void setMacId(String macId) {
            this.macId = macId;
        }

    }


    public class Item {

        @SerializedName("ItemType")
        private String itemType;
        @SerializedName("SerialNumber")
        private String serialNumber;
        @SerializedName("Quantity")
        private String quantity;
        @SerializedName("Item")
        private String item;
        @SerializedName("SubItem")
        private String subItem;
        @SerializedName("ConsumptionType")
        private String consumptionType;
        @SerializedName("MacId")
        private String macId;

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

        public String getItem() {
            return item;
        }

        public void setItem(String item) {
            this.item = item;
        }

        public String getSubItem() {
            return subItem;
        }

        public void setSubItem(String subItem) {
            this.subItem = subItem;
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
