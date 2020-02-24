package com.example.fieldforceapp;

import com.example.fieldforceapp.Model.LoginRequest;
import com.google.gson.JsonElement;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiInterface {
    @POST ("index.php")
    Call< JsonElement > performUserLogin ( @Body LoginRequest loginRequest );
}
