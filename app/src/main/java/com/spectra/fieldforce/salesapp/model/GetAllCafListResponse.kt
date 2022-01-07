package com.spectra.fieldforce.salesapp.model

data class GetAllCafListResponse(
    val Response: CafResponse,
    val Status: String,
    val StatusCode: String
)

data class CafResponse(
    val Data: ArrayList<CafData>,
    val Message: String,
    val StatusCode: Int
)

data class CafData(
    val Cafid: String,
    val CreatedOn: String,
    val EmailID: String,
    val LeadId: String,
    val MobileNo: String,
    val OpportunityId: String,
    val Status: String
)