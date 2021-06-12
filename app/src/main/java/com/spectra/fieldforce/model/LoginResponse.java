package com.spectra.fieldforce.model;

import com.google.gson.annotations.SerializedName;

public class LoginResponse {
    @SerializedName("Status")
    private String status;
    @SerializedName("ErrorCode")

    private Integer errorCode;
    @SerializedName("response")

    private Response response;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(Integer errorCode) {
        this.errorCode = errorCode;
    }

    public Response getResponse() {
        return response;
    }

    public void setResponse(Response response) {
        this.response = response;
    }

    public class Response  {

        @SerializedName("name")

        private String name;
        @SerializedName("userID")

        private String userID;
        @SerializedName("username")

        private String username;
        @SerializedName("ffaAuth")

        private String ffaAuth;
        @SerializedName("installAuth")

        private String installAuth;
        @SerializedName("vendorCode")

        private String vendorCode;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getUserID() {
            return userID;
        }

        public void setUserID(String userID) {
            this.userID = userID;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getFfaAuth() {
            return ffaAuth;
        }

        public void setFfaAuth(String ffaAuth) {
            this.ffaAuth = ffaAuth;
        }

        public String getInstallAuth() {
            return installAuth;
        }

        public void setInstallAuth(String installAuth) {
            this.installAuth = installAuth;
        }

        public String getVendorCode() {
            return vendorCode;
        }

        public void setVendorCode(String vendorCode) {
            this.vendorCode = vendorCode;
        }

    }

}
