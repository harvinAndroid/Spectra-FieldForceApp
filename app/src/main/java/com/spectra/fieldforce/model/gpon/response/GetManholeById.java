package com.spectra.fieldforce.model.gpon.response;

import com.google.gson.annotations.SerializedName;

public class GetManholeById {
    @SerializedName("Status")
    private String status;
    @SerializedName("Response")
    private Response response;

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


    public class Response {

        @SerializedName("FiberNoRunningNoWise")
        private Integer fiberNoRunningNoWise;
        @SerializedName("FiberTube")
        private Integer fiberTube;
        @SerializedName("Distance")
        private String distance;
        @SerializedName("ManHoleType")
        private String manHoleType;
        @SerializedName("FiberNoTubeWise")
        private Integer fiberNoTubeWise;
        @SerializedName("FiberCable")
        private String fiberCable;
        @SerializedName("LocationLandmark")
        private String locationLandmark;
        @SerializedName("itemID")
        private String itemID;
        @SerializedName("WCRGUIDID")
        private String wcrguidid;
        @SerializedName("CANID")
        private String canid;

        public Integer getFiberNoRunningNoWise() {
            return fiberNoRunningNoWise;
        }

        public void setFiberNoRunningNoWise(Integer fiberNoRunningNoWise) {
            this.fiberNoRunningNoWise = fiberNoRunningNoWise;
        }

        public Integer getFiberTube() {
            return fiberTube;
        }

        public void setFiberTube(Integer fiberTube) {
            this.fiberTube = fiberTube;
        }

        public String getDistance() {
            return distance;
        }

        public void setDistance(String distance) {
            this.distance = distance;
        }

        public String getManHoleType() {
            return manHoleType;
        }

        public void setManHoleType(String manHoleType) {
            this.manHoleType = manHoleType;
        }

        public Integer getFiberNoTubeWise() {
            return fiberNoTubeWise;
        }

        public void setFiberNoTubeWise(Integer fiberNoTubeWise) {
            this.fiberNoTubeWise = fiberNoTubeWise;
        }

        public String getFiberCable() {
            return fiberCable;
        }

        public void setFiberCable(String fiberCable) {
            this.fiberCable = fiberCable;
        }

        public String getLocationLandmark() {
            return locationLandmark;
        }

        public void setLocationLandmark(String locationLandmark) {
            this.locationLandmark = locationLandmark;
        }

        public String getItemID() {
            return itemID;
        }

        public void setItemID(String itemID) {
            this.itemID = itemID;
        }

        public String getWcrguidid() {
            return wcrguidid;
        }

        public void setWcrguidid(String wcrguidid) {
            this.wcrguidid = wcrguidid;
        }

        public String getCanid() {
            return canid;
        }

        public void setCanid(String canid) {
            this.canid = canid;
        }

    }
}
