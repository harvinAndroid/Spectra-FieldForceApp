package com.spectra.fieldforce.model;

public class ArtifactRequest {
    private String Authkey;
    private String Action;
    private String SrNumber;
    private String EmailId;
    private String router_position_extension;
    private String other_in_ml_extension;
    private String router_position;
    private String speed_on_wifi;
    private String other_in_ml;

    public String getRouter_position() {
        return router_position;
    }

    public void setRouter_position(String router_position) {
        this.router_position = router_position;
    }

    public String getSpeed_on_wifi() {
        return speed_on_wifi;
    }

    public void setSpeed_on_wifi(String speed_on_wifi) {
        this.speed_on_wifi = speed_on_wifi;
    }

    public String getOther_in_ml() {
        return other_in_ml;
    }

    public void setOther_in_ml(String other_in_ml) {
        this.other_in_ml = other_in_ml;
    }

    public String getSpeed_on_lan() {
        return speed_on_lan;
    }

    public void setSpeed_on_lan(String speed_on_lan) {
        this.speed_on_lan = speed_on_lan;
    }

    private String speed_on_lan;


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

    public String getSrNumber() {
        return SrNumber;
    }

    public void setSrNumber(String srNumber) {
        SrNumber = srNumber;
    }

    public String getEmailId() {
        return EmailId;
    }

    public void setEmailId(String EmailId) {
        this.EmailId = EmailId;
    }

    public String getRouter_position_extension() {
        return router_position_extension;
    }

    public void setRouter_position_extension(String router_position_extension) {
        this.router_position_extension = router_position_extension;
    }

    public String getOther_in_ml_extension() {
        return other_in_ml_extension;
    }

    public void setOther_in_ml_extension(String other_in_ml_extension) {
        this.other_in_ml_extension = other_in_ml_extension;
    }

    public String getSpeed_on_lan_extension() {
        return speed_on_lan_extension;
    }

    public void setSpeed_on_lan_extension(String speed_on_lan_extension) {
        this.speed_on_lan_extension = speed_on_lan_extension;
    }

    public String getSpeed_on_wifi_extension() {
        return speed_on_wifi_extension;
    }

    public void setSpeed_on_wifi_extension(String speed_on_wifi_extension) {
        this.speed_on_wifi_extension = speed_on_wifi_extension;
    }

    private String speed_on_lan_extension;
    private String speed_on_wifi_extension;
}
