package com.spectra.fieldforce.kaizalaapp.model

data class KGetPlanCatRes(
    val Response: MutableList<PlanData>,
    val Status: String,
    val StatusCode: String
)

data class PlanData(
    val CategoryName: String
)