package com.spectra.fieldforce.salesapp.model

data class UpdateFlrRequest(
    val Action: String,
    val Authkey: String,
    val leadId: String,
    val mobilephoneflr: String,
    val statusflr: String,
    val estimatedclosureflr: String?,
    val remarkflr: String,
    val appointmentflr: String,
    val prefereddatetimeflr: String?,
    val userName: String,
    val password: String,
)