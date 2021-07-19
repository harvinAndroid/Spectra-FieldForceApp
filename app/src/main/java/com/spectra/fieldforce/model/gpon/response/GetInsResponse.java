package com.spectra.fieldforce.model.gpon.response;

import com.google.gson.annotations.SerializedName;

public class GetInsResponse {
    @SerializedName("Status")
    private String status;
    @SerializedName("StatusCode")
    private String statusCode;
    @SerializedName("Response")
    private Response response;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public Response getResponse() {
        return response;
    }

    public void setResponse(Response response) {
        this.response = response;
    }

public class Response {

    @SerializedName("FiberDistance")
    private String fiberDistance;
    @SerializedName("AlarmsInfo")
    private String alarmsInfo;
    @SerializedName("PowerLevel")
    private String powerLevel;
    @SerializedName("ActiveStatus")
    private String activeStatus;
    @SerializedName("IPAddress")
    private String iPAddress;

    public String getMessaage() {
        return Messaage;
    }

    private String Messaage;

    public String getFiberDistance() {
        return fiberDistance;
    }

    public void setFiberDistance(String fiberDistance) {
        this.fiberDistance = fiberDistance;
    }

    public String getAlarmsInfo() {
        return alarmsInfo;
    }

    public void setAlarmsInfo(String alarmsInfo) {
        this.alarmsInfo = alarmsInfo;
    }

    public String getPowerLevel() {
        return powerLevel;
    }

    public void setPowerLevel(String powerLevel) {
        this.powerLevel = powerLevel;
    }

    public String getActiveStatus() {
        return activeStatus;
    }

    public void setActiveStatus(String activeStatus) {
        this.activeStatus = activeStatus;
    }

    public String getIPAddress() {
        return iPAddress;
    }

    public void setIPAddress(String iPAddress) {
        this.iPAddress = iPAddress;
    }
}
}
