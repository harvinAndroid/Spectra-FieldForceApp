package com.spectra.fieldforce.kaizalaapp.model

data class KCafUpdateRes(
    val Response: UpdateResponse,
    val Status: String,
    val StatusCode: String
)

data class UpdateResponse(
    val CAFId: String,
    val DocId: String,
    val OTP: String,
    val OTPAuthenticationStatus: String,
    val StatusCode: Int,
    val StatusName: String
)