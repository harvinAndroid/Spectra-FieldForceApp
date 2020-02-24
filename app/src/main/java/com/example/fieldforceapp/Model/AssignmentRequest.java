package com.example.fieldforceapp.Model;

public class AssignmentRequest {

    String Authkey="";
    String Action="";
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

    public String getemailID () {
        return emailID;
    }
    public void setemailID ( String emailid ) {
        emailID = emailid;
    }
}
