package com.spectra.fieldforce.model.gpon.response;

import com.google.gson.annotations.SerializedName;

public class CommonClassResponse {


    @SerializedName("StatusCode")
    private Integer statusCode;
    @SerializedName("Status")
    private String status;
    @SerializedName("Response")
    private Response response;

    public Integer getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }

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

        @SerializedName("StatusCode")
        private Integer statusCode;
        @SerializedName("Message")
        private String message;
        private String Id;

        public Integer getStatusCode() {
            return statusCode;
        }
        public String getMessage() {
            return message;
        }
        public String getId() {
            return Id;
        }

    }
}
