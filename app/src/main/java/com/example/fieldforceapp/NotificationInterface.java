package com.example.fieldforceapp;
import com.example.fieldforceapp.Model.NotificationRequest;
import com.google.gson.JsonElement;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
public interface NotificationInterface {

    @POST ("index.php")
    Call< JsonElement > sendNotification( @Body NotificationRequest notificationRequest );
}
