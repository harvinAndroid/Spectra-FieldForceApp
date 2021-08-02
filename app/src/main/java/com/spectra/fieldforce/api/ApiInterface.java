package com.spectra.fieldforce.api;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.spectra.fieldforce.model.ArtifactRequest;
import com.spectra.fieldforce.model.AssignmentRequest;
import com.spectra.fieldforce.model.CanIdRequest;
import com.spectra.fieldforce.model.CanIdResponse;
import com.spectra.fieldforce.model.ChangeBinRequest;
import com.spectra.fieldforce.model.ChangeBinResponse;
import com.spectra.fieldforce.model.CommonResponse;
import com.spectra.fieldforce.model.EndtimeRequest;
import com.spectra.fieldforce.model.LoginResponse;
import com.spectra.fieldforce.model.gpon.request.AccountInfoRequest;
import com.spectra.fieldforce.model.gpon.request.AddBucketListRequest;
import com.spectra.fieldforce.model.gpon.request.AddItemConsumption;
import com.spectra.fieldforce.model.gpon.request.AddProvisioningRequest;
import com.spectra.fieldforce.model.gpon.request.AssociatedResquest;
import com.spectra.fieldforce.model.gpon.request.BucketListRequest;
import com.spectra.fieldforce.model.gpon.request.DeleteItemRequest;
import com.spectra.fieldforce.model.gpon.request.GetINSRequest;
import com.spectra.fieldforce.model.gpon.request.GetMaxCap;
import com.spectra.fieldforce.model.gpon.request.HoldWcrRequest;
import com.spectra.fieldforce.model.gpon.request.IRCompleteRequest;
import com.spectra.fieldforce.model.gpon.request.ItemConsumptionById;
import com.spectra.fieldforce.model.gpon.request.ReleaseMyBucket;
import com.spectra.fieldforce.model.gpon.request.ResendActivationCodeRequest;
import com.spectra.fieldforce.model.gpon.request.ResendCodeIRRequest;
import com.spectra.fieldforce.model.gpon.request.SubmitApprovalRequest;
import com.spectra.fieldforce.model.gpon.request.UpdateAppointmentRequest;
import com.spectra.fieldforce.model.gpon.request.UpdateCpeMacRequest;
import com.spectra.fieldforce.model.gpon.request.UpdateCustomerNetwork;
import com.spectra.fieldforce.model.gpon.request.UpdateFmsRequest;
import com.spectra.fieldforce.model.gpon.request.UpdateGeneralDetails;
import com.spectra.fieldforce.model.gpon.request.UpdateIREngineer;
import com.spectra.fieldforce.model.gpon.request.UpdateManHoleRequest;
import com.spectra.fieldforce.model.gpon.request.UpdateQualityParamIRequest;
import com.spectra.fieldforce.model.gpon.request.UpdateQualityParamRequest;
import com.spectra.fieldforce.model.gpon.request.UpdateWcrEnggRequest;
import com.spectra.fieldforce.model.gpon.request.UploadWcrImgRequest;
import com.spectra.fieldforce.model.gpon.request.WcrCompleteRequest;
import com.spectra.fieldforce.model.gpon.response.AccInfoResponse;
import com.spectra.fieldforce.model.gpon.response.AccountDetailsResponse;
import com.spectra.fieldforce.model.gpon.response.CommonClassResponse;
import com.spectra.fieldforce.model.gpon.response.GetAllBucketList;
import com.spectra.fieldforce.model.gpon.response.GetFibreCable;
import com.spectra.fieldforce.model.gpon.response.GetFmsListResponse;
import com.spectra.fieldforce.model.gpon.response.GetInsResponse;
import com.spectra.fieldforce.model.gpon.response.GetItemConumptionByIdResponse;
import com.spectra.fieldforce.model.gpon.response.GetItemListResponse;
import com.spectra.fieldforce.model.gpon.response.GetManholeById;
import com.spectra.fieldforce.model.gpon.response.GetMaxCapResponse;
import com.spectra.fieldforce.model.gpon.response.GetMyBucketList;
import com.spectra.fieldforce.model.gpon.response.GetSubItemListResponse;
import com.spectra.fieldforce.model.gpon.response.HoldReasonResponse;
import com.spectra.fieldforce.model.gpon.response.IrInfoResponse;
import com.spectra.fieldforce.model.gpon.response.WCRHoldCategoryResponse;
import com.spectra.fieldforce.model.ItemConsumption.DeleteItemConsumption;
import com.spectra.fieldforce.model.ItemConsumption.GetItemConsumption;
import com.spectra.fieldforce.model.ItemConsumption.GetItemConsumptionRequest;
import com.spectra.fieldforce.model.ItemConsumption.ItemConsumptionDetails;
import com.spectra.fieldforce.model.ItemConsumption.ItemConsumptionRequest;
import com.spectra.fieldforce.model.ItemConsumption.ItemResponse;
import com.spectra.fieldforce.model.ItemConsumption.ItemStatus;
import com.spectra.fieldforce.model.ItemConsumption.NrgpDetails;
import com.spectra.fieldforce.model.LoginRequest;
import com.spectra.fieldforce.model.MRTG;
import com.spectra.fieldforce.model.MrtgRequest;
import com.spectra.fieldforce.model.QuestionList.QuestionListRequest;
import com.spectra.fieldforce.model.RCRequest;
import com.spectra.fieldforce.model.SRRequest;
import com.spectra.fieldforce.model.SaveQuestionareList.SaveQuestionareList;
import com.spectra.fieldforce.model.SavetokenRequest;
import com.spectra.fieldforce.model.SendChangeBinRequest;
import com.spectra.fieldforce.model.SrDetailsListResponse;
import com.spectra.fieldforce.model.StarttimeRequest;
import com.spectra.fieldforce.model.gpon.response.WcrResponse;
import com.spectra.fieldforce.model.questionAnsResponse.QuestionAnswerList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;


public interface ApiInterface {

    @POST ("index.php")
    Call<LoginResponse> performUserLogin(@Body LoginRequest loginRequest);
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

    @POST ("index.php")
    Call<GetItemConsumption> getItemConsumption (@Body ItemConsumptionRequest itemConsumptionRequest );

    @POST("index.php")
    Call<CommonResponse> setUnholdStatus(@Body CanIdRequest canIdRequest);

    @POST ("index.php")
    Call<ItemResponse> addItemConsumption (@Body JsonObject object );

    @POST ("index.php")
    Call<ItemResponse> editItemConsumption (@Body JsonObject object );

    @POST ("index.php")
    Call<ItemConsumptionDetails> getItemConsumptionDetails (@Body GetItemConsumptionRequest getItemConsumptionRequest );

    @POST ("index.php")
    Call<ItemResponse> deleteItemConsumptionDetails (@Body DeleteItemConsumption deleteItemConsumption );

    @POST ("index.php")
    Call<CommonResponse> getStatus (@Body ChangeBinRequest changeBinRequest );


    @POST ("index.php")
    Call<CommonResponse> saveStatus (@Body ChangeBinRequest changeBinRequest );

    @POST ("index.php")
    Call<ItemStatus> getMaterialStatus (@Body ChangeBinRequest changeBinRequest );

    @POST ("index.php")
    Call<NrgpDetails> getNrgpData (@Body AssignmentRequest itemConsumptionRequest );


      @POST("index.php")
    Call<AccInfoResponse> getAccountInfo(@Body AccountInfoRequest accountInfoRequest);

    @POST ("index.php")
    Call<WcrResponse> getWcrInfo(@Body AccountInfoRequest accountInfoRequest);

    @POST ("index.php")
    Call<IrInfoResponse> getIrInfo(@Body AccountInfoRequest accountInfoRequest);

    @POST ("index.php")
    Call<WCRHoldCategoryResponse> getHoldCategory(@Body AccountInfoRequest accountInfoRequest);


    @POST ("index.php")
    Call<GetItemListResponse> getConsumptionItemList(@Body AccountInfoRequest accountInfoRequest);


    @POST ("index.php")
    Call<GetFmsListResponse> getFmsList(@Body AccountInfoRequest accountInfoRequest);


    @POST ("index.php")
    Call<GetItemListResponse> getItemListName(@Body AccountInfoRequest accountInfoRequest);

    @POST ("index.php")
    Call<GetSubItemListResponse> getSubItem(@Body ItemConsumptionById itemConsumptionById);

    @POST ("index.php")
    Call<GetFibreCable> getFibreList(@Body AccountInfoRequest accountInfoRequest);

    @POST ("index.php")
    Call<CommonClassResponse> addItemConsumption(@Body AddItemConsumption addItemConsumption);

    @POST ("index.php")
    Call<CommonClassResponse> updateAssociateDetails(@Body AssociatedResquest associatedResquest);

    @POST ("index.php")
    Call<CommonClassResponse> updsteFmsDetails(@Body UpdateFmsRequest updateFmsRequest);

    @POST ("index.php")
    Call<CommonClassResponse> updateWcrEng(@Body UpdateWcrEnggRequest updateWcrEnggRequest);

    @POST ("index.php")
    Call<GetItemConumptionByIdResponse> getItemDetailsById(@Body ItemConsumptionById itemConsumptionById);

    @POST ("index.php")
    Call<CommonResponse> wcrEnggDetails(@Body UpdateWcrEnggRequest updateWcrEnggRequest);

    @POST ("index.php")
    Call<CommonClassResponse> updateHoldCategory(@Body HoldWcrRequest holdWcrRequest);

    @POST ("index.php")
    Call<CommonClassResponse> wcrComplete(@Body WcrCompleteRequest wcrCompleteRequest);

    @POST ("index.php")
    Call<CommonClassResponse> resendCodeWcr(@Body ResendActivationCodeRequest resendActivationCodeRequest);

    @POST ("index.php")
    Call<CommonClassResponse> updateQualityParamIR(@Body UpdateQualityParamIRequest updateQualityParamIRequest);

    @POST("index.php")
    Call<AccountDetailsResponse> getAccountDetails(@Body AccountInfoRequest accountInfoRequest);

    @POST("index.php")
    Call<CommonClassResponse> addProvisioning(@Body AddProvisioningRequest addProvisioningRequest);

    @POST("index.php")
    Call<CommonClassResponse> addManholeItem(@Body UpdateManHoleRequest updateManHoleRequest);

    @POST("index.php")
    Call<GetAllBucketList> getAllBucketList(@Body BucketListRequest bucketListRequest);


    @POST("index.php")
    Call<CommonClassResponse> deleteItemById(@Body DeleteItemRequest deleteItemRequest);


    @POST("index.php")
    Call<GetManholeById> editManholeById(@Body UpdateManHoleRequest updateManHoleRequest);

    @POST("index.php")
    Call<CommonClassResponse> getResendCodeIR(@Body ResendCodeIRRequest resendActivationCodeRequest);

    @POST("index.php")
    Call<CommonClassResponse> updateGeneralDetails(@Body UpdateGeneralDetails updateGeneralDetails);


    @POST("index.php")
    Call<CommonClassResponse> updateIrEnginer(@Body UpdateIREngineer updateIREngineer);

    @POST("index.php")
    Call<CommonClassResponse> updateCpeMac(@Body UpdateCpeMacRequest updateCpeMacRequest);

    @POST("index.php")
    Call<CommonClassResponse> updateIR(@Body   IRCompleteRequest irCompleteRequest);

    @POST("index.php")
    Call<CommonClassResponse> addItemToBucket(@Body AddBucketListRequest addBucketListRequest);

    @POST("index.php")
    Call<GetMyBucketList> getMyBucketList(@Body BucketListRequest bucketListRequest);

    @POST("index.php")
    Call<CommonClassResponse> releaseMyBucket(@Body ReleaseMyBucket releaseMyBucket);

    @POST ("index.php")
    Call<GetMaxCapResponse> getMaxCapValue(@Body GetMaxCap getMaxCap);

    @POST ("index.php")
    Call<CommonClassResponse> updateCustomerNetwork(@Body UpdateCustomerNetwork updateCustomerNetwork);

    @POST ("index.php")
    Call<CommonClassResponse> updateQualityParamReq(@Body UpdateQualityParamRequest updateQualityParamRequest);

    @POST ("index.php")
    Call<CommonClassResponse> submitApproval(@Body SubmitApprovalRequest submitApprovalRequest);

    @POST ("index.php")
    Call<CommonClassResponse> updateAppointmentDate(@Body UpdateAppointmentRequest updateAppointmentRequest);

    @POST ("index.php")
    Call<GetInsResponse> getInsDetails(@Body GetINSRequest getINSRequest);

    @POST ("index.php")
    Call<CommonClassResponse> UploadWcr(@Body UploadWcrImgRequest uploadWcrImgRequest);

    @POST ("index.php")
    Call<HoldReasonResponse> getHoldReason(@Body AccountInfoRequest accountInfoRequest);


}
