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

   private String ID;
    private String wcrId;

    public String getID() {
        return ID;
    }

    public String getWcrId() {
        return wcrId;
    }

    public String getIrId() {
        return irId;
    }

    public String getOrderType() {
        return orderType;
    }

    public String getCanId() {
        return canId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getBusinessSegment() {
        return businessSegment;
    }

    public String getStatus() {
        return status;
    }

    public String getHoldCategory() {
        return holdCategory;
    }

    public String getEngineerName() {
        return engineerName;
    }

    public String getVendorCode() {
        return vendorCode;
    }

    public String getVendorName() {
        return vendorName;
    }

    public String getSlaStatus() {
        return slaStatus;
    }

    public String getAreaName() {
        return areaName;
    }

    public String getProduct() {
        return product;
    }

    public String getContactPerson() {
        return contactPerson;
    }

    public String getContactNo() {
        return contactNo;
    }

    private String irId;
    private String orderType;
    private String canId;
    private String customerName;
    private String businessSegment;
    private String status;
    private String holdCategory;
    private String engineerName;
    private String vendorCode;
    private String vendorName;
    private String slaStatus;
    private String areaName;
    private String product;
    private String contactPerson;
    private String contactNo;
    private String createdOn;
    private String wcrslaclock,irslaclock;
    private String appointmentDate;
    private String cityId;
    private String address;

    public String getAddAssign() {
        return addAssign;
    }

    public void setAddAssign(String addAssign) {
        this.addAssign = addAssign;
    }

    private String addAssign;
    public String getConsumptionStatus() {
        return consumptionStatus;
    }

    private String consumptionStatus;
    private String wcrStatus;

    public String getWcrStatus() {
        return wcrStatus;
    }

    public String getIrStatus() {
        return irStatus;
    }

    private String irStatus;

    public String getActivationOTP() {
        return activationOTP;
    }

    private String activationOTP;

    public String getAppointmentDate() {
        return appointmentDate;
    }

    public String getCityId() {
        return cityId;
    }

    public String getAddress() {
        return address;
    }

    public String getWcrslaclock() {
        return wcrslaclock;
    }

    public String getIrslaclock() {
        return irslaclock;
    }

    public String getCreatedOn() {
        return createdOn;
    }
}

}



