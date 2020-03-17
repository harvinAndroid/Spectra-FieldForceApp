package com.example.fieldforceapp;

import com.example.fieldforceapp.Model.SavetokenRequest;
import com.google.gson.JsonElement;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface SavetokenInterface {
    @POST ("index.php")
    Call< JsonElement > performSaveToken ( @Body SavetokenRequest savetokenRequest );

}
