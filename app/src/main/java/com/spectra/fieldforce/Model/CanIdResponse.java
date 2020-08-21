package com.spectra.fieldforce.Model;

import java.util.ArrayList;


public class CanIdResponse {
    private String status;
    private ArrayList<Response> response ;
    private String message;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ArrayList<Response> getResponse() {
        return response;
    }

    public void setResponse(ArrayList<Response> response) {
        this.response = response;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public class Response{
        private String AccountStatus;

        public String getAccountStatus() {
            return AccountStatus;
        }

        public void setAccountStatus(String accountStatus) {
            AccountStatus = accountStatus;
        }

        public Boolean getBarringFlag() {
            return BarringFlag;
        }

        public void setBarringFlag(Boolean barringFlag) {
            BarringFlag = barringFlag;
        }

        public Boolean getFUPFlag() {
            return FUPFlag;
        }

        public void setFUPFlag(Boolean FUPFlag) {
            this.FUPFlag = FUPFlag;
        }

        private Boolean BarringFlag;
        private Boolean FUPFlag;

        public String getProductSegment() {
            return ProductSegment;
        }

        public void setProductSegment(String productSegment) {
            ProductSegment = productSegment;
        }

        public String getProduct() {
            return Product;
        }

        public void setProduct(String product) {
            Product = product;
        }

        private String ProductSegment;
        private String Product;
        private IvrNotification[] ivrNotification;
        public IvrNotification[] getIvrNotification ()
        {
            return ivrNotification;
        }

        public void setIvrNotification (IvrNotification[] ivrNotification)
        {
            this.ivrNotification = ivrNotification;
        }


    }

    public class IvrNotification {
        private String message;

        public String getMessage ()
        {
            return message;
        }

        public void setMessage (String message)
        {
            this.message = message;
        }
    }

}
