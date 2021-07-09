package com.spectra.fieldforce.model.gpon.response;

import com.google.gson.annotations.SerializedName;

public class GetMaxCapResponse {
    @SerializedName("Status")
    public String status;
    @SerializedName("Response")
    public Response response;

    public class Data{
        public String maxCap;
    }

    public class Response{
        public Data data;
        @SerializedName("StatusCode")
        public int statusCode;
        @SerializedName("Message")
        public String message;
    }

}
