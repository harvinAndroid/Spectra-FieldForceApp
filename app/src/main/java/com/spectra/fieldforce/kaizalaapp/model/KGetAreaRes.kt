package com.spectra.fieldforce.kaizalaapp.model

data class KGetAreaRes(
    val Response: MutableList<KAreaResponse>,
    val Status: String,
    val StatusCode: Int
)

data class KAreaResponse(
    val AreaCode: String,
    val AreaName: String
)
