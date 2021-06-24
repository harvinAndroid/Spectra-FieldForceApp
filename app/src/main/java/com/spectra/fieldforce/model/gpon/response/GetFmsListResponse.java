package com.spectra.fieldforce.model.gpon.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetFmsListResponse {
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

        @SerializedName("FMS_List")
        private List<Fms> fMSList;

        public List<Fms> getFMSList() {
            return fMSList;
        }

        public void setFMSList(List<Fms> fMSList) {
            this.fMSList = fMSList;
        }

    }

    public class Fms {

        @SerializedName("ID")
        private String id;
        @SerializedName("FMS")
        private String fms;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getFms() {
            return fms;
        }

        public void setFms(String fms) {
            this.fms = fms;
        }

    }
}
