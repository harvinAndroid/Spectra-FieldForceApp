package com.spectra.fieldforce.kaizalaapp.model

data class KGetSocietyRes(
    val Response: MutableList<SocietyResponse>,
    val Status: String,
    val StatusCode: String
)

data class SocietyResponse(
    val SocietyCode: String,
    val SocietyName: String
)