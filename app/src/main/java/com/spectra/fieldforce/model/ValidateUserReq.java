package com.spectra.fieldforce.model;

public class ValidateUserReq {
    private String Authkey;
    private String Action;
    private String UName;
    private String Password;

    public ValidateUserReq(String authkey, String action, String UName, String password) {
        this.Authkey = authkey;
        this.Action = action;
        this.UName = UName;
        this.Password = password;
    }
}
