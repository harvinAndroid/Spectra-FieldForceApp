package com.spectra.fieldforce.kaizalaapp.model

data class KGetCafReq(
    val Action: String,
    val Authkey: String,
    val CAFId: String,
    val password: String,
    val userName: String
)