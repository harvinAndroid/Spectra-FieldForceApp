package com.spectra.fieldforce.salesapp.model

data class CreateQuoteRequest(
    val Action: String,
    val Authkey: String,
    val Oppid: String,
    val password: String,
    val userName: String
)