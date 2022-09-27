package com.spectra.fieldforce.kaizalaapp.model



data class KCreateLeadReq(
    val Action: String?,
    val Authkey: String?,
    val leadId:String,
    val businessSegment: String?,
    val companyID: String?,
    val contactPersonName: String?,
    val customersegment: String?,
    val emailId: String?,
    val ems: String?,
    val firstName: String?,
    val groupID: String?,
    val isSameAddress: String?,
    val lastName: String?,
    val leadChannel: String?,
    val leadSource: String?,
    val leadTopic: String?,
    val mobileNo: String?,
    val password: String?,
    val productName: String?,
    val relationshipId: String?,
    val remark: String?,
    val salutationId: String?,
    val specifyleadsource: String?,
    val subBusinessSegment: String?,
    val userName: String?,
    val verticalID: String?,
    val CompanyDetail: KCompanyDetail?,
    val ContactAddress: KContactAddress?,
    val InstallationAddress: KInstallationAddress?,
    val SDWAN: KSDWAN?,
    val otherDetail: KOtherDetail?,
)

data class KCompanyDetail(
    val companyName: String?,
    val firmType: String?,
    val industryType: String?,
    val jobTitle: String?
)

data class KContactAddress(
    val contactArea: String?,
    val contactBuilding: String?,
    val contactCity: String?,
    val contactCountry: String?,
    val contactFloor: String?,
    val contactPincode: String?,
    val contactPlotNo: String?,
    val contactSpecifyArea: String?,
    val contactSpecifyBuilding: String?,
    val contactState: String?,
    val contactblock: String?
)

data class KInstallationAddress(
    val block: String?,
    val installArea: String?,
    val installBuilding: String?,
    val installCity: String?,
    val installCountry: String?,
    val installFloor: String?,
    val installPincode: String?,
    val installPlotNo: String?,
    val installSociety: String?,
    val installSpecifyArea: String?,
    val installSpecifyBuilding: String?,
    val installState: String?
)

data class KSDWAN(
    val applicationsHosted: String?,
    val currentOperationalCity: String?,
    val customerBroadbandServices: String?,
    val customerILLservices: String?,
    val customerRoutingServices: String?,
    val firewallSetToExpire: String?,
    val indicationITSpent: String?,
    val itSupport: String?,
    val linksManagedLinks: String?,
    val mPLSBackbone: String?,
    val mPLSContract: String?,
    val networkSecurityServices: String?,
    val noOfLocation: String?,
    val numberOfLinksBroadbandServices: String?,
    val numberOfLinksIll: String?,
    val redundancyMPLS: String?,
    val sdwanRemarks: String?
)

data class KOtherDetail(
    val currentWorkingLocation: String?,
    val existingServiceProvider1: String?,
    val existingServiceProvider2: String?,
    val isDatacenter: Boolean?,
    val isFirewall: Boolean?,
    val isManagesWiFi: Boolean?,
    val isVOIP: Boolean?,
    val isVPN: Boolean?,
    val media: String?,
    val serviceFromServiceProvider1: String?,
    val serviceFromServiceProvider2: String?,
    val targetInstallationPeriod: String?
)