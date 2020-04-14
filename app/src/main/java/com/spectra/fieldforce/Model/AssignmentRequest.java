package com.spectra.fieldforce.Model;

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

    public String getUser_email ( String emailID ) {
        return emailID;
    }
    public void setemailID ( String emailID ) {
        this.emailID = emailID;
    }


}
