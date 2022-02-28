package com.spectra.fieldforce.salesapp.model

data class GetAllContactResponse(
    val Response: ContactResponse,
    val Status: String,
    val StatusCode: String
)

data class ContactResponse(
    val Data: ArrayList<ContactData>,
    val Message: String,
    val StatusCode: Int
)

data class ContactData(
    val ContactId: String,
    val CreatedOn: String,
    val EmailAddress: String,
    val FullName: String,
    val MobileNumber: String,
    val Status: String
)