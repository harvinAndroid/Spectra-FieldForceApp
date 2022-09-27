package com.spectra.fieldforce.kaizalaapp.model

data class KCafCreatedRespo(
    val Response: MutableList<KCafResponse>,
    val Status: String,
    val StatusCode: String
)

data class KCafResponse(
    val CAFId: String,
    val DocId: String,
    val OTP: String,
    val OTPAuthenticationStatus: String,
    val StatusCode: Int,
    val StatusName: String
)