package com.spectra.fieldforce.model.gpon.request;

public class AddBucketListRequest {

   private String  Authkey,Action,Order_id,Order_type,WCRId,IRId, CustomerID,CustomerName,CRM_status,HoldCategory,HoldReason,
               EngineerName,EngineerID,VendorCode,VendorName,SlaStatus,AreaName,Product,ContactPerson,ContactNo,
               OrderCreatedOn,AppointmentDate,WCRSlaClock,IRSlaClock;

    public void setWCRSlaClock(String WCRSlaClock) {
        this.WCRSlaClock = WCRSlaClock;
    }

    public void setIRSlaClock(String IRSlaClock) {
        this.IRSlaClock = IRSlaClock;
    }

    public void setAuthkey(String authkey) {
        Authkey = authkey;
    }

    public void setAction(String action) {
        Action = action;
    }

    public void setOrder_id(String order_id) {
        Order_id = order_id;
    }

    public void setOrder_type(String order_type) {
        Order_type = order_type;
    }

    public void setWCRId(String WCRId) {
        this.WCRId = WCRId;
    }

    public void setIRId(String IRId) {
        this.IRId = IRId;
    }

    public void setCustomerID(String customerID) {
        CustomerID = customerID;
    }

    public void setCustomerName(String customerName) {
        CustomerName = customerName;
    }

    public void setCRM_status(String CRM_status) {
        this.CRM_status = CRM_status;
    }

    public void setHoldCategory(String holdCategory) {
        HoldCategory = holdCategory;
    }

    public void setHoldReason(String holdReason) {
        HoldReason = holdReason;
    }

    public void setEngineerName(String engineerName) {
        EngineerName = engineerName;
    }

    public void setEngineerID(String engineerID) {
        EngineerID = engineerID;
    }

    public void setVendorCode(String vendorCode) {
        VendorCode = vendorCode;
    }

    public void setVendorName(String vendorName) {
        VendorName = vendorName;
    }

    public void setSlaStatus(String slaStatus) {
        SlaStatus = slaStatus;
    }

    public void setAreaName(String areaName) {
        AreaName = areaName;
    }

    public void setProduct(String product) {
        Product = product;
    }

    public void setContactPerson(String contactPerson) {
        ContactPerson = contactPerson;
    }

    public void setContactNo(String contactNo) {
        ContactNo = contactNo;
    }

    public void setOrderCreatedOn(String orderCreatedOn) {
        OrderCreatedOn = orderCreatedOn;
    }

    public void setAppointmentDate(String appointmentDate) {
        AppointmentDate = appointmentDate;
    }
}
