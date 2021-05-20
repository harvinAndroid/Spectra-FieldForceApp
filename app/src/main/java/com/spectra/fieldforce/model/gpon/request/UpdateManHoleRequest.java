package com.spectra.fieldforce.model.gpon.request;

public class UpdateManHoleRequest {
    private String Authkey;
    private String Action;
    private String ItemID;
    private String CanId;
    private String WCRguidId;
    private String FiberNoRunningNoWise;
    private String FiberTube;
    private String Distance;

    public String getAuthkey() {
        return Authkey;
    }

    public void setAuthkey(String authkey) {
        Authkey = authkey;
    }

    public String getAction() {
        return Action;
    }

    public void setAction(String action) {
        Action = action;
    }

    public String getItemID() {
        return ItemID;
    }

    public void setItemID(String itemID) {
        ItemID = itemID;
    }

    public String getCanId() {
        return CanId;
    }

    public void setCanId(String canId) {
        CanId = canId;
    }

    public String getWCRguidId() {
        return WCRguidId;
    }

    public void setWCRguidId(String WCRguidId) {
        this.WCRguidId = WCRguidId;
    }

    public String getFiberNoRunningNoWise() {
        return FiberNoRunningNoWise;
    }

    public void setFiberNoRunningNoWise(String fiberNoRunningNoWise) {
        FiberNoRunningNoWise = fiberNoRunningNoWise;
    }

    public String getFiberTube() {
        return FiberTube;
    }

    public void setFiberTube(String fiberTube) {
        FiberTube = fiberTube;
    }

    public String getDistance() {
        return Distance;
    }

    public void setDistance(String distance) {
        Distance = distance;
    }

    public String getManHoleType() {
        return ManHoleType;
    }

    public void setManHoleType(String manHoleType) {
        ManHoleType = manHoleType;
    }

    public String getFiberNoTubeWise() {
        return FiberNoTubeWise;
    }

    public void setFiberNoTubeWise(String fiberNoTubeWise) {
        FiberNoTubeWise = fiberNoTubeWise;
    }

    public String getFiberCable() {
        return FiberCable;
    }

    public void setFiberCable(String fiberCable) {
        FiberCable = fiberCable;
    }

    public String getLocationLandmark() {
        return LocationLandmark;
    }

    public void setLocationLandmark(String locationLandmark) {
        LocationLandmark = locationLandmark;
    }

    private String ManHoleType;
    private String FiberNoTubeWise;
    private String FiberCable;
    private String LocationLandmark;


}
