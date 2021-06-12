package com.spectra.fieldforce.model.gpon.request;

public class UpdateGeneralDetails {
    private String Authkey;
    private String Action;
    private String IRguid;
    private String ProvisionGuid;
    private String IpAddress;
    private String DevicePort;
    private String First;
    private String Last;
    private String Subnet;

    public void setAuthkey(String authkey) {
        Authkey = authkey;
    }

    public void setAction(String action) {
        Action = action;
    }

    public void setIRguid(String IRguid) {
        this.IRguid = IRguid;
    }

    public void setProvisionGuid(String provisionGuid) {
        ProvisionGuid = provisionGuid;
    }

    public void setIpAddress(String ipAddress) {
        IpAddress = ipAddress;
    }

    public void setDevicePort(String devicePort) {
        DevicePort = devicePort;
    }

    public void setFirst(String first) {
        First = first;
    }

    public void setLast(String last) {
        Last = last;
    }

    public void setSubnet(String subnet) {
        Subnet = subnet;
    }

    public void setGateway(String gateway) {
        Gateway = gateway;
    }

    public void setPrimary(String primary) {
        Primary = primary;
    }

    public void setSecondary(String secondary) {
        Secondary = secondary;
    }

    public void setDeviceId(String deviceId) {
        DeviceId = deviceId;
    }

    public void setCpe(String cpe) {
        Cpe = cpe;
    }

    public void setIRAttached(String IRAttached) {
        this.IRAttached = IRAttached;
    }

    private String Gateway;
    private String Primary;
    private String Secondary;
    private String DeviceId;
    private String Cpe;
    private String IRAttached;


}
