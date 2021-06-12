package com.spectra.fieldforce.model.gpon.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class WcrResponse {
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

        @SerializedName("WCR")
        private Wcr wcr;
        @SerializedName("CusotmerNetwork")
        private CusotmerNetwork cusotmerNetwork;
        @SerializedName("InstallationQuality")
        private InstallationQuality installationQuality;
        @SerializedName("EngineerDetails")
        private EngineerDetails engineerDetails;
        @SerializedName("itemConsumtionList")
        private ArrayList<ItemConsumtion> itemConsumtionList;
        @SerializedName("EquipmentDetailsList")
        private ArrayList<EquipmentDetailsList> equipmentDetailsList;
        @SerializedName("ManHoleDetailsList")
        private ArrayList<ManHoleDetails> manHoleDetailsList;
        @SerializedName("Associated")
        private Associated associated;
        @SerializedName("FMSDetails")
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

        public ArrayList<ItemConsumtion> getItemConsumtionList() {
            return itemConsumtionList;
        }

        public void setItemConsumtionList(ArrayList<ItemConsumtion> itemConsumtionList) {
            this.itemConsumtionList = itemConsumtionList;
        }

        public ArrayList<EquipmentDetailsList> getEquipmentDetailsList() {
            return equipmentDetailsList;
        }

        public void setEquipmentDetailsList(ArrayList<EquipmentDetailsList> equipmentDetailsList) {
            this.equipmentDetailsList = equipmentDetailsList;
        }

        public ArrayList<ManHoleDetails> getManHoleDetailsList() {
            return manHoleDetailsList;
        }

        public void setManHoleDetailsList(ArrayList<ManHoleDetails> manHoleDetailsList) {
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

        public Integer getFiberNoRunningNoWise() {
            return fiberNoRunningNoWise;
        }

        public Integer getFiberTube() {
            return fiberTube;
        }

        public String getDistance() {
            return distance;
        }

        public String getManHoleType() {
            return manHoleType;
        }

        public Integer getFiberNoTubeWise() {
            return fiberNoTubeWise;
        }

        public String getFiberCable() {
            return fiberCable;
        }

        public String getLocationLandmark() {
            return locationLandmark;
        }

        public String getItemID() {
            return itemID;
        }

        public String getWcrguidid() {
            return wcrguidid;
        }

        public String getCanid() {
            return canid;
        }

        @SerializedName("CANID")
        @Expose
        private String canid;



    }


    public class FMSDetails {

        @SerializedName("fmsfirst")
        private String fmsfirst;
        @SerializedName("fmssecond")
        private String fmssecond;
        @SerializedName("podendfmsno")
        private String podendfmsno;
        @SerializedName("portnocxend")
        private String portnocxend;
        @SerializedName("portnopodend")
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

    public class Associated {

        @SerializedName("IDBlength")
        private String iDBlength;
        @SerializedName("LinkBudget")
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

    public class ItemConsumtion {

        @SerializedName("Item")
        private String item;
        @SerializedName("SubItem")
        private String subItem;
        @SerializedName("ItemType")
        private String itemType;
        @SerializedName("SerialNumber")
        private String serialNumber;
        @SerializedName("Quantity")
        private Integer quantity;
        @SerializedName("ItemID")
        private String itemID;
        @SerializedName("WCRGUIDID")
        private String wcrguidid;
        @SerializedName("CANID")
        private String canid;
        @SerializedName("ItemCode")
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

    public class EquipmentDetailsList{
        private String Item;
        private String SubItem;
        private String ItemCode;
        private String ItemType;
        private String Quantity;
        private String SerialNumber;
        private String ItemID;

        public String getItem() {
            return Item;
        }

        public String getSubItem() {
            return SubItem;
        }

        public String getItemCode() {
            return ItemCode;
        }

        public String getItemType() {
            return ItemType;
        }

        public String getQuantity() {
            return Quantity;
        }

        public String getSerialNumber() {
            return SerialNumber;
        }

        public String getItemID() {
            return ItemID;
        }

        public String getWCRGUIDID() {
            return WCRGUIDID;
        }

        public String getCANID() {
            return CANID;
        }

        private String WCRGUIDID;
        private String CANID;

    }
    public class EngineerDetails {

        @SerializedName("EngName")
        private String engName;
        @SerializedName("InstCode")
        private String instCode;
        @SerializedName("WCRCreatedon")
        private String wCRCreatedon;
        @SerializedName("InstallationDate")
        private String installationDate;
        @SerializedName("WCRRemarks")
        private String wCRRemarks;
        @SerializedName("HoldCategory")
        private String holdCategory;
        @SerializedName("HoldReason")
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
        private String wifiSSID;
        @SerializedName("RxPower")
        private String rxPower;
        @SerializedName("TxPower")
        private String txPower;
        @SerializedName("SpeedWifi")
        private Float speedWifi;
        @SerializedName("SpeedLan")
        private Float speedLan;

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

        public Float getSpeedWifi() {
            return speedWifi;
        }

        public void setSpeedWifi(Float speedWifi) {
            this.speedWifi = speedWifi;
        }

        public Float getSpeedLan() {
            return speedLan;
        }

        public void setSpeedLan(Float speedLan) {
            this.speedLan = speedLan;
        }

    }

    public class InstallationQuality {

        @SerializedName("Speed")
        private String speed;
        @SerializedName("Cable")
        private String cable;
        @SerializedName("Face")
        private String face;
        @SerializedName("ONT")
        private String ont;
        @SerializedName("Wifi")
        private String wifi;
        @SerializedName("SelfCare")
        private String selfCare;
        @SerializedName("AntiVirus")
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

    public class Wcr {

        @SerializedName("BusinessSegment")
        private String businessSegment;
        @SerializedName("Pod")
        private String pod;
        @SerializedName("Product")
        private String product;
        @SerializedName("Installationdate")
        private String installationdate;
        @SerializedName("WCRGUIDID")
        private String wcrguidid;
        @SerializedName("CANID")
        private String canid;
        @SerializedName("Techcontactnumber")
        private String techcontactnumber;
        @SerializedName("TechContactName")
        private String techContactName;
        @SerializedName("Rack")
        private String rack;
        @SerializedName("Redundacy")
        private String redundacy;
        @SerializedName("WCRConsumptionStatus")
        private String wCRConsumptionStatus;
        @SerializedName("ProductSegment")
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
