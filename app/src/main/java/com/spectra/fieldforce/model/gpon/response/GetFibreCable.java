package com.spectra.fieldforce.model.gpon.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetFibreCable {
    @SerializedName("Status")
    public String status;
    @SerializedName("Response")
    public Response response;

    public class Response{
        @SerializedName("data")
        public List<Datum> data;
        @SerializedName("StatusCode")
        public int statusCode;
        @SerializedName("Message")
        public String message;
    }

    public class Datum{
        @SerializedName("Type")
        public String type;
        @SerializedName("value")
        public String value;
    }
}
