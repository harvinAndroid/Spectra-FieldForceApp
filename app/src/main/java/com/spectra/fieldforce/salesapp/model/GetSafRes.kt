package com.spectra.fieldforce.salesapp.model

data class GetSafRes(
    val Response: SafResponsee,
    val Status: String,
    val StatusCode: String
)

data class SafResponsee(
    val Data: ArrayList<SafDataa>,
    val Message: String,
    val StatusCode: Int
)

data class SafDataa(
    val AttachDocument: String,
    val BusinessSegment: String,
    val Company: String,
    val CompanyName: String,
    val ContactPersonName: String,
    val CustomerWorkingHours: String,
    val Group: String,
    val GstNumber: String,
    val GstNumberDetial: String,
    val OpportunityId: String,
    val PanNo: String,
    val PayInSlip: String,
    val ProductSegment: String,
    val SafNo: String,
    val ServiceId: String,
    val Status: String,
    val SubBussinessSegment: String,
    val SubmitFlag: Any,
    val TanNo: String,
    val Vertical: String,
    val Owner:String,
    val authSigDetailsSAF: AuthSigDetailsSAF,
    val billingAddressSAF: BillingAddressSAF,
    val companyDetailSAF: CompanyDetailSAF,
    val installationAddressesSAF: InstallationAddressesSAF,
    val paymentsSAF: PaymentsSAF
)

data class AuthSigDetailsSAF(
    val Auth_City: String,
    val Auth_Country: String,
    val Auth_EmailId: String,
    val Auth_Father: String,
    val Auth_MobileNo: String,
    val Auth_Name: String,
    val Auth_PhoneNo: String,
    val Auth_PinCode: String,
    val Auth_State: String
)

data class BillingAddressSAF(
    val Bill_Area: String,
    val Bill_BuildingName: String,
    val Bill_City: String,
    val Bill_ConatctName: String,
    val Bill_Country: String,
    val Bill_EmailId: String,
    val Bill_Floor: String,
    val Bill_PhoneNo: String,
    val Bill_PinCode: String,
    val Bill_Sameaddress: String,
    val Bill_SpecifyArea: String,
    val Bill_SpecifyBuilding: String,
    val Bill_State: String,
    val Bill_BuildingNo:String
)

data class CompanyDetailSAF(
    val Company_Name: String,
    val FirmType: String,
    val IndustryType: String
)

data class InstallationAddressesSAF(
    val Inst_Area: String,
    val Inst_BillType: String,
    val Inst_BuildingName: String,
    val Inst_BuildingNo: String,
    val Inst_City: String,
    val Inst_Country: String,
    val Inst_EmailId: String,
    val Inst_InstallationBlock: String,
    val Inst_InstallationFloor: String,
    val Inst_LeadId: String,
    val Inst_LeadName: String,
    val Inst_MobileNo: String,
    val Inst_PinCode: String,
    val Inst_SpecifyArea: String,
    val Inst_SpecifyBuilding: String,
    val Inst_State: String
)

data class PaymentsSAF(
    val Account: String,
    val ApprovalCode: String,
    val BankName: String,
    val Branch: String,
    val ChequeDDDate: String,
    val ChequeDDNo: String,
    val CreditCard4Digit: String,
    val DebitCard4Digit: String,
    val DocumentId: String,
    val Otc: String,
    val PaymentDate: String,
    val PaymentId: String,
    val PaymentSlip: String,
    val PaymentStatus: String,
    val Reason: String,
    val SafId: String,
    val SecurityDeposit: String,
    val SecurityDepositType: String,
    val TotalAmount: String,
    val TransactionReferenceId: String
)