package com.spectra.fieldforce.salesapp.model

data class GetAllLeadRequest(
    val Action: String,
    val Authkey: String,
    val emailid: String,
    val password: String,
    val userName: String
)