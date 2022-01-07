package com.spectra.fieldforce.salesapp.model

data class GetLeadRequest(
    val Action: String,
    val Authkey: String,
    val LeadId: String,
    val password: String,
    val userName: String
)