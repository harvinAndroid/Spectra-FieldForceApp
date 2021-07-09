package com.spectra.fieldforce.model.gpon.response;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class AccountDetailsResponse {

    @SerializedName("Status")
    public String status;
    @SerializedName("Response")
    public Response response;

    public String getStatus() {
        return status;
    }

    public Response getResponse() {
        return response;
    }

    public class OnuProfile{
        public String modelName;
        public String profileName;

        public String getModelName() {
            return modelName;
        }

        public String getProfileName() {
            return profileName;
        }
    }

    public class TowerDetail{
        public String towerId;
        public String towerName;
        public String servingDB;

        public String getTowerId() {
            return towerId;
        }

        public String getTowerName() {
            return towerName;
        }

        public String getServingDB() {
            return servingDB;
        }
    }

    public class Data{
        public ArrayList<OnuProfile> onuProfile;
        public ArrayList<TowerDetail> towerDetail;
        @SerializedName("Onu")
        public String onu;
        @SerializedName("Status")
        public String status;
        @SerializedName("Towerdetails")
        public String towerdetails;
        @SerializedName("Building")
        public String building;
        @SerializedName("Society")
        public String society;
        @SerializedName("AccountNumber")
        public String accountNumber;
        @SerializedName("Name")
        public String name;
        @SerializedName("City")
        public String city;
        @SerializedName("EmailId")
        public String emailId;
        @SerializedName("MobileNo")
        public String mobileNo;
        @SerializedName("PhoneNo")
        public String phoneNo;
        @SerializedName("Address")
        public String address;
        @SerializedName("Productname")
        public String productname;

        public ArrayList<OnuProfile> getOnuProfile() {
            return onuProfile;
        }

        public ArrayList<TowerDetail> getTowerDetail() {
            return towerDetail;
        }

        public String getOnu() {
            return onu;
        }

        public String getStatus() {
            return status;
        }

        public String getTowerdetails() {
            return towerdetails;
        }

        public String getBuilding() {
            return building;
        }

        public String getSociety() {
            return society;
        }

        public String getAccountNumber() {
            return accountNumber;
        }

        public String getName() {
            return name;
        }

        public String getCity() {
            return city;
        }

        public String getEmailId() {
            return emailId;
        }

        public String getMobileNo() {
            return mobileNo;
        }

        public String getPhoneNo() {
            return phoneNo;
        }

        public String getAddress() {
            return address;
        }

        public String getProductname() {
            return productname;
        }
    }

    public class Response{
        public Data data;

        public Data getData() {
            return data;
        }

        public int getStatusCode() {
            return statusCode;
        }

        public String getMessage() {
            return message;
        }

        @SerializedName("StatusCode")
        public int statusCode;
        @SerializedName("Message")
        public String message;
    }

}