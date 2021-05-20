package com.spectra.fieldforce.model.gpon.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class WcrInfoResponse {
    @SerializedName("Status")
    @Expose
    private Integer status;
    @SerializedName("Response")
    @Expose
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

        @SerializedName("WCR")
        @Expose
        private Wcr wcr;
        @SerializedName("CusotmerNetwork")
        @Expose
        private CusotmerNetwork cusotmerNetwork;
        @SerializedName("InstallationQuality")
        @Expose
        private InstallationQuality installationQuality;
        @SerializedName("EngineerDetails")
        @Expose
        private EngineerDetails engineerDetails;
        @SerializedName("itemConsumtionList")
        @Expose
        private List<ItemConsumtion> itemConsumtionList;
        @SerializedName("EquipmentDetailsList")
        @Expose
        private List<EquipmentDetails> equipmentDetailsList;
        @SerializedName("ManHoleDetailsList")
        @Expose
        private List<ManHoleDetails> manHoleDetailsList;
        @SerializedName("Associated")
        @Expose
        private Associated associated;
        @SerializedName("FMSDetails")
        @Expose
        private FMSDetails fMSDetails;

        public Wcr getWcr() {
            return wcr;
        }

        public void setWcr(Wcr wcr) {
            this.wcr = wcr;
        }

        public CusotmerNetwork getCusotmerNetwork() {
            return cusotmerNetwork;
        }

        public void setCusotmerNetwork(CusotmerNetwork cusotmerNetwork) {
            this.cusotmerNetwork = cusotmerNetwork;
        }

        public InstallationQuality getInstallationQuality() {
            return installationQuality;
        }

        public void setInstallationQuality(InstallationQuality installationQuality) {
            this.installationQuality = installationQuality;
        }

        public EngineerDetails getEngineerDetails() {
            return engineerDetails;
        }

        public void setEngineerDetails(EngineerDetails engineerDetails) {
            this.engineerDetails = engineerDetails;
        }

        public List<ItemConsumtion> getItemConsumtionList() {
            return itemConsumtionList;
        }

        public void setItemConsumtionList(List<ItemConsumtion> itemConsumtionList) {
            this.itemConsumtionList = itemConsumtionList;
        }

        public List<EquipmentDetails> getEquipmentDetailsList() {
            return equipmentDetailsList;
        }

        public void setEquipmentDetailsList(List<EquipmentDetails> equipmentDetailsList) {
            this.equipmentDetailsList = equipmentDetailsList;
        }

        public List<ManHoleDetails> getManHoleDetailsList() {
            return manHoleDetailsList;
        }

        public void setManHoleDetailsList(List<ManHoleDetails> manHoleDetailsList) {
            this.manHoleDetailsList = manHoleDetailsList;
        }

        public Associated getAssociated() {
            return associated;
        }

        public void setAssociated(Associated associated) {
            this.associated = associated;
        }

        public FMSDetails getFMSDetails() {
            return fMSDetails;
        }

        public void setFMSDetails(FMSDetails fMSDetails) {
            this.fMSDetails = fMSDetails;
        }

    }

    public class ManHoleDetails {

        @SerializedName("FiberNoRunningNoWise")
        @Expose
        private Integer fiberNoRunningNoWise;
        @SerializedName("FiberTube")
        @Expose
        private Integer fiberTube;
        @SerializedName("Distance")
        @Expose
        private String distance;
        @SerializedName("ManHoleType")
        @Expose
        private String manHoleType;
        @SerializedName("FiberNoTubeWise")
        @Expose
        private Integer fiberNoTubeWise;
        @SerializedName("FiberCable")
        @Expose
        private String fiberCable;
        @SerializedName("LocationLandmark")
        @Expose
        private String locationLandmark;
        @SerializedName("itemID")
        @Expose
        private String itemID;
        @SerializedName("WCRGUIDID")
        @Expose
        private String wcrguidid;
        @SerializedName("CANID")
        @Expose
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

    public class EquipmentDetails {

        @SerializedName("Item")
        @Expose
        private String item;
        @SerializedName("SubItem")
        @Expose
        private String subItem;
        @SerializedName("ItemCode")
        @Expose
        private String itemCode;
        @SerializedName("ItemType")
        @Expose
        private String itemType;
        @SerializedName("Quantity")
        @Expose
        private String quantity;
        @SerializedName("SerialNumber")
        @Expose
        private String serialNumber;
        @SerializedName("ItemID")
        @Expose
        private String itemID;
        @SerializedName("WCRGUIDID")
        @Expose
        private String wcrguidid;
        @SerializedName("CANID")
        @Expose
        private String canid;

        public String getItem() {
            return item;
        }

        public void setItem(String item) {
            this.item = item;
        }

        public String getSubItem() {
            return subItem;
        }

        public void setSubItem(String subItem) {
            this.subItem = subItem;
        }

        public String getItemCode() {
            return itemCode;
        }

        public void setItemCode(String itemCode) {
            this.itemCode = itemCode;
        }

        public String getItemType() {
            return itemType;
        }

        public void setItemType(String itemType) {
            this.itemType = itemType;
        }

        public String getQuantity() {
            return quantity;
        }

        public void setQuantity(String quantity) {
            this.quantity = quantity;
        }

        public String getSerialNumber() {
            return serialNumber;
        }

        public void setSerialNumber(String serialNumber) {
            this.serialNumber = serialNumber;
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

    public class ItemConsumtion {

        @SerializedName("Item")
        @Expose
        private String item;
        @SerializedName("SubItem")
        @Expose
        private String subItem;
        @SerializedName("ItemType")
        @Expose
        private String itemType;
        @SerializedName("SerialNumber")
        @Expose
        private String serialNumber;
        @SerializedName("Quantity")
        @Expose
        private Integer quantity;
        @SerializedName("ItemID")
        @Expose
        private String itemID;
        @SerializedName("WCRGUIDID")
        @Expose
        private String wcrguidid;
        @SerializedName("CANID")
        @Expose
        private String canid;
        @SerializedName("ItemCode")
        @Expose
        private String itemCode;

        public String getItem() {
            return item;
        }

        public void setItem(String item) {
            this.item = item;
        }

        public String getSubItem() {
            return subItem;
        }

        public void setSubItem(String subItem) {
            this.subItem = subItem;
        }

        public String getItemType() {
            return itemType;
        }

        public void setItemType(String itemType) {
            this.itemType = itemType;
        }

        public String getSerialNumber() {
            return serialNumber;
        }

        public void setSerialNumber(String serialNumber) {
            this.serialNumber = serialNumber;
        }

        public Integer getQuantity() {
            return quantity;
        }

        public void setQuantity(Integer quantity) {
            this.quantity = quantity;
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

        public String getItemCode() {
            return itemCode;
        }

        public void setItemCode(String itemCode) {
            this.itemCode = itemCode;
        }

    }

    public class Associated {

        @SerializedName("IDBlength")
        @Expose
        private String iDBlength;
        @SerializedName("LinkBudget")
        @Expose
        private String linkBudget;

        public String getIDBlength() {
            return iDBlength;
        }

        public void setIDBlength(String iDBlength) {
            this.iDBlength = iDBlength;
        }

        public String getLinkBudget() {
            return linkBudget;
        }

        public void setLinkBudget(String linkBudget) {
            this.linkBudget = linkBudget;
        }

    }

    public class FMSDetails {

        @SerializedName("fmsfirst")
        @Expose
        private String fmsfirst;
        @SerializedName("fmssecond")
        @Expose
        private String fmssecond;
        @SerializedName("podendfmsno")
        @Expose
        private String podendfmsno;
        @SerializedName("portnocxend")
        @Expose
        private String portnocxend;
        @SerializedName("portnopodend")
        @Expose
        private String portnopodend;

        public String getFmsfirst() {
            return fmsfirst;
        }

        public void setFmsfirst(String fmsfirst) {
            this.fmsfirst = fmsfirst;
        }

        public String getFmssecond() {
            return fmssecond;
        }

        public void setFmssecond(String fmssecond) {
            this.fmssecond = fmssecond;
        }

        public String getPodendfmsno() {
            return podendfmsno;
        }

        public void setPodendfmsno(String podendfmsno) {
            this.podendfmsno = podendfmsno;
        }

        public String getPortnocxend() {
            return portnocxend;
        }

        public void setPortnocxend(String portnocxend) {
            this.portnocxend = portnocxend;
        }

        public String getPortnopodend() {
            return portnopodend;
        }

        public void setPortnopodend(String portnopodend) {
            this.portnopodend = portnopodend;
        }

    }

    public class InstallationQuality {

        @SerializedName("Speed")
        @Expose
        private String speed;
        @SerializedName("Cable")
        @Expose
        private String cable;
        @SerializedName("Face")
        @Expose
        private String face;
        @SerializedName("ONT")
        @Expose
        private String ont;
        @SerializedName("Wifi")
        @Expose
        private String wifi;
        @SerializedName("SelfCare")
        @Expose
        private String selfCare;
        @SerializedName("AntiVirus")
        @Expose
        private String antiVirus;

        public String getSpeed() {
            return speed;
        }

        public void setSpeed(String speed) {
            this.speed = speed;
        }

        public String getCable() {
            return cable;
        }

        public void setCable(String cable) {
            this.cable = cable;
        }

        public String getFace() {
            return face;
        }

        public void setFace(String face) {
            this.face = face;
        }

        public String getOnt() {
            return ont;
        }

        public void setOnt(String ont) {
            this.ont = ont;
        }

        public String getWifi() {
            return wifi;
        }

        public void setWifi(String wifi) {
            this.wifi = wifi;
        }

        public String getSelfCare() {
            return selfCare;
        }

        public void setSelfCare(String selfCare) {
            this.selfCare = selfCare;
        }

        public String getAntiVirus() {
            return antiVirus;
        }

        public void setAntiVirus(String antiVirus) {
            this.antiVirus = antiVirus;
        }

    }

    public class EngineerDetails {

        @SerializedName("EngName")
        @Expose
        private String engName;
        @SerializedName("InstCode")
        @Expose
        private String instCode;
        @SerializedName("WCRCreatedon")
        @Expose
        private String wCRCreatedon;
        @SerializedName("InstallationDate")
        @Expose
        private String installationDate;
        @SerializedName("WCRRemarks")
        @Expose
        private String wCRRemarks;
        @SerializedName("HoldCategory")
        @Expose
        private String holdCategory;
        @SerializedName("HoldReason")
        @Expose
        private String holdReason;

        public String getEngName() {
            return engName;
        }

        public void setEngName(String engName) {
            this.engName = engName;
        }

        public String getInstCode() {
            return instCode;
        }

        public void setInstCode(String instCode) {
            this.instCode = instCode;
        }

        public String getWCRCreatedon() {
            return wCRCreatedon;
        }

        public void setWCRCreatedon(String wCRCreatedon) {
            this.wCRCreatedon = wCRCreatedon;
        }

        public String getInstallationDate() {
            return installationDate;
        }

        public void setInstallationDate(String installationDate) {
            this.installationDate = installationDate;
        }

        public String getWCRRemarks() {
            return wCRRemarks;
        }

        public void setWCRRemarks(String wCRRemarks) {
            this.wCRRemarks = wCRRemarks;
        }

        public String getHoldCategory() {
            return holdCategory;
        }

        public void setHoldCategory(String holdCategory) {
            this.holdCategory = holdCategory;
        }

        public String getHoldReason() {
            return holdReason;
        }

        public void setHoldReason(String holdReason) {
            this.holdReason = holdReason;
        }

    }

    public class CusotmerNetwork {

        @SerializedName("WifiSSID")
        @Expose
        private String wifiSSID;
        @SerializedName("RxPower")
        @Expose
        private String rxPower;
        @SerializedName("TxPower")
        @Expose
        private String txPower;
        @SerializedName("SpeedWifi")
        @Expose
        private Integer speedWifi;
        @SerializedName("SpeedLan")
        @Expose
        private Integer speedLan;

        public String getWifiSSID() {
            return wifiSSID;
        }

        public void setWifiSSID(String wifiSSID) {
            this.wifiSSID = wifiSSID;
        }

        public String getRxPower() {
            return rxPower;
        }

        public void setRxPower(String rxPower) {
            this.rxPower = rxPower;
        }

        public String getTxPower() {
            return txPower;
        }

        public void setTxPower(String txPower) {
            this.txPower = txPower;
        }

        public Integer getSpeedWifi() {
            return speedWifi;
        }

        public void setSpeedWifi(Integer speedWifi) {
            this.speedWifi = speedWifi;
        }

        public Integer getSpeedLan() {
            return speedLan;
        }

        public void setSpeedLan(Integer speedLan) {
            this.speedLan = speedLan;
        }

    }

    public class Wcr {

        @SerializedName("BusinessSegment")
        @Expose
        private String businessSegment;
        @SerializedName("Pod")
        @Expose
        private String pod;
        @SerializedName("Product")
        @Expose
        private String product;
        @SerializedName("Installationdate")
        @Expose
        private String installationdate;
        @SerializedName("WCRGUIDID")
        @Expose
        private String wcrguidid;
        @SerializedName("CANID")
        @Expose
        private String canid;
        @SerializedName("Techcontactnumber")
        @Expose
        private String techcontactnumber;
        @SerializedName("TechContactName")
        @Expose
        private String techContactName;
        @SerializedName("Rack")
        @Expose
        private String rack;
        @SerializedName("Redundacy")
        @Expose
        private String redundacy;
        @SerializedName("WCRConsumptionStatus")
        @Expose
        private String wCRConsumptionStatus;
        @SerializedName("ProductSegment")
        @Expose
        private String productSegment;

        public String getBusinessSegment() {
            return businessSegment;
        }

        public void setBusinessSegment(String businessSegment) {
            this.businessSegment = businessSegment;
        }

        public String getPod() {
            return pod;
        }

        public void setPod(String pod) {
            this.pod = pod;
        }

        public String getProduct() {
            return product;
        }

        public void setProduct(String product) {
            this.product = product;
        }

        public String getInstallationdate() {
            return installationdate;
        }

        public void setInstallationdate(String installationdate) {
            this.installationdate = installationdate;
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

        public String getTechcontactnumber() {
            return techcontactnumber;
        }

        public void setTechcontactnumber(String techcontactnumber) {
            this.techcontactnumber = techcontactnumber;
        }

        public String getTechContactName() {
            return techContactName;
        }

        public void setTechContactName(String techContactName) {
            this.techContactName = techContactName;
        }

        public String getRack() {
            return rack;
        }

        public void setRack(String rack) {
            this.rack = rack;
        }

        public String getRedundacy() {
            return redundacy;
        }

        public void setRedundacy(String redundacy) {
            this.redundacy = redundacy;
        }

        public String getWCRConsumptionStatus() {
            return wCRConsumptionStatus;
        }

        public void setWCRConsumptionStatus(String wCRConsumptionStatus) {
            this.wCRConsumptionStatus = wCRConsumptionStatus;
        }

        public String getProductSegment() {
            return productSegment;
        }

        public void setProductSegment(String productSegment) {
            this.productSegment = productSegment;
        }

    }


}
