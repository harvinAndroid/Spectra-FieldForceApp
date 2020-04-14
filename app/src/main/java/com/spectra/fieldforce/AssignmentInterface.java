package com.spectra.fieldforce;
import com.spectra.fieldforce.Model.AssignmentRequest;
import com.google.gson.JsonElement;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface AssignmentInterface {
    @POST ("index.php")
    Call< JsonElement > performUserAssignment ( @Body AssignmentRequest assignmentRequest );
}
