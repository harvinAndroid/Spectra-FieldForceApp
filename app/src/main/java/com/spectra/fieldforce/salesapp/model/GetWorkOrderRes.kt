package com.spectra.fieldforce.salesapp.model

data class GetWorkOrderRes(
    val Response: WorkResponse,
    val Status: String,
    val StatusCode: String
)

data class WorkResponse(
    val Data: ArrayList<WorkData>,
    val Message: String,
    val StatusCode: Int
)

data class WorkData(
    val AcceptedByCustomerDate: String,
    val City: String,
    val ContactPerson: String,
    val CreatedOn: String,
    val Opportunity: String,
    val OrderStatus: String,
    val Owner: String,
    val ProductAttached: String,
    val SafNo: String,
    val SiteId: String,
    val SiteType: String,
    val SolutionDeploymentMode: String,
    val State: String,
    val StateserviceActivation: String,
    val WorkOrderID: String
)