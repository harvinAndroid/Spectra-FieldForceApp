package com.spectra.fieldforce.salesapp.model

data class GetCompanyResponse(
    val Response: ComapnyResponse,
    val Status: String,
    val StatusCode: String
)

data class ComapnyResponse(
    val Data: ArrayList<ComapnyData>,
    val Message: String,
    val StatusCode: Int
)

data class ComapnyData(
    val Company_ID: String,
    val Company_Name: String,
    val Group_ID: String,
    val Group_Name: String
)