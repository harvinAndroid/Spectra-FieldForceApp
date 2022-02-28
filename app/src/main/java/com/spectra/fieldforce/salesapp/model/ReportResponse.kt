package com.spectra.fieldforce.salesapp.model

data class ReportResponse(
    val Response: RepResponse,
    val Status: String,
    val StatusCode: String
)

data class RepResponse(
    val Message: String,
    val StatusCode: Int
)