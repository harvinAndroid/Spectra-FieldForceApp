package com.spectra.fieldforce.salesapp.model

data class LeadSalutationRequest(
    val Action: String,
    val Authkey: String,
    val password: String,
    val userName: String
)