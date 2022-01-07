package com.spectra.fieldforce.salesapp.model

data class GetCompanyRequest(
    val Action: String,
    val Authkey: String,
    val companyId: String,
    val password: String,
    val userName: String
)