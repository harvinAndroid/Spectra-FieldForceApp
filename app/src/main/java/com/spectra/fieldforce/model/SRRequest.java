package com.spectra.fieldforce.model;

public class SRRequest {
    private String Authkey = "";
    private String Action = "";
    private String TaskType = "";
    private  String SlotType = "";
    private String SrNumber = "";
    private String ActionCode = "";
    private String CustomerName = "";
    private String ContactNo = "";
    private String Contacted = "";
    private String EngId = "";
    private String ETR = "";
    private String EmailId = "";
    private String RConeId = "";
    private String RCtwoId = "";
    private String RCthirdId = "";
    private String ReasonOf = "";
    private String EmpId = "";
    private String UpdatedBy = "";
    private String ResolveContacted = "";
    private String Source = "";

    public String getCustomerID() {
        return CustomerID;
    }

    public void setCustomerID(String customerID) {
        CustomerID = customerID;
    }

    private String CustomerID ;

    public String getNetworkType() {
        return NetworkType;
    }

    public void setNetworkType(String networkType) {
        NetworkType = networkType;
    }

    private String NetworkType;


    public String getAuthkey() {
        return Authkey;
    }

    public void setAuthkey(String authkey) {
        Authkey = authkey;
    }

    public String getAction() {
        return Action;
    }

    public void setAction(String action) {
        Action = action;
    }

    public String getTaskType() {
        return TaskType;
    }

    public void setTaskType(String taskType) {
        this.TaskType = taskType;
    }

    public String getSlotType() {
        return SlotType;
    }

    public void setSlotType(String slotType) {
        SlotType = slotType;
    }

    public String getSrNumber() {
        return SrNumber;
    }

    public void setSrNumber(String srNumber) {
        this.SrNumber = srNumber;
    }

    public String getActionCode() {
        return ActionCode;
    }

    public void setActionCode(String actionCode) {
        this.ActionCode = actionCode;
    }

    public String getContactName() {
        return CustomerName;
    }

    public void setContactName(String contactName) {
        this.CustomerName = contactName;
    }

    public String getContactNumber() {
        return ContactNo;
    }

    public void setContactNumber(String contactNumber) {
        this.ContactNo = contactNumber;
    }

    public String getContacted() {
        return Contacted;
    }

    public void setContacted(String contacted) {
        this.Contacted = contacted;
    }

    public String getEngId() {
        return EngId;
    }

    public void setEngId(String engId) {
        this.EngId = engId;
    }

    public String getETR() {
        return ETR;
    }

    public void setETR(String etr) {
        this.ETR = etr;
    }

    public String getEmailId() {
        return EmailId;
    }

    public void setEmailId(String emailId) {
        this.EmailId = emailId;
    }

    public String getRConeId() {
        return RConeId;
    }

    public void setRConeId(String rConeId) {
        this.RConeId = rConeId;
    }

    public String getRCtwoId() {
        return RCtwoId;
    }

    public void setRCtwoId(String rCtwoId) {
        this.RCtwoId = rCtwoId;
    }

    public String getRCthirdId() {
        return RCthirdId;
    }

    public void setRCthirdId(String rCthirdId) {
        this.RCthirdId = rCthirdId;
    }

    public String getReasonOf() {
        return ReasonOf;
    }

    public void setReasonOf(String reasonOf) {
        this.ReasonOf = reasonOf;
    }

    public String getSource() {
        return Source;
    }

    public void setSource(String source) {
        this.Source = source;
    }

    public String getResolveContacted() {
        return ResolveContacted;
    }

    public void setResolveContacted(String resolveContacted) {
        this.ResolveContacted = resolveContacted;
    }

    public String getEmpId() {
        return EmpId;
    }

    public void setEmpId(String empId) {
        this.EmpId = empId;
    }

    public String getUpdatedBy() {
        return UpdatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.UpdatedBy = updatedBy;
    }
}
