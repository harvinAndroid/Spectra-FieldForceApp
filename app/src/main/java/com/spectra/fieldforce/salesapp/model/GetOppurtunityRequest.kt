package com.spectra.fieldforce.salesapp.model

data class GetOppurtunityRequest(
        val Action: String?,
        val Authkey: String?,
        val OppId: String?,
        val password: String?,
        val userName: String?,
        val Leadid: String?
)