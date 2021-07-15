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

       private String  order_id;
        private String order_type;
        private String wcrId;
        private String irId;
        private String customerID;
        private String customerName;
        private String appointmentDate;
        private String crm_status;
        private String holdCategory;
        private String holdReason;
        private String vendorName;
        private String slaStatus;
        private String areaName;
        private String product;
        private String contactPerson;
        private String contactNo;

        public String getOrder_id() {
            return order_id;
        }

        public String getOrder_type() {
            return order_type;
        }

        public String getWcrId() {
            return wcrId;
        }

        public String getIrId() {
            return irId;
        }

        public String getCustomerID() {
            return customerID;
        }

        public String getCustomerName() {
            return customerName;
        }

        public String getAppointmentDate() {
            return appointmentDate;
        }

        public String getCrm_status() {
            return crm_status;
        }

        public String getHoldCategory() {
            return holdCategory;
        }

        public String getHoldReason() {
            return holdReason;
        }

        public String getVendorName() {
            return vendorName;
        }

        public String getSlaStatus() {
            return slaStatus;
        }

        public String getAreaName() {
            return areaName;
        }

        public String getProduct() {
            return product;
        }

        public String getContactPerson() {
            return contactPerson;
        }

        public String getContactNo() {
            return contactNo;
        }

        public String getOrderCreatedOn() {
            return orderCreatedOn;
        }

        public String getLongitude() {
            return longitude;
        }

        public String getLatitude() {
            return latitude;
        }

        private String orderCreatedOn;
        private String longitude;
        private String latitude;

    }

}
