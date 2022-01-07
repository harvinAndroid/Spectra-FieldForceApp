package com.spectra.fieldforce.salesapp.model

data class UpdateLeadRequest(
    val Action: String,
    val Authkey: String,
    val CompanyDetail: CompanyDetaill,
    val ContactAddress: ContactAddresss,
    val InstallationAddress: InstallationAddresss,
    val OtherDetail: OtherDetaill,
    val businessSegment: String,
    val cafId: String,
    val contactPersonName: String,
    val creationDate: String,
    val emailId: String,
    val ems: String,
    val firstName: String,
    val lastName: String,
    val leadChannel: String,
    val leadId: String,
    val leadSource: String,
    val mobileNo: String,
    val productName: String,
    val remark: String,
    val salutationId: String,
    val subBusinessSegment: String
)

data class CompanyDetaill(
    val companyName: String,
    val firmType: String,
    val industryType: String,
    val jobTitle: String
)

data class ContactAddresss(
    val contactArea: String,
    val contactAreaName: String,
    val contactBuilding: String,
    val contactBuildingName: String,
    val contactCity: String,
    val contactCityName: String,
    val contactCountry: String,
    val contactCountryName: String,
    val contactFloor: String,
    val contactPincode: String,
    val contactPlotNo: String,
    val contactState: String,
    val contactStateName: String
)

data class InstallationAddresss(
    val installArea: String,
    val installAreaName: String,
    val installBuilding: String,
    val installBuildingName: String,
    val installCity: String,
    val installCityName: String,
    val installCountry: String,
    val installCountryName: String,
    val installFloor: String,
    val installPincode: String,
    val installPlotNo: String,
    val installSociety: String,
    val installSocietyName: String,
    val installState: String,
    val installStateName: String
)

data class OtherDetaill(
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