package com.spectra.fieldforce.model.gpon.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetMyBucketList {
    @SerializedName("StatusCode")

    private Integer statusCode;
    @SerializedName("Status")

    private String status;
    @SerializedName("Response")

    private List<Response> response;

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

    public List<Response> getResponse() {
        return response;
    }

    public void setResponse(List<Response> response) {
        this.response = response;
    }


    public class Response {

        @SerializedName("order_id")

        private String orderId;
        @SerializedName("order_type")

        private String orderType;
        @SerializedName("customerID")

        private String customerID;
        @SerializedName("customerName")

        private String customerName;
        @SerializedName("podName")

        private String podName;
        @SerializedName("crm_status")

        private String crmStatus;
        @SerializedName("holdCategory")

        private String holdCategory;
        @SerializedName("holdReason")
        private String holdReason;

        public String getIrId() {
            return irId;
        }

        public String getWcrId() {
            return wcrId;
        }

        private String irId;
        private String wcrId;
        private String appointmentDate;

        public String getAppointmentDate() {
            return appointmentDate;
        }

        public String getOrderId() {
            return orderId;
        }

        public void setOrderId(String orderId) {
            this.orderId = orderId;
        }

        public String getOrderType() {
            return orderType;
        }

        public void setOrderType(String orderType) {
            this.orderType = orderType;
        }

        public String getCustomerID() {
            return customerID;
        }

        public void setCustomerID(String customerID) {
            this.customerID = customerID;
        }

        public String getCustomerName() {
            return customerName;
        }

        public void setCustomerName(String customerName) {
            this.customerName = customerName;
        }

        public String getPodName() {
            return podName;
        }

        public void setPodName(String podName) {
            this.podName = podName;
        }

        public String getCrmStatus() {
            return crmStatus;
        }

        public void setCrmStatus(String crmStatus) {
            this.crmStatus = crmStatus;
        }

        public String getHoldCategory() {
            return holdCategory;
        }

        public void setHoldCategory(String holdCategory) {
            this.holdCategory = holdCategory;
        }

        public String getHoldReason() {
            return holdReason;
        }

        public void setHoldReason(String holdReason) {
            this.holdReason = holdReason;
        }

    }

}
