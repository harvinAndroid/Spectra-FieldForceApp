package com.spectra.fieldforce.salesapp.model

data class GetContactRequest(
    val Action: String?,
    val Authkey: String?,
    val contactID: String?,
    val password: String?,
    val userName: String?
)