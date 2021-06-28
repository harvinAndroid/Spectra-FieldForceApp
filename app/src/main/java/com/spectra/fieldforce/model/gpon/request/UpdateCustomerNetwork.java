package com.spectra.fieldforce.model.gpon.request;

public class UpdateCustomerNetwork {
    private String Authkey,Action,WCRguidId,WifiSSID,RxPower,TxPower,SpeedWifi,SpeedLan;

    public void setAuthkey(String authkey) {
        Authkey = authkey;
    }

    public void setAction(String action) {
        Action = action;
    }

    public void setWCRguidId(String WCRguidId) {
        this.WCRguidId = WCRguidId;
    }

    public void setWifiSSID(String wifiSSID) {
        WifiSSID = wifiSSID;
    }

    public void setRxPower(String rxPower) {
        RxPower = rxPower;
    }

    public void setTxPower(String txPower) {
        TxPower = txPower;
    }

    public void setSpeedWifi(String speedWifi) {
        SpeedWifi = speedWifi;
    }

    public void setSpeedLan(String speedLan) {
        SpeedLan = speedLan;
    }
}
