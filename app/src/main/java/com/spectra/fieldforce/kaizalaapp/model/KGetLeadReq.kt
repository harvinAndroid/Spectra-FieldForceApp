package com.spectra.fieldforce.kaizalaapp.model

data class KGetLeadReq(
    val Action: String,
    val Authkey: String,
    val LeadId: String,
    val password: String,
    val userName: String
)
