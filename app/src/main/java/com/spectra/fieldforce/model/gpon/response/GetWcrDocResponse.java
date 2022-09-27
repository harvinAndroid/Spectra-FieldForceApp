package com.spectra.fieldforce.model.gpon.response;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class GetWcrDocResponse {
    @SerializedName("Status")
    public String status;
    @SerializedName("StatusCode")
    public String statusCode;
    @SerializedName("Response")
    public Response response;


    public class Datum {
        @SerializedName("Filename")
        public String filename;
        @SerializedName("Attachment")
        public String attachment;
    }

    public class Response {
        @SerializedName("StatusCode")
        public int statusCode;
        @SerializedName("Message")
        public String message;
        @SerializedName("Data")
        public ArrayList<Datum> data;
    }
}
