package com.spectra.fieldforce.Model.ItemConsumption;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ItemStatus {
    @SerializedName("Status")
    private Integer status;

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    private String Message;
    @SerializedName("Response")
    private List<Response> response ;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public List<Response> getResponse() {
        return response;
    }

    public void setResponse(List<Response> response) {
        this.response = response;
    }

    public class Response {

        @SerializedName("approvalstatus")
        private String approvalstatus;
        @SerializedName("Frstatus")
        private String frstatus;

        public String getApprovalstatus() {
            return approvalstatus;
        }

        public void setApprovalstatus(String approvalstatus) {
            this.approvalstatus = approvalstatus;
        }

        public String getFrstatus() {
            return frstatus;
        }

        public void setFrstatus(String frstatus) {
            this.frstatus = frstatus;
        }

    }
}
