package com.spectra.fieldforce.kaizalaapp.model

data class KContactRespo(
    val Response: ConResponse,
    val Status: String,
    val StatusCode: String
)

data class ConResponse(
    val ContactId: String,
    val StatusCode: Int,
    val StatusName: String
)