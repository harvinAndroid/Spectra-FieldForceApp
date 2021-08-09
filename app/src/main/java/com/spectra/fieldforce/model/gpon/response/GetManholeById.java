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
        private String fiberNoRunningNoWise;
        @SerializedName("FiberTube")
        private String fiberTube;
        @SerializedName("Distance")
        private String distance;
        @SerializedName("ManHoleType")
        private String manHoleType;
        @SerializedName("FiberNoTubeWise")
        private String fiberNoTubeWise;
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

        public String getFiberNoRunningNoWise() {
            return fiberNoRunningNoWise;
        }

        public void setFiberNoRunningNoWise(String fiberNoRunningNoWise) {
            this.fiberNoRunningNoWise = fiberNoRunningNoWise;
        }

        public String getFiberTube() {
            return fiberTube;
        }

        public void setFiberTube(String fiberTube) {
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

        public String getFiberNoTubeWise() {
            return fiberNoTubeWise;
        }

        public void setFiberNoTubeWise(String fiberNoTubeWise) {
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
