package com.spectra.fieldforce.salesapp.model

data class ValidateResponse(
    val Response: List<ResponseX>,
    val Status: String,
    val StatusCode: Int
)

data class ResponseX(
    val emailId: String,
    val field_force: String,
    val installation: String,
    val name: String,
    val userId: String,
    val vendorCode: String
)