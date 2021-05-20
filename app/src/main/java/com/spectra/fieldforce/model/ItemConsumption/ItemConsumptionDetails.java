package com.spectra.fieldforce.model.ItemConsumption;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ItemConsumptionDetails {
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


    public class Response{
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

        @SerializedName("item_code")
        private String itemCode;
        @SerializedName("item_name")
        private String itemName;
        @SerializedName("sub_item_code")
        private String subItemCode;
        private String maxcap;

        public String getTemplateId() {
            return TemplateId;
        }

        public void setTemplateId(String templateId) {
            TemplateId = templateId;
        }

        private String TemplateId;

        public String getItemType() {
            return ItemType;
        }

        public void setItemType(String itemType) {
            ItemType = itemType;
        }

        private String ItemType;

        public String getMaxcap() {
            return maxcap;
        }

        public void setMaxcap(String maxcap) {
            this.maxcap = maxcap;
        }

        public String getConsumptionType() {
            return ConsumptionType;
        }

        public void setConsumptionType(String consumptionType) {
            ConsumptionType = consumptionType;
        }

        private String ConsumptionType;

        public String getNRGPSerialNumber() {
            return NRGPSerialNumber;
        }

        public void setNRGPSerialNumber(String NRGPSerialNumber) {
            this.NRGPSerialNumber = NRGPSerialNumber;
        }

        public String getNRGP() {
            return NRGP;
        }

        public void setNRGP(String NRGP) {
            this.NRGP = NRGP;
        }

        public String getNRGPItem() {
            return NRGPItem;
        }

        public void setNRGPItem(String NRGPItem) {
            this.NRGPItem = NRGPItem;
        }

        private String NRGPSerialNumber;
        private String NRGP;
        private String NRGPItem;

        private String sub_item_name;

        public String getSub_item_name() {
            return sub_item_name;
        }

        public void setSub_item_name(String sub_item_name) {
            this.sub_item_name = sub_item_name;
        }

        public String getItemCode() {
            return itemCode;
        }

        public void setItemCode(String itemCode) {
            this.itemCode = itemCode;
        }

        public String getItemName() {
            return itemName;
        }

        public void setItemName(String itemName) {
            this.itemName = itemName;
        }

        public String getSubItemCode() {
            return subItemCode;
        }

        public void setSubItemCode(String subItemCode) {
            this.subItemCode = subItemCode;
        }


    }


    public class Item {

        @SerializedName("item_code")
        private String itemCode;
        @SerializedName("item_name")
        private String itemName;
        @SerializedName("sub_item_code")
        private String subItemCode;
        private String maxcap;
        private String NRGPSerialNumber;

        public String getTemplateId() {
            return TemplateId;
        }

        public void setTemplateId(String templateId) {
            TemplateId = templateId;
        }

        private String TemplateId;

        public String getNRGPSerialNumber() {
            return NRGPSerialNumber;
        }

        public void setNRGPSerialNumber(String NRGPSerialNumber) {
            this.NRGPSerialNumber = NRGPSerialNumber;
        }

        public String getNRGP() {
            return NRGP;
        }

        public void setNRGP(String NRGP) {
            this.NRGP = NRGP;
        }

        public String getNRGPItem() {
            return NRGPItem;
        }

        public void setNRGPItem(String NRGPItem) {
            this.NRGPItem = NRGPItem;
        }

        private String NRGP;
        private String NRGPItem;
        public String getItemType() {
            return ItemType;
        }

        public void setItemType(String itemType) {
            ItemType = itemType;
        }

        private String ItemType;

        public String getMaxcap() {
            return maxcap;
        }

        public void setMaxcap(String maxcap) {
            this.maxcap = maxcap;
        }

        public String getConsumptionType() {
            return ConsumptionType;
        }

        public void setConsumptionType(String consumptionType) {
            ConsumptionType = consumptionType;
        }

        private String ConsumptionType;


        private String sub_item_name;

        public String getSub_item_name() {
            return sub_item_name;
        }

        public void setSub_item_name(String sub_item_name) {
            this.sub_item_name = sub_item_name;
        }

        public String getItemCode() {
            return itemCode;
        }

        public void setItemCode(String itemCode) {
            this.itemCode = itemCode;
        }

        public String getItemName() {
            return itemName;
        }

        public void setItemName(String itemName) {
            this.itemName = itemName;
        }

        public String getSubItemCode() {
            return subItemCode;
        }

        public void setSubItemCode(String subItemCode) {
            this.subItemCode = subItemCode;
        }


    }
}
