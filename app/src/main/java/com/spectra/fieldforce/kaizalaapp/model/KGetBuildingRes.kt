package com.spectra.fieldforce.kaizalaapp.model

data class KGetBuildingRes(
    val Response: MutableList<KBuildResponse>,
    val Status: String,
    val StatusCode: Int
)

data class KBuildResponse(
    val BuildingCode: String,
    val BuildingName: String
)

