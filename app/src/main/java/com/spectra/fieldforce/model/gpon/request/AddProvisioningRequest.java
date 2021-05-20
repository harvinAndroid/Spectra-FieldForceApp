package com.spectra.fieldforce.model.gpon.request;

public class AddProvisioningRequest {
    private String Authkey;
    private String Action;
    private String OnuSerial;
    private String Profile;
    private String Description;

    public void setAuthkey(String authkey) {
        Authkey = authkey;
    }

    public void setAction(String action) {
        Action = action;
    }

    public void setOnuSerial(String onuSerial) {
        OnuSerial = onuSerial;
    }

    public void setProfile(String profile) {
        Profile = profile;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public void setTowerId(String towerId) {
        TowerId = towerId;
    }

    public void setSplitterId(String splitterId) {
        SplitterId = splitterId;
    }

    public void setCanId(String canId) {
        CanId = canId;
    }

    private String TowerId;
    private String SplitterId;
    private String CanId;

}
