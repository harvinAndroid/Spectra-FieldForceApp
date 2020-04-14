package com.spectra.fieldforce;

import com.spectra.fieldforce.Model.LoginRequest;
import com.google.gson.JsonElement;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;


public interface ApiInterface {
    @POST ("index.php")
    Call< JsonElement > performUserLogin ( @Body LoginRequest loginRequest );
   // Call<User> performRegistration(@Query("name") String Name, @Query("user_name") String UserName, @Query("user_password") String UserPass);

}
