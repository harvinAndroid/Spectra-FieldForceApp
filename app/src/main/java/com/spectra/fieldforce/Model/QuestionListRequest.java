package com.spectra.fieldforce.Model;

public class QuestionListRequest {
    private String Authkey;
    private String Action;

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    private String Type;

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
}
