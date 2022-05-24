package com.spectra.fieldforce.salesapp.model

data class AllSafRes(
    val Response: SafResponse,
    val Status: String,
    val StatusCode: String
)

data class SafResponse(
    val Data: ArrayList<SafData>,
    val Message: String,
    val StatusCode: Int
)

data class SafData(
    val EmailId: String,
    val MobileNo: String,
    val OppId: String,
    val SafNo: String,
    val SafStatus: String,
    val ServiceID: String,
    val Vertical: String
)