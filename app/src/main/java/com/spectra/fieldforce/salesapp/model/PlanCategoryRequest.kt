package com.spectra.fieldforce.salesapp.model

data class PlanCategoryRequest(
    val Action: String,
    val Authkey: String,
    val bussSegment: String,
    val password: String?,
    val userName: String?
)