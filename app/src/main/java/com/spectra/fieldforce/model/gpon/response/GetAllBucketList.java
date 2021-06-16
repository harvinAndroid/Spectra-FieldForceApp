package com.spectra.fieldforce.model.gpon.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetAllBucketList {
    @SerializedName("Status")
    private String status;
    @SerializedName("Response")
    private List<Response> response;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Response> getResponse() {
        return response;
    }

    public void setResponse(List<Response> response) {
        this.response = response;
    }



public class Response {

    @SerializedName("ID")
    private String id;
    @SerializedName("orderType")
    private String orderType;
    @SerializedName("canId")
    private String canId;
    @SerializedName("customerName")
    private String customerName;
    @SerializedName("podName")
    private String podName;
    @SerializedName("status")
    private String status;
    @SerializedName("holdCategory")
    private String holdCategory;
    @SerializedName("holdReason")
    private String holdReason;
    @SerializedName("engineerName")
    private String engineerName;
    @SerializedName("networkTechnology")
    private String networkTechnology;

    public String getIrId() {
        return irId;
    }

    private String irId;
    private String wcrId;

    public String getWcrId() {
        return wcrId;
    }



    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getCanId() {
        return canId;
    }

    public void setCanId(String canId) {
        this.canId = canId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getPodName() {
        return podName;
    }

    public void setPodName(String podName) {
        this.podName = podName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getHoldCategory() {
        return holdCategory;
    }

    public void setHoldCategory(String holdCategory) {
        this.holdCategory = holdCategory;
    }

    public String getHoldReason() {
        return holdReason;
    }

    public void setHoldReason(String holdReason) {
        this.holdReason = holdReason;
    }

    public String getEngineerName() {
        return engineerName;
    }

    public void setEngineerName(String engineerName) {
        this.engineerName = engineerName;
    }

    public String getNetworkTechnology() {
        return networkTechnology;
    }

    public void setNetworkTechnology(String networkTechnology) {
        this.networkTechnology = networkTechnology;
    }
}

}



