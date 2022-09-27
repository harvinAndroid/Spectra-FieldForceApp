package com.spectra.fieldforce.kaizalaapp.model

data class KUpdateContactRes(
    val Response: Response,
    val Status: String,
    val StatusCode: String
)

data class Response(
    val ContactId: String,
    val StatusCode: Int,
    val StatusName: String
)