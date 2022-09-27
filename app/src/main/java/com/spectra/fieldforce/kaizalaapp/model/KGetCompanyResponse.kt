package com.spectra.fieldforce.kaizalaapp.model

data class KGetCompanyResponse(
    val Response: MutableList<KCompanyResponse>,
    val Status: String,
    val StatusCode: String
)

data class KCompanyResponse(
    val Company_ID: String,
    val Company_Name: String,
    val Group_ID: String,
    val Group_Name: String
)