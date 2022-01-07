package com.spectra.fieldforce.salesapp.model

data class GetApprovalRersponse(
    val Response: AppResponse,
    val Status: String,
    val StatusCode: String
)

data class AppResponse(
    val Data: ArrayList<AppData>,
    val Message: String,
    val StatusCode: Int
)

data class AppData(
    val ApprovalDate: String,
    val ApprovalRequestedDate: String,
    val Approver: String,
    val CreatedOn: String,
    val Name: String,
    val NextApproval: String,
    val Opportunity: String,
    val RejectedDate: String,
    val Status: String,
    val StatusReason: String
)