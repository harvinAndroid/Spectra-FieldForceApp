package com.spectra.fieldforce.model.gpon.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class HoldReasonResponse {
    @SerializedName("Status")
    private String status;
    @SerializedName("Response")
    private Response response;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Response getResponse() {
        return response;
    }

    public void setResponse(Response response) {
        this.response = response;
    }



public class Response {

    @SerializedName("WCR_Hold_Category")
    private List<WCRHoldCategory> wCRHoldCategory;

    public List<WCRHoldCategory> getWCRHoldCategory() {
        return wCRHoldCategory;
    }

    public void setWCRHoldCategory(List<WCRHoldCategory> wCRHoldCategory) {
        this.wCRHoldCategory = wCRHoldCategory;
    }

}

public class WCRHoldCategory {

    @SerializedName("ID")
    private String id;
    @SerializedName("Category")
    private String category;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}

}
