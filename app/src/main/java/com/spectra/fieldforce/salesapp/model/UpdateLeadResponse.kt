package com.spectra.fieldforce.salesapp.model

data class UpdateLeadResponse(
    val Response: Response,
    val Status: String,
    val StatusCode: String
)

data class Response(
    val LeadId: String,
    val StatusCode: Int,
    val StatusName: String
)