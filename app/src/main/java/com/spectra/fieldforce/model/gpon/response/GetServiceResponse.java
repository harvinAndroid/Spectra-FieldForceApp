package com.spectra.fieldforce.model.gpon.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetServiceResponse {
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


        @SerializedName("data")
        private List<Datum> data;
        @SerializedName("StatusCode")
        private Integer statusCode;
        @SerializedName("Message")
        private String message;

        public List<Datum> getData() {
            return data;
        }

        public void setData(List<Datum> data) {
            this.data = data;
        }

        public Integer getStatusCode() {
            return statusCode;
        }

        public void setStatusCode(Integer statusCode) {
            this.statusCode = statusCode;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

    }

    public class Datum {

        @SerializedName("subItemId")
        private String subItemId;
        @SerializedName("subItemName")
        private String subItemName;

        public String getItemId() {
            return itemId;
        }

        private String itemId;

        public String getSubItemId() {
            return subItemId;
        }

        public void setSubItemId(String subItemId) {
            this.subItemId = subItemId;
        }

        public String getSubItemName() {
            return subItemName;
        }

        public void setSubItemName(String subItemName) {
            this.subItemName = subItemName;
        }
    }
}
