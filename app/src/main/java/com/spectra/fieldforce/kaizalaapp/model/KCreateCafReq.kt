package com.spectra.fieldforce.kaizalaapp.model

data class KCreateCafReq(
    val AccountDetail: AccountDetail?,
    val Action: String?,
    val Authkey: String?,
    val PaymentDetail: PaymentDetail?,
    val cafdetail: Cafdetail?,
    val customerImage: CustomerImage?,
    val documentRequired: DocumentRequired?,
    val lampCafId: String?,
    val lampLeadId: String?,
    val password: String?,
    val userName: String?
)

data class AccountDetail(
    val CompanyId: String?,
    val GroupId: String?,
    val RelationshipId: String?,
    val area: String?,
    val blockTower: String?,
    val businessSegment: String?,
    val city: String?,
    val country: String?,
    val emailId: String?,
    val firstName: String?,
    val housePlotFlatNumber: String?,
    val lastName: String?,
    val mobileNo: String?,
    val phoneNo: String?,
    val postalCode: String?,
    val product: String?,
    val salutationId: String?,
    val society: String?,
    val state: String?,
    val subBusinessSegment: String?
)

data class PaymentDetail(
    val amount: String?,
    val approvalCode: String?,
    val bankName: String?,
    val branchName: String?,
    val chequeDDDate: String?,
    val chequeDDNo: String?,
    val creditCardDigits: String?,
    val debitCardDigits: String?,
    val otc: String?,
    val payInSlip: String?,
    val paymentDate: String?,
    val paymentStatus: String?,
    val securityDeposit: String?,
    val securityDepositType: String?,
    val transactionId: String?
)

data class Cafdetail(
    val cafNo:String?,
    val CompanyId: String?,
    val GroupId: String?,
    val PANNo: String?,
    val RelationshipId: String?,
    val TANNo: String?,
    val area: String?,
    val businessSegment: String?,
    val city: String?,
    val country: String?,
    val customerName: String?,
    val emailId: String?,
    val gSTNo: String?,
    val housePlotFlatNumber: String?,
    val isGST: String?,
    val mobileNo: String?,
    val phoneNo: String?,
    val postalCode: String?,
    val product: String?,
    val society: String?,
    val state: String?,
    val subBusinessSegment: String?,
    val voice: String?
)

data class CustomerImage(
    val fileContent: String?,
    val fileNameWithExtension: String?
)

data class DocumentRequired(
    val AadharNo: String?,
    val CompanyId: String?,
    val FirmType: String?,
    val GroupId: String?,
    val PassportNo: String?,
    val RelationshipId: String?,
    val VoterNo: String?,
    val aadharCard: Boolean?,
    val aadharCardPhotoId: Boolean?,
    val bankPassbook: Boolean?,
    val centralStateGovtId: Boolean?,
    val centralStateGovtIdPhotoId: Boolean?,
    val creditCardStatement: Boolean?,
    val documentData: List<DocumentData>?,
    val drivingLicense: Boolean?,
    val drivingLicenseNo: String?,
    val drivingLicensePhotoId: Boolean?,
    val electricityBill: Boolean?,
    val electricityIssueDate: String?,
    val gasConnection: Boolean?,
    val gasConnectionIssueDate: String?,
    val panCardPhotoId: Boolean?,
    val passport: Boolean?,
    val passportPhotoId: Boolean?,
    val rationCardPhotoId: Boolean?,
    val rentAgreement: Boolean?,
    val telephoneBill: Boolean?,
    val telephoneIssueDate: String?,
    val votercard: Boolean?,
    val votercardPhotoId: Boolean?,
    val waterBill: Boolean?,
    val waterBillIssueDate: String?
)


data class DocumentData(
    val fileContent: String?,
    val fileNameWithExtension: String?
)