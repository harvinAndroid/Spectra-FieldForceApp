package com.spectra.fieldforce.salesapp.model

data class CreateSafReq(
    val Action: String?,
    val Authkey: String?,
    val AuthSigDetailsSAF: SAFAuthSigDetails?,
    val BillingAddressSAF: SAFBillingAddress?,
    val CompanyDetailsSAF: SAFCompanyDetails?,
    val InstallationAddressesSAF: SAFInstallationAddresses?,
    val PaymentsSAF: SAFPayments?,
    val businessSegment: String?,
    val company: String?,
    val companyName: String?,
    val contactPersonName: String?,
    val customerWorkingHours: String?,
    val group: String?,
    val gstNumber: String?,
    val gstNumberDetial: String?,
    val oppId: String?,
    val opportunityId: String?,
    val panNo: String?,
    val password: String?,
    val productSegment: String?,
    val serviceId: String?,
    val subBussinessSegment: String?,
    val tanNo: String?,
    val userName: String?,
    val vertical: String?,
    val safNo:String?
)

data class SAFAuthSigDetails(
    val auth_city: String?,
    val auth_country: String?,
    val auth_emailId: String?,
    val auth_father: String?,
    val auth_mobileNo: String?,
    val auth_name: String?,
    val auth_phoneNo: String?,
    val auth_pinCode: String?,
    val auth_state: String?
)

data class SAFBillingAddress(
    val bill_area: String?,
    val bill_buildingName: String?,
    val bill_city: String?,
    val bill_conatctName: String?,
    val bill_country: String?,
    val bill_emailId: String?,
    val bill_floor: String?,
    val bill_phoneNo: String?,
    val bill_pinCode: String?,
    val bill_sameaddress: String?,
    val bill_specifyArea: String?,
    val bill_specifyBuilding: String?,
    val bill_state: String?
)

data class SAFCompanyDetails(
    val company_Name: String?,
    val firmType: String?,
    val industryType: String?
)

data class SAFInstallationAddresses(
    val inst_area: String?,
    val inst_billType: String?,
    val inst_buildingName: String?,
    val inst_buildingNo: String?,
    val inst_city: String?,
    val inst_country: String?,
    val inst_emailId: String?,
    val inst_installationBlock: String?,
    val inst_installationFloor: String?,
    val inst_leadId: String?,
    val inst_leadname: String?,
    val inst_mobileNo: String?,
    val inst_pinCode: String?,
    val inst_specifyArea: String?,
    val inst_specifyBuilding: String?,
    val inst_state: String?
)

data class SAFPayments(
    val approvalCode: String?,
    val bankName: String?,
    val branch: String?,
    val chequeDDDate: String?,
    val chequeDDNo: String?,
    val creditCard4Digit: String?,
    val debitCard4Digit: String?,
    val otc: String?,
    val paymentDate: String?,
    val paymentSlip: String?,
    val reason: String?,
    val securityDeposit: String?,
    val securityDepositType: String?,
    val totalAmount: String?,
    val transactionReferenceId: String?,
    val paymentId:String?
)