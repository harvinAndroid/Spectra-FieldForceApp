package com.spectra.fieldforce.model;

public class Order {
    private String customerID;
    private String customerName;
    private String customerAddress;
    private String customerCityName;
    private String customerMobile;
    private String slotType;
    private String engId;
    private String customerPrefDate;
    private String case_remarks;
    private String segment;
    private String podName;
    private String srNumber;
    private String portId;
    private String oltname;
    private String slaClock;
    private String acknowledge_status;
    private String slaStatus;
    private String customerIP;
    private String srStatus;
    private String startTime;
    private String endTime;
    private String srType;
    private String srSubType;
    private String roasteDate;
    private String foni;
    private String repeat_sr;
    private String startLatitude;
    private String endLatitude;
    private String massoutage;
    private String actionCode;
    private String customer_contacted;
    private String etr;
    private String srSubSubType;
    private String accountTurnOver,customerCategory,externalCustomerSegment;

    public String getAccountTurnOver() {
        return accountTurnOver;
    }

    public String getCustomerCategory() {
        return customerCategory;
    }

    public String getExternalCustomerSegment() {
        return externalCustomerSegment;
    }

    private String contactedName,contactedNumber;

    public String getContactedName() {
        return contactedName;
    }

    public String getContactedNumber() {
        return contactedNumber;
    }

    public String getCustomerNetworkTech() {
        return customerNetworkTech;
    }

    public void setCustomerNetworkTech(String customerNetworkTech) {
        this.customerNetworkTech = customerNetworkTech;
    }

    private String customerNetworkTech;

    public String getSrSubSubTypeID() {
        return srSubSubTypeID;
    }

    public void setSrSubSubTypeID(String srSubSubTypeID) {
        this.srSubSubTypeID = srSubSubTypeID;
    }

    private String srSubSubTypeID;

    public String getSrSubSubType() {
        return srSubSubType;
    }

    public void setSrSubSubType(String srSubSubType) {
        this.srSubSubType = srSubSubType;
    }

    public String getCustomer_contacted() {
        return customer_contacted;
    }

    public void setCustomer_contacted(String customer_contacted) {
        this.customer_contacted = customer_contacted;
    }

    public String getEtr() {
        return etr;
    }

    public void setEtr(String etr) {
        this.etr = etr;
    }

    public Order(String customerID, String customerName, String customerAddress, String customerCityName, String engId,
                 String customerMobile, String slotType, String customerPrefDate, String case_remarks, String segment,
                 String podName, String srNumber, String portId, String oltname, String slaClock, String acknowledge_status,
                 String slaStatus, String customerIP, String srStatus, String startTime, String endTime, String startLatitude,
                 String endLatitude, String srType, String srSubType, String roasteDate, String foni, String repeat_sr,
                 String massoutage, String actionCode) {
        this.customerID = customerID;
        this.customerName = customerName;
        this.customerAddress = customerAddress;
        this.customerCityName = customerCityName;
        this.customerMobile = customerMobile;
        this.customerPrefDate = customerPrefDate;
        this.case_remarks = case_remarks;
        this.slotType = slotType;
        this.acknowledge_status = acknowledge_status;
        this.segment = segment;
        this.podName = podName;
        this.srNumber = srNumber;
        this.portId = portId;
        this.oltname = oltname;
        this.slaClock = slaClock;
        this.slaStatus = slaStatus;
        this.customerIP = customerIP;
        this.srStatus = srStatus;
        this.startTime = startTime;
        this.endTime = endTime;
        this.srType = srType;
        this.srSubType = srSubType;
        this.roasteDate = roasteDate;
        this.foni = foni;
        this.repeat_sr = repeat_sr;
        this.massoutage = massoutage;
        this.engId = engId;
        this.startLatitude = startLatitude;
        this.endLatitude = endLatitude;
        this.actionCode = actionCode;
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

    public String getSlotType() {
        return slotType;
    }

    public void setSlotType(String slotType) {
        this.slotType = slotType;
    }

    public String getAcknowledge_status() {
        return acknowledge_status;
    }

    public void setAcknowledge_status(String acknowledge_status) {
        this.slotType = acknowledge_status;
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
        return oltname;
    }

    public void setDeviceName(String oltname) {
        this.oltname = oltname;
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

    public String getFoni() {
        return foni;
    }

    public void setFoni(String foni) {
        this.foni = foni;
    }

    public String getRepeat_sr() {
        return repeat_sr;
    }

    public void setRepeat_sr(String repeat_sr) {
        this.repeat_sr = repeat_sr;
    }

    public String getMassoutage() {
        return massoutage;
    }

    public void setMassoutage(String massoutage) {
        this.massoutage = massoutage;
    }

    public String getEngId() {
        return engId;
    }

    public void setEngId(String engId) {
        this.engId = engId;
    }

    public String getStartLatitude() {
        return startLatitude;
    }

    public void setStartLatitude(String startLatitude) {
        this.startLatitude = startLatitude;
    }

    public String getEndLatitude() {
        return endLatitude;
    }

    public void setEndLatitude(String endLatitude) {
        this.endLatitude = endLatitude;
    }
    public String getActionCode() {
        return actionCode;
    }

    public void setActionCode(String actionCode) {
        this.actionCode = actionCode;
    }

}