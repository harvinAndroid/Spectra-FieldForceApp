package com.spectra.fieldforce.model.gpon.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class WCRHoldCategoryResponse {
    @SerializedName("Status")
    private Integer status;
    @SerializedName("Response")
    private Response response;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
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
        private List<WCRHoldCategory> wCRHoldCategory = null;

        public List<WCRHoldCategory> getWCRHoldCategory() {
            return wCRHoldCategory;
        }

        public void setWCRHoldCategory(List<WCRHoldCategory> wCRHoldCategory) {
            this.wCRHoldCategory = wCRHoldCategory;
        }

    }

    public class WCRHoldCategory {

        @SerializedName("ID")
        private String ID;
        @SerializedName("Category")
        private String Category;

        public String getId() {
            return ID;
        }

        public void setId(String id) {
            this.ID = id;
        }

        public String getCategory() {
            return Category;
        }

        public void setCategory(String category) {
            this.Category = category;
        }
    }

}
