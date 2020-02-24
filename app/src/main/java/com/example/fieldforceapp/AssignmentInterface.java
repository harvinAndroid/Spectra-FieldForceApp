package com.example.fieldforceapp;
import com.example.fieldforceapp.Model.AssignmentRequest;
import com.google.gson.JsonElement;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface AssignmentInterface {
    @POST ("index.php")
    Call< JsonElement > performUserAssignment ( @Body AssignmentRequest assignmentRequest );
}
