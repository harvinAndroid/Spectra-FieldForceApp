package com.example.fieldforceapp.Model;

public class NotificationRequest {
    String Authkey="";
    String Action="";
    String token="";
    String emailID="";

    public String getAuthkey () {
        return Authkey;
    }

    public void setAuthkey ( String authkey ) {
        Authkey = authkey;
    }

    public String getAction () {
        return Action;
    }

    public void setAction ( String action ) {
        Action = action;
    }

    public String getToken () {
        return token;
    }

    public void setToken ( String token ) {
        this.token = token;
    }

    public String getEmailID () {
        return emailID;
    }

    public void setEmailID ( String token ) {
        this.emailID = emailID;
    }
}
