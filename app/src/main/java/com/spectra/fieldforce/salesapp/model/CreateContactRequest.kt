package com.spectra.fieldforce.salesapp.model

data class CreateContactRequest(
    val Action: String?,
    val Authkey: String?,
    val area: String?,
    val building: String?,
    val callAttempted: String?,
    val channel: String?,
    val city: String?,
    val competitorName: String?,
    val disposition: String?,
    val emailAddress: String?,
    val firstName: String?,
    val followupDate: String?,
    val lastName: String?,
    val mobileNumber: String?,
    val mobileNumber2: String?,
    val password: String?,
    val planCategory: String?,
    val remark: String?,
    val source: String?,
    val specifyArea: String?,
    val specifybuilding: String?,
    val state: String?,
    val statusReason: String?,
    val userName: String?,
    val businessSegment:String?,
    val dncNumber:String?,
    val campaignName:String?,
    val contactId:String?

)