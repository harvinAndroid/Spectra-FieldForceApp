package com.spectra.fieldforce.kaizalaapp.model

data class KGetRelationReq(
    val Action: String,
    val Authkey: String,
    val CompanyId: String,
    val password: String,
    val userName: String
)