package com.spectra.fieldforce.salesapp.model

data class LostOppurtunityRequest(
    val Action: String,
    val Authkey: String,
    val Oppid: String,
    val password: String?,
    val status: String,
    val userName: String?
)