package com.spectra.fieldforce.model.gpon.response;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class AccountDetailsResponse {

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


    public class OnuProfile {

        @SerializedName("modelName")
        private String modelName;
        @SerializedName("profileName")
        private String profileName;

        public String getModelName() {
            return modelName;
        }

        public void setModelName(String modelName) {
            this.modelName = modelName;
        }

        public String getProfileName() {
            return profileName;
        }

        public void setProfileName(String profileName) {
            this.profileName = profileName;
        }

    }

    public class Response {

        @SerializedName("onuProfile")

        private ArrayList<OnuProfile> onuProfile = null;
        @SerializedName("towerDetail")
        private ArrayList<TowerDetail> towerDetail = null;
        @SerializedName("Onu")
        private String onu;
        @SerializedName("Status")
        private String status;
        @SerializedName("Towerdetails")
        private String towerdetails;
        @SerializedName("Building")
        private String building;
        @SerializedName("Society")
        private Object society;
        @SerializedName("AccountNumber")
        private String accountNumber;
        @SerializedName("Name")
        private String name;
        @SerializedName("City")
        private String city;
        @SerializedName("EmailId")
        private String emailId;
        @SerializedName("MobileNo")
        private String mobileNo;
        @SerializedName("PhoneNo")
        private String phoneNo;
        @SerializedName("Address")
        private String address;
        @SerializedName("Productname")
        private String productname;

        public ArrayList<OnuProfile> getOnuProfile() {
            return onuProfile;
        }

        public void setOnuProfile(ArrayList<OnuProfile> onuProfile) {
            this.onuProfile = onuProfile;
        }

        public ArrayList<TowerDetail> getTowerDetail() {
            return towerDetail;
        }

        public void setTowerDetail(ArrayList<TowerDetail> towerDetail) {
            this.towerDetail = towerDetail;
        }

        public String getOnu() {
            return onu;
        }

        public void setOnu(String onu) {
            this.onu = onu;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getTowerdetails() {
            return towerdetails;
        }

        public void setTowerdetails(String towerdetails) {
            this.towerdetails = towerdetails;
        }

        public String getBuilding() {
            return building;
        }

        public void setBuilding(String building) {
            this.building = building;
        }

        public Object getSociety() {
            return society;
        }

        public void setSociety(Object society) {
            this.society = society;
        }

        public String getAccountNumber() {
            return accountNumber;
        }

        public void setAccountNumber(String accountNumber) {
            this.accountNumber = accountNumber;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getEmailId() {
            return emailId;
        }

        public void setEmailId(String emailId) {
            this.emailId = emailId;
        }

        public String getMobileNo() {
            return mobileNo;
        }

        public void setMobileNo(String mobileNo) {
            this.mobileNo = mobileNo;
        }

        public String getPhoneNo() {
            return phoneNo;
        }

        public void setPhoneNo(String phoneNo) {
            this.phoneNo = phoneNo;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getProductname() {
            return productname;
        }

        public void setProductname(String productname) {
            this.productname = productname;
        }

    }

    public class TowerDetail {

        @SerializedName("towerId")
        private String towerId;
        @SerializedName("towerName")
        private String towerName;
        @SerializedName("servingDB")
        private String servingDB;

        public String getTowerId() {
            return towerId;
        }

        public void setTowerId(String towerId) {
            this.towerId = towerId;
        }

        public String getTowerName() {
            return towerName;
        }

        public void setTowerName(String towerName) {
            this.towerName = towerName;
        }

        public String getServingDB() {
            return servingDB;
        }

        public void setServingDB(String servingDB) {
            this.servingDB = servingDB;
        }

    }
}