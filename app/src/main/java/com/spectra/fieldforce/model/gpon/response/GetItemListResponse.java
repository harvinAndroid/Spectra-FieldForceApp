package com.spectra.fieldforce.model.gpon.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetItemListResponse {
    @SerializedName("Status")
    private Integer status;
    @SerializedName("Response")
    private Response response;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Response getResponse() {
        return response;
    }

    public void setResponse(Response response) {
        this.response = response;
    }

    public class Response {

        @SerializedName("Item_List")
        private ItemList itemList;

        public ItemList getItemList() {
            return itemList;
        }

        public void setItemList(ItemList itemList) {
            this.itemList = itemList;
        }

    }

    public class ItemList {

        @SerializedName("data")
        private List<Datum> data;
        @SerializedName("StatusCode")
        private Integer statusCode;
        @SerializedName("Message")
        private String message;

        public List<Datum> getData() {
            return data;
        }

        public void setData(List<Datum> data) {
            this.data = data;
        }

        public Integer getStatusCode() {
            return statusCode;
        }

        public void setStatusCode(Integer statusCode) {
            this.statusCode = statusCode;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

    }

    public class Datum {

        @SerializedName("itemId")
        private String itemId;
        @SerializedName("itemName")
        private String itemName;

        public String getItemId() {
            return itemId;
        }

        public void setItemId(String itemId) {
            this.itemId = itemId;
        }

        public String getItemName() {
            return itemName;
        }

        public void setItemName(String itemName) {
            this.itemName = itemName;
        }

    }
}
