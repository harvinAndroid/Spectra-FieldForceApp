package com.spectra.fieldforce.salesapp.model

data class DisqualifyLead(
    val Action: String,
    val Authkey: String,
    val leadId: String,
    val password: String?,
    val status: String,
    val userName: String?
)