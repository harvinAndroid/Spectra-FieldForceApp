package com.spectra.fieldforce.model.gpon.response;

import com.google.gson.annotations.SerializedName;

public class AccInfoResponse {
    @SerializedName("Status")
    public int status;
    @SerializedName("Response")
    public Response response;

    public class Response{
        @SerializedName("CANID")
        public String cANID;
        @SerializedName("Segment")
        public String segment;
        @SerializedName("Name")
        public String name;
        @SerializedName("City")
        public String city;
        @SerializedName("Society")
        public String society;
        @SerializedName("Area")
        public String area;
        @SerializedName("WCRStatus")
        public boolean wCRStatus;
        @SerializedName("IRStatus")
        public boolean iRStatus;
        @SerializedName("StatusofReport")
        public String statusofReport;
        @SerializedName("DOAFlag")
        public String dOAFlag;
    }
}
