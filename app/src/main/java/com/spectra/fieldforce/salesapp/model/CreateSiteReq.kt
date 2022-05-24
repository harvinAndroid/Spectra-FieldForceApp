package com.spectra.fieldforce.salesapp.model

data class CreateSiteReq(
    val Action: String?,
    val Authkey: String?,
    val address: String?,
    val city: String?,
    val contactPersonName: String?,
    val customerContactNumber: String?,
    val customerEmailAddress: String?,
    val customerEmergencyContactNumber: String?,
    val noOfLanPool: String?,
    val noOfWanlinks: String?,
    val opportunityId: String?,
    val password: String?,
    val pinCode: String?,
    val priceList: String?,
    val relationship: String?,
    val siteCategory: String?,
    val solutionDeploymentMode: String?,
    val state: String?,
    val userName: String?,
    val siteID:String?
)