package com.spectra.fieldforce.kaizalaapp.model

data class KGetAllContact(
    val Response: ArrayList<KContactResponse>,
    val Status: String,
    val StatusCode: String
)

data class KContactResponse(
    val Data: ArrayList<KContactData>,
    val Message: String,
    val StatusCode: Int
)

data class KContactData(
    val ContactId: String,
    val EmailAddress: String,
    val FullName: String,
    val MobileNumber: String,
    val Status: String
)