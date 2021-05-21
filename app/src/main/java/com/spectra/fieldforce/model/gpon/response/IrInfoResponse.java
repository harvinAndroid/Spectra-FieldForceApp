package com.spectra.fieldforce.model.gpon.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class IrInfoResponse {
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

    public class General {

        @SerializedName("provisionId")
        @Expose
        private String provisionId;
        @SerializedName("IpAddress")
        @Expose
        private String ipAddress;
        @SerializedName("Gateway")
        @Expose
        private String gateway;
        @SerializedName("DevicePort")
        @Expose
        private String devicePort;
        @SerializedName("Primary")
        @Expose
        private String primary;
        @SerializedName("First")
        @Expose
        private String first;
        @SerializedName("Secondary")
        @Expose
        private String secondary;
        @SerializedName("Last")
        @Expose
        private String last;
        @SerializedName("DeviceId")
        @Expose
        private String deviceId;
        @SerializedName("Subnet")
        @Expose
        private String subnet;
        @SerializedName("CPE")
        @Expose
        private String cpe;
        @SerializedName("IRAttached")
        @Expose
        private String iRAttached;

        public String getProvisionId() {
            return provisionId;
        }

        public void setProvisionId(String provisionId) {
            this.provisionId = provisionId;
        }

        public String getIpAddress() {
            return ipAddress;
        }

        public void setIpAddress(String ipAddress) {
            this.ipAddress = ipAddress;
        }

        public String getGateway() {
            return gateway;
        }

        public void setGateway(String gateway) {
            this.gateway = gateway;
        }

        public String getDevicePort() {
            return devicePort;
        }

        public void setDevicePort(String devicePort) {
            this.devicePort = devicePort;
        }

        public String getPrimary() {
            return primary;
        }

        public void setPrimary(String primary) {
            this.primary = primary;
        }

        public String getFirst() {
            return first;
        }

        public void setFirst(String first) {
            this.first = first;
        }

        public String getSecondary() {
            return secondary;
        }

        public void setSecondary(String secondary) {
            this.secondary = secondary;
        }

        public String getLast() {
            return last;
        }

        public void setLast(String last) {
            this.last = last;
        }

        public String getDeviceId() {
            return deviceId;
        }

        public void setDeviceId(String deviceId) {
            this.deviceId = deviceId;
        }

        public String getSubnet() {
            return subnet;
        }

        public void setSubnet(String subnet) {
            this.subnet = subnet;
        }

        public String getCpe() {
            return cpe;
        }

        public void setCpe(String cpe) {
            this.cpe = cpe;
        }

        public String getIRAttached() {
            return iRAttached;
        }

        public void setIRAttached(String iRAttached) {
            this.iRAttached = iRAttached;
        }

    }

    public class Response {

        @SerializedName("IR")
        @Expose
        private Ir ir;
        @SerializedName("itemConsumtionList")
        @Expose
        private List<ItemConsumtion> itemConsumtionList;
        @SerializedName("InstallationItemList")
        @Expose
        private List<InstallationItemList> installationItemList ;
        @SerializedName("General")
        @Expose
        private General general;
        @SerializedName("Engineer")
        @Expose
        private Engineer engineer;
        @SerializedName("InstallationQty")
        @Expose
        private InstallationQty installationQty;

        public Ir getIr() {
            return ir;
        }

        public void setIr(Ir ir) {
            this.ir = ir;
        }

        public List<ItemConsumtion> getItemConsumtionList() {
            return itemConsumtionList;
        }

        public void setItemConsumtionList(List<ItemConsumtion> itemConsumtionList) {
            this.itemConsumtionList = itemConsumtionList;
        }

        public List<InstallationItemList> getInstallationItemList() {
            return installationItemList;
        }

        public void setInstallationItemList(List<InstallationItemList> installationItemList) {
            this.installationItemList = installationItemList;
        }

        public General getGeneral() {
            return general;
        }

        public void setGeneral(General general) {
            this.general = general;
        }

        public Engineer getEngineer() {
            return engineer;
        }

        public void setEngineer(Engineer engineer) {
            this.engineer = engineer;
        }

        public InstallationQty getInstallationQty() {
            return installationQty;
        }

        public void setInstallationQty(InstallationQty installationQty) {
            this.installationQty = installationQty;
        }

    }

    public class InstallationItemList{
        private String Item;
        private String SubItem;
        private String ItemType;
        private String Quantity;
        private String ItemID;

        public String getItem() {
            return Item;
        }

        public String getSubItem() {
            return SubItem;
        }

        public String getItemType() {
            return ItemType;
        }

        public String getQuantity() {
            return Quantity;
        }

        public String getItemID() {
            return ItemID;
        }

        public String getSerialNumber() {
            return SerialNumber;
        }

        public String getIRGUID() {
            return IRGUID;
        }

        public String getCANID() {
            return CANID;
        }

        public String getItemCode() {
            return ItemCode;
        }

        private String SerialNumber;
        private String IRGUID;
        private String CANID;
        private String ItemCode;

    }

    public class Engineer {

        @SerializedName("EngName")
        @Expose
        private String engName;
        @SerializedName("InstallationDate")
        @Expose
        private String installationDate;
        @SerializedName("BillingCommencementDate")
        @Expose
        private String billingCommencementDate;
        @SerializedName("OTP")
        @Expose
        private String otp;
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

        public String getInstallationDate() {
            return installationDate;
        }

        public void setInstallationDate(String installationDate) {
            this.installationDate = installationDate;
        }

        public String getBillingCommencementDate() {
            return billingCommencementDate;
        }

        public void setBillingCommencementDate(String billingCommencementDate) {
            this.billingCommencementDate = billingCommencementDate;
        }

        public String getOtp() {
            return otp;
        }

        public void setOtp(String otp) {
            this.otp = otp;
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

    public class InstallationQty {

        @SerializedName("Speed")
        @Expose
        private String speed;
        @SerializedName("SelfCare")
        @Expose
        private String selfCare;
        @SerializedName("DNS")
        @Expose
        private String dns;
        @SerializedName("MRTG")
        @Expose
        private String mrtg;
        @SerializedName("IP")
        @Expose
        private String ip;
        @SerializedName("AntiVirus")
        @Expose
        private String antiVirus;

        public String getSpeed() {
            return speed;
        }

        public void setSpeed(String speed) {
            this.speed = speed;
        }

        public String getSelfCare() {
            return selfCare;
        }

        public void setSelfCare(String selfCare) {
            this.selfCare = selfCare;
        }

        public String getDns() {
            return dns;
        }

        public void setDns(String dns) {
            this.dns = dns;
        }

        public String getMrtg() {
            return mrtg;
        }

        public void setMrtg(String mrtg) {
            this.mrtg = mrtg;
        }

        public String getIp() {
            return ip;
        }

        public void setIp(String ip) {
            this.ip = ip;
        }

        public String getAntiVirus() {
            return antiVirus;
        }

        public void setAntiVirus(String antiVirus) {
            this.antiVirus = antiVirus;
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
        @SerializedName("IRGUID")
        @Expose
        private String irguid;
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

        public String getIrguid() {
            return irguid;
        }

        public void setIrguid(String irguid) {
            this.irguid = irguid;
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

    public class Ir {

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
        @SerializedName("IRGUID")
        @Expose
        private String irguid;
        @SerializedName("CANID")
        @Expose
        private String canid;
        @SerializedName("ConsumptionStatus")
        @Expose
        private String consumptionStatus;
        @SerializedName("MACShared")
        @Expose
        private String mACShared;

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

        public String getIrguid() {
            return irguid;
        }

        public void setIrguid(String irguid) {
            this.irguid = irguid;
        }

        public String getCanid() {
            return canid;
        }

        public void setCanid(String canid) {
            this.canid = canid;
        }

        public String getConsumptionStatus() {
            return consumptionStatus;
        }

        public void setConsumptionStatus(String consumptionStatus) {
            this.consumptionStatus = consumptionStatus;
        }

        public String getMACShared() {
            return mACShared;
        }

        public void setMACShared(String mACShared) {
            this.mACShared = mACShared;
        }

    }


}
