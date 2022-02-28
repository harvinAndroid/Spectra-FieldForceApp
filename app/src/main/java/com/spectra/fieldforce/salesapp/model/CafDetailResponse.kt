package com.spectra.fieldforce.salesapp.model

data class CafDetailResponse(
    val Response: CaffResponse?,
    val Status: String?,
    val StatusCode: String?
)

data class CaffResponse(
    val Data: CaffData?,
    val Message: String?,
    val StatusCode: Int?
)

data class CaffData(
    val AttachDocument: String?,
    val BillCycle: Any?,
    val BusinessDays: String?,
    val BusinessSegment: String?,
    val CafNo: String?,
    val Company: String?,
    val CompanyName: String?,
    val CustomerName: String?,
    val CustomerWorkingHours: String?,
    val Group: String?,
    val GstNumber: String?,
    val GstNumberDetial: String?,
    val IsCustomerHappy: String?,
    val LastMileType: String?,
    val Nationality: String?,
    val NetworkTechnology: String?,
    val NtwMonitAlert: String?,
    val OpportunityId: String?,
    val OpportunityId2: String?,
    val PanNo: String?,
    val PayInSlip: String?,
    val PreferredCommMode: String?,
    val Product: String?,
    val ProductId:String?,
    val ProductSegment: String?,
    val Relationship: String?,
    val ServiceId: String?,
    val Status: String?,
    val SubBussinessSegment: String?,
    val TanNo: String?,
    val SubmitFlag:String?,
    val authSigDetails: AuthSigDetails?,
    val billingAddress: BillingAddress?,
    val companyDetail: CafCompanyDetail?,
    val installationAddresses: CafInstallationAddresses?,
    val otherinformations: Otherinformations?,
    val payments: Payments?
)

data class AuthSigDetails(
    val Auth_City: String?,
    val Auth_Country: String?,
    val Auth_EmailId: String?,
    val Auth_Father: String?,
    val Auth_MobileNo: String?,
    val Auth_Name: String?,
    val Auth_Nationality: String?,
    val Auth_PinCode: String?,
    val Auth_Resadd: String?,
    val Auth_State: String?
)

data class BillingAddress(
    val Bill_Area: String,
    val Bill_BuildingName: String,
    val Bill_BuildingNo: String,
    val Bill_City: String,
    val Bill_ConatctName: String,
    val Bill_Country: String,
    val Bill_EmailId: String,
    val Bill_Floor: String,
    val Bill_MobileNo: String,
    val Bill_PinCode: String,
    val Bill_Sameaddress: String,
    val Bill_State: String
)

data class CafCompanyDetail(
    val Company_Name: String,
    val FirmType: String,
    val IndustryType: String
)

data class CafInstallationAddresses(
    val Inst_Area: String,
    val Inst_BillType: String,
    val Inst_BuildingAddress: String,
    val Inst_BuildingCode: String,
    val Inst_BuildingName: String,
    val Inst_BuildingNo: String,
    val Inst_BuildingStatus: String,
    val Inst_CategoryofCustomer: String,
    val Inst_City: String,
    val Inst_Country: String,
    val Inst_EmailId: String,
    val Inst_InstallationFloor: String,
    val Inst_LeadId: String,
    val Inst_LeadName: String,
    val Inst_MobileNo: String,
    val Inst_PinCode: String,
    val Inst_RedunancyRequired: String,
    val Inst_State: String,
    val Inst_VoidPort: String
)

data class Otherinformations(
    val CompanySelfPo: String,
    val ExistingServiceProvider: String,
    val FeasibilityType: String,
    val FireWall: String,
    val PoLock: String,
    val PoNext: String,
    val UptimeSLA: String,
    val FirewallAMC:String
)

data class Payments(
    val Account: String,
    val ApprovalCode: String,
    val BankName: String,
    val Branch: String,
    val CafId: String,
    val ChequeDDDate: String,
    val ChequeDDNo: String,
    val CreditCard4Digit: String,
    val DebitCard4Digit: String,
    val DocumentId: String,
    val Otc: String,
    val PayInSlip: String,
    val PaymentDate: String,
    val PaymentId: String,
    val PaymentStatus: String,
    val Reason: String,
    val TotalAmount: String,
    val TransactionReferenceId: String,
    val SecurityDepositType:String,
    val SecurityDeposit:String
)