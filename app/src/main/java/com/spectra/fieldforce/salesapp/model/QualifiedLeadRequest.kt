package com.spectra.fieldforce.salesapp.model

data class QualifiedLeadRequest(
    val Action: String,
    val Authkey: String,
    val leadId: String,
    val password: String,
    val userName: String
)