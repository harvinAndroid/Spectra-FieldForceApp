package com.spectra.fieldforce.salesapp.model

class GetPlanCategoryRes(
    val Response: PlanCategoryResponse,
    val Status: String,
    val StatusCode: String
)

data class PlanCategoryResponse(
    val Data: ArrayList<PlanCategoryData>,
    val Message: String,
    val StatusCode: Int
)

data class PlanCategoryData(
    val CategoryName: String
)