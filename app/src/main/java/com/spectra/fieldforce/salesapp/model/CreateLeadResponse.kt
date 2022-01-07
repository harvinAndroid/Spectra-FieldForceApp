package com.spectra.fieldforce.salesapp.model

data class CreateLeadResponse(
    val Response: LeadResponse,
    val Status: String,
    val StatusCode: String
)

data class LeadResponse(
    val LeadId: String,
    val Message: String,
    val StatusCode: String
)