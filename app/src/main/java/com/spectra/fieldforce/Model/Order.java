package com.spectra.fieldforce.Model;

public class Order {
    private String assignmentId, customerID, customerName, customerAddress, customerCityName,
            customerMobile, customerEmailId, customerPrefDate, case_remarks, powerLevelINAS,
            customerNetworkTech, slotId, pengId, sengId, sloteBookedDate, srNumber, portId,
            roasterId, srStatus, createdOn, status, startTime, endTime,srType,srSubType,srSubSubType,roasteDate ;

    public Order() {

    }

    public Order(String customerID, String customerName, String customerAddress, String customerMobile,
                 String customerCityName, String customerEmailId, String customerPrefDate,
                 String case_remarks, String srNumber, String portId, String srStatus,
                 String startTime, String endTime, String srType,String srSubType,String srSubSubType,String roasteDate ) {
        this.customerID = customerID;
        this.customerName = customerName;
        this.customerAddress = customerAddress;
        this.customerMobile = customerMobile;
        this.customerCityName = customerCityName;
        this.customerEmailId = customerEmailId;
        this.customerPrefDate = customerPrefDate;
        this.case_remarks = case_remarks;
        this.srNumber = srNumber;
        this.portId = portId;
        this.srStatus = srStatus;
        this.startTime = startTime;
        this.endTime = endTime;
        this.srType = srType;
        this.srSubType = srSubType;
        this.srSubSubType = srSubSubType;
        this.roasteDate=roasteDate;
    }

    public String getCustomerID() {
        return customerID;
    }

    public void setCustomerID(String customerID) {
        this.customerID = customerID;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerAddress() {
        return customerAddress;
    }

    public void setCustomerAddress(String customerAddress) {
        this.customerAddress = customerAddress;
    }

    public String getCustomerMobile() {
        return customerMobile;
    }

    public void setCustomerMobile(String customerMobile) {
        this.customerMobile = customerMobile;
    }

    public String getCustomerCityName() {
        return customerCityName;
    }

    public void setCustomerCityName(String customerCityName) {
        this.customerCityName = customerCityName;
    }

    public String getCustomerEmailId() {
        return customerEmailId;
    }

    public void setCustomerEmailId(String customerMobile) {
        this.customerEmailId = customerEmailId;
    }

    public String getCustomerPrefDate() {
        return customerPrefDate;
    }

    public void setCustomerPrefDate(String customerPrefDate) {
        this.customerPrefDate = customerPrefDate;
    }

    public String getCaseRemarks() {
        return case_remarks;
    }

    public void setCaseRemarks(String case_remarks) {
        this.case_remarks = case_remarks;
    }

    public String getSrNumber() {
        return srNumber;
    }

    public void setSrNumber(String srNumber) {
        this.srNumber = srNumber;
    }

    public String getPortId() {
        return portId;
    }

    public void setPortId(String portId) {
        this.portId = portId;
    }

    public String getSrStatus() {
        return srStatus;
    }

    public void setSrStatus(String srStatus) {
        this.srStatus = srStatus;
    }

    public String getFromtime() {
        return startTime;
    }

    public void setFromtime(String startTime) {
        this.startTime = startTime;
    }

    public String getTotime() {
        return endTime;
    }

    public void setTotime(String startTime) {
        this.endTime = endTime;
    }


    public String getSrType() {
        return srType;
    }

    public void setSrType(String srType) {
        this.srType = srType;
    }

    public String getSrSubType() {
        return srSubType;
    }

    public void setSrSubType(String srSubSubType) {
        this.srSubType = srSubType;
    }

    public String getSrSubSubType() {
        return srSubSubType;
    }

    public void setSrSubSubType(String srSubSubType) {
        this.srSubSubType = srSubSubType;
    }

    public String getRoasterDate() {
        return roasteDate;
    }

    public void setRoasterDate(String roasteDate) {
        this.roasteDate = roasteDate;
    }


}