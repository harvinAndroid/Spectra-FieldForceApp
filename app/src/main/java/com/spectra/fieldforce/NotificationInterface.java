package com.spectra.fieldforce;
import com.spectra.fieldforce.Model.NotificationRequest;
import com.google.gson.JsonElement;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
public interface NotificationInterface {

    @POST ("index.php")
    Call< JsonElement > sendNotification( @Body NotificationRequest notificationRequest );
}
