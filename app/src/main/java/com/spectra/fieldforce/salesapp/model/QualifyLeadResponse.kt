package com.spectra.fieldforce.salesapp.model

data class QualifyLeadResponse(
    val Response: QualifyResponse,
    val Status: String,
    val StatusCode: String
)

data class QualifyResponse(
    val Id: String,
    val Message: String,
    val StatusCode: Int
)