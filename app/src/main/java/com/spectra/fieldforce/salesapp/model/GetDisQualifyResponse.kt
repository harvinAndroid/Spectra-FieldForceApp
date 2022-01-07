package com.spectra.fieldforce.salesapp.model

data class GetDisQualifyResponse(
    val Response: DisqualifyResponse,
    val Status: String,
    val StatusCode: String
)

data class DisqualifyResponse(
    val Data: String,
    val Message: String,
    val StatusCode: Int
)