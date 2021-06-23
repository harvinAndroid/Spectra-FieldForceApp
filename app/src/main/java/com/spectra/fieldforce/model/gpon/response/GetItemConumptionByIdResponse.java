package com.spectra.fieldforce.model.gpon.response;

import com.google.gson.annotations.SerializedName;

public class GetItemConumptionByIdResponse {
    @SerializedName("Status")
    public String status;
    @SerializedName("Response")
    public Response response;

    public class Response{
        @SerializedName("Item")
        public String item;
        @SerializedName("SubItem")
        public String subItem;
        @SerializedName("ItemType")
        public String itemType;
        @SerializedName("SerialNumber")
        public String serialNumber;
        @SerializedName("Quantity")
        public String quantity;
        @SerializedName("ItemID")
        public String itemID;
        @SerializedName("WCRGUIDID")
        public String wCRGUIDID;
        @SerializedName("CANID")
        public String cANID;
        @SerializedName("ConsumptionType")
        public String consumptionType;
        @SerializedName("MacId")
        public String macId;
    }

}
