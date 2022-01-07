package com.spectra.fieldforce.salesapp.model

data class ValidateUserRequest(
    val Action: String,
    val Authkey: String,
    val UserName: String,
    val UserPass: String,
    val UserType: String
)