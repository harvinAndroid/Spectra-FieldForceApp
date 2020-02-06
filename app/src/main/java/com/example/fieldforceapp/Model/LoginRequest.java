package com.example.fieldforceapp.Model;

public class LoginRequest {


String Authkey="";

String Action="";
String user_name="";
String user_password="";

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

    public String getUser_name () {
        return user_name;
    }

    public void setUser_name ( String user_name ) {
        this.user_name = user_name;
    }

    public String getUser_password () {
        return user_password;
    }

    public void setUser_password ( String user_password ) {
        this.user_password = user_password;
    }
}
