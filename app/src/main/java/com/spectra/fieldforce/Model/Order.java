package com.spectra.fieldforce.Model;

public class Order {
    private String customerID, customerName, customerAddress, customerCityName, customerMobile,
            customerPrefDate, case_remarks, segment, podName, srNumber, portId, deviceName, slaClock,
            slaStatus, customerIP, srStatus, startTime, endTime, srType, srSubType, roasteDate;

    public Order() {

    }

    public Order(String customerID, String customerName, String customerAddress, String customerCityName,
                 String customerMobile, String customerPrefDate, String case_remarks, String segment,
                 String podName, String srNumber, String portId, String deviceName, String slaClock,
                 String slaStatus, String customerIP, String srStatus, String startTime, String endTime,
                 String srType, String srSubType, String roasteDate) {
        this.customerID = customerID;
        this.customerName = customerName;
        this.customerAddress = customerAddress;
        this.customerCityName = customerCityName;
        this.customerMobile = customerMobile;
        this.customerPrefDate = customerPrefDate;
        this.case_remarks = case_remarks;
        this.segment = segment;
        this.podName = podName;
        this.srNumber = srNumber;
        this.portId = portId;
        this.deviceName = deviceName;
        this.slaClock = slaClock;
        this.slaStatus = slaStatus;
        this.customerIP = customerIP;
        this.srStatus = srStatus;
        this.startTime = startTime;
        this.endTime = endTime;
        this.srType = srType;
        this.srSubType = srSubType;
        this.roasteDate = roasteDate;
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

    public String getPodName() {
        return podName;
    }

    public void setPodName(String podName) {
        this.podName = podName;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
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

    public String getRoasterDate() {
        return roasteDate;
    }

    public void setRoasterDate(String roasteDate) {
        this.roasteDate = roasteDate;
    }

    public String getSlaClock() {
        return slaClock;
    }

    public void setSlaClock(String slaClock) {
        this.slaClock = slaClock;
    }

    public String getSlaStatus() {
        return slaStatus;
    }

    public void setStatus(String slaStatus) {
        this.slaStatus = slaStatus;
    }

    public String getCustomerIP() {
        return customerIP;
    }

    public void setCustomerIP(String customerIP) {
        this.customerIP = customerIP;
    }

    public String getSegment() {
        return segment;
    }

    public void setSegment(String segment) {
        this.segment = segment;
    }
}