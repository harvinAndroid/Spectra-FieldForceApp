package com.spectra.fieldforce.salesapp.model

data class GetOppurtunityProductRequest(
    val Action: String,
    val Authkey: String,
    val Oppid: String,
    val password: String,
    val userName: String
)