package com.spectra.fieldforce.kaizalaapp.model

data class KGetRelationRes(
    val Response: MutableList<KRelationResponse>,
    val Status: String,
    val StatusCode: String
)

data class KRelationResponse(
    val Company_ID: String,
    val Company_Name: String,
    val Relationship_ID: String,
    val Relationship_Name: String
)