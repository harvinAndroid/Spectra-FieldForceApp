package com.spectra.fieldforce.kaizalaapp.model

data class KGetLeadRes(
    val Response: MutableList<KleadResponse>?,
    val Status: String?,
    val StatusCode: Int?
)

data class KleadResponse(
    val BusinessSegment: String?,
    val CAFId: String?,
    val CompanyId: String?,
    val ContactPersonName: String?,
    val CreationDate: String?,
    val EMS: String?,
    val EmailId: String?,
    val FirstName: String?,
    val GroupId: String?,
    val LastName: String?,
    val LeadChannel: String?,
    val LeadId: String?,
    val LeadSource: String?,
    val MobileNo: String?,
    val ProductName: String?,
    val RelationshipId: String?,
    val Remark: String?,
    val SalutationId: String?,
    val SubBusinessSegment: String?,
    val companyDetail: CompanyDetail?,
    val contactAddress: ContactAddress?,
    val installationAddress: InstallationAddress?,
    val otherDetail: OtherDetail?,
    val companyName:String?,
    val GroupName:String?
)

data class CompanyDetail(
    val CompanyName: String?,
    val FirmType: Int?,
    val IndustryType: String?,
    val JobTitle: String?
)

data class ContactAddress(
    val ContactArea: String?,
    val ContactAreaName: String?,
    val ContactBuilding: String?,
    val ContactBuildingName: String?,
    val ContactCity: String?,
    val ContactCityName: String?,
    val ContactCountry: String?,
    val ContactCountryName: String?,
    val ContactFloor: String?,
    val ContactPincode: String?,
    val ContactPlotNo: String?,
    val ContactState: String?,
    val ContactStateName: String?
)

data class InstallationAddress(
    val InstallArea: String?,
    val InstallAreaName: String?,
    val InstallBuilding: String?,
    val InstallBuildingName: String?,
    val InstallCity: String?,
    val InstallCityName: String?,
    val InstallCountry: String?,
    val InstallCountryName: String?,
    val InstallFloor: String?,
    val InstallPincode: String?,
    val InstallPlotNo: String?,
    val InstallSociety: String?,
    val InstallSocietyName: String?,
    val InstallState: String?,
    val InstallStateName: String?
)

data class OtherDetail(
    val CurrentWorkingLocation: String?,
    val ExistingServiceProvider1: Int?,
    val ExistingServiceProvider2: Int?,
    val IsDatacenter: Boolean?,
    val IsFirewall: Boolean?,
    val IsManagesWiFi: Boolean?,
    val IsVOIP: Boolean?,
    val IsVPN: Boolean?,
    val Media: Int?,
    val ServiceFromServiceProvider1: Int?,
    val ServiceFromServiceProvider2: Int?,
    val TargetInstallationPeriod: String?
)