package com.spectra.fieldforce.salesapp.model


data class ValidateSalesResponse(
    val Response: SalesResponse,
    val Status: String,
    val StatusCode: String
)

data class SalesResponse(
    val Message: String,
    val StatusCode: Int,
    val `data`: SalesData
)

data class SalesData(
    val EmployeeID: String,
    val FullName: String,
    val Manager: String,
    val MobilePhone: String,
    val OperatingCity: String,
    val Position: String,
    val PrimaryEmail: String,
    val UserName: String
)