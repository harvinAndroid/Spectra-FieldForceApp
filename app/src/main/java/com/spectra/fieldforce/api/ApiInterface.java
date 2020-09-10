package com.spectra.fieldforce.api;

import com.google.gson.JsonElement;
import com.spectra.fieldforce.Model.ArtifactRequest;
import com.spectra.fieldforce.Model.AssignmentRequest;
import com.spectra.fieldforce.Model.CanIdRequest;
import com.spectra.fieldforce.Model.CanIdResponse;
import com.spectra.fieldforce.Model.ChangeBinRequest;
import com.spectra.fieldforce.Model.ChangeBinResponse;
import com.spectra.fieldforce.Model.CommonResponse;
import com.spectra.fieldforce.Model.EndtimeRequest;
import com.spectra.fieldforce.Model.LoginRequest;
import com.spectra.fieldforce.Model.MRTG;
import com.spectra.fieldforce.Model.MrtgRequest;
import com.spectra.fieldforce.Model.QuestionList.QuestionListRequest;
import com.spectra.fieldforce.Model.QuestionListResponse;
import com.spectra.fieldforce.Model.QuestionareList;
import com.spectra.fieldforce.Model.RCRequest;
import com.spectra.fieldforce.Model.SRRequest;
import com.spectra.fieldforce.Model.SaveQuestionareList.SaveQuestionareList;
import com.spectra.fieldforce.Model.SavetokenRequest;
import com.spectra.fieldforce.Model.SendChangeBinRequest;
import com.spectra.fieldforce.Model.SrDetailsListResponse;
import com.spectra.fieldforce.Model.StarttimeRequest;
import com.spectra.fieldforce.Model.questionAnsResponse.QuestionAnsList;
import com.spectra.fieldforce.Model.questionAnsResponse.QuestionAnswerList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;


public interface ApiInterface {

    @POST ("index.php")
    Call<JsonElement> performUserLogin(@Body LoginRequest loginRequest);
   // Call<User> performRegistration(@Query("name") String Name, @Query("user_name") String UserName, @Query("user_password") String UserPass);


    @POST ("index.php")
    Call<QuestionAnswerList> getQuestionList(@Body QuestionListRequest questionListRequest);


    @POST ("index.php")
    Call<CommonResponse> sendQuestionare(@Body SaveQuestionareList saveQuestionareList);


    @POST ("index.php")
    Call<CanIdResponse> getCanIdDetails(@Body CanIdRequest canIdRequest);

    @POST("index.php")
    Call<JsonElement> getSRDetail(@Body SRRequest srRequest);

    @POST ("index.php")
    Call< JsonElement > performOrderStarttime(@Body StarttimeRequest starttimeRequest);

    @POST ("index.php")
    Call< JsonElement > performSaveToken(@Body SavetokenRequest savetokenRequest);

    @POST("index.php")
    Call<JsonElement> getRCDetail(@Body RCRequest rcRequest);

    @POST("index.php")
    Call<MRTG> getMrtgRequest(@Body MrtgRequest mrtgRequest);

    @POST("index.php")
    Call<JsonElement> getChnageBinDetails(@Body ChangeBinRequest changeBinRequest);

    @POST("index.php")
    Call<ChangeBinResponse> sendBinDetails(@Body SendChangeBinRequest sendChangeBinRequest);

    @POST ("index.php")
    Call<SrDetailsListResponse> getSrDetailsList(@Body ChangeBinRequest changeBinRequest);

    @POST ("index.php")
    Call<JsonElement> UploadArtifacts(@Body ArtifactRequest artifactRequest);
/*

    @POST ("index.php")
    Call<QuestionareListResponse> getSaveQuestionAnsList(@Body GetQuestionAnsList getQuestionAnsList);
*/

    @POST ("index.php")
    Call< JsonElement > performUserAssignment ( @Body AssignmentRequest assignmentRequest );

    @POST ("index.php")
    Call< JsonElement > performOrderEndtime ( @Body EndtimeRequest endtimeRequest );
}
