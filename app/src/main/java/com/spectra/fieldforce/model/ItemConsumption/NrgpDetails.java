package com.spectra.fieldforce.model.ItemConsumption;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class NrgpDetails {

    @SerializedName("Status")
    private Integer status;
    @SerializedName("Response")
    private ArrayList<Response> response;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public ArrayList<Response> getResponse() {
        return response;
    }

    public void setResponse(ArrayList<Response> response) {
        this.response = response;
    }



public class Response {

    @SerializedName("item_code")
    private String itemCode;
    @SerializedName("item_name")
    private String itemName;
    @SerializedName("sub_item_code")
    private String subItemCode;
    @SerializedName("sub_item_name")
    private String subItemName;
    @SerializedName("max_cap")
    private String maxCap;

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

    public String getSubItemName() {
        return subItemName;
    }

    public void setSubItemName(String subItemName) {
        this.subItemName = subItemName;
    }

    public String getMaxCap() {
        return maxCap;
    }

    public void setMaxCap(String maxCap) {
        this.maxCap = maxCap;
    }


}
}
