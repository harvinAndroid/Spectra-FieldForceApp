package com.spectra.fieldforce.salesapp.model

data class CreateLeadRequest(
    val Action: String,
    val Authkey: String,
    val leadId:String,
    val CompanyDetail: CompanyDetail,
    val ContactAddress: ContactAddress,
    val InstallationAddress: InstallationAddress,
    val businessSegment: String,
    val companyID: String,
    val contactPersonName: String,
    val customersegment: String,
    val emailId: String,
    val ems: String,
    val firstName: String,
    val groupID: String,
    val lastName: String,
    val leadChannel: String,
    val leadSource: String,
    val leadTopic: String,
    val mobileNo: String,
    val otherDetail: OtherDetail,
    val password: String,
    val productName: String,
    val relationshipId: String,
    val remark: String,
    val salutationId: String,
    val specifyleadsource: String,
    val subBusinessSegment: String,
    val userName: String
)

data class CompanyDetail(
    val companyName: String,
    val firmType: String,
    val industryType: String,
    val jobTitle: String
)

data class ContactAddress(
        val contactArea: String?,
        val contactBuilding: String?,
        val contactCity: String?,
        val contactCountry: String,
        val contactFloor: String,
        val contactPincode: String,
        val contactPlotNo: String,
        val contactSpecifyArea: String?,
        val contactSpecifyBuilding: String,
        val contactState: String,
        val contactblock: String
)

data class InstallationAddress(
    val block: String,
    val installArea: String,
    val installBuilding: String,
    val installCity: String,
    val installCountry: String,
    val installFloor: String,
    val installPincode: String,
    val installPlotNo: String,
    val installSociety: String,
    val installSpecifyArea: String,
    val installSpecifyBuilding: String,
    val installState: String
)

data class OtherDetail(
    val currentWorkingLocation: String,
    val existingServiceProvider1: String,
    val existingServiceProvider2: String,
    val isDatacenter: Boolean,
    val isFirewall: Boolean,
    val isManagesWiFi: Boolean,
    val isVOIP: Boolean,
    val isVPN: Boolean,
    val media: String,
    val serviceFromServiceProvider1: String,
    val serviceFromServiceProvider2: String,
    val targetInstallationPeriod: String
)