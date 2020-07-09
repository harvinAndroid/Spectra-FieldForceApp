package com.spectra.fieldforce.Model;

public class SRRequest {
    String Authkey = "";
    String Action = "";
    String TaskType = "";
    String SlotType = "";
    String SrNumber = "";

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
}
