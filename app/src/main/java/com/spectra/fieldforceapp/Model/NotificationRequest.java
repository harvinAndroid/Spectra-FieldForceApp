package com.spectra.fieldforceapp.Model;

public class NotificationRequest {
    String AuthKey="";
    String Action="";
    String SenderId="";
    String FCMToken="";
    String NotiBody="";
    String NotiTitle="";
    String NotiIcon="";

    public String getAuthKey () {
        return AuthKey;
    }

    public void setAuthKey ( String authKey ) {
        AuthKey = authKey;
    }

    public String getAction () {
        return Action;
    }

    public void setAction ( String action ) {
        Action = action;
    }

    public String getSenderId () {
        return SenderId;
    }

    public void setSenderId ( String senderId ) {
        SenderId = senderId;
    }

    public String getFcmToken () {
        return FCMToken;
    }

    public void setFcmToken ( String fcmToken ) {
        FCMToken = fcmToken;
    }

    public String getNotiBody () {
        return NotiBody;
    }

    public void setNotiBody ( String notiBody ) {
        NotiBody = notiBody;
    }

    public String getNotiTitle () {
        return NotiTitle;
    }

    public void setNotiTitle ( String notiTitle ) {
        this.NotiTitle = notiTitle;
    }

    public String getNotiIcon () {
        return NotiIcon;
    }

    public void setNotiIcon ( String notiIcon ) {
        this.NotiIcon = notiIcon;
    }
}
