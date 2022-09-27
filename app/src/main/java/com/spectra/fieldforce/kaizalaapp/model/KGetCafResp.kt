package com.spectra.fieldforce.kaizalaapp.model

data class KGetCafResp(
    val Response: KCafRes?,
    val Status: String?,
    val StatusCode: Int?
)

data class KCafRes(
    val CAFId: String?,
    val LampCafId: String?,
    val LampLeadId: String?,
    val OTPAuthStatsu: Boolean?,
    val OTPCode: String?,
    val Status: String?,
    val accountDetail: KAccountDetail?,
    val cafDetail: KCafDetail?,
    val customerImage: KCustomerImage?,
    val documentRequired: KDocumentRequired?,
    val paymentDetail: KPaymentDetail?
)

data class KAccountDetail(
    val AccGuid: String?,
    val Area: String?,
    val BlockTower: String?,
    val BusinessSegment: String?,
    val City: String?,
    val CompanyId: String?,
    val Country: String?,
    val EmailId: String?,
    val FirstName: String?,
    val GroupId: String?,
    val HousePlotFlatNumber: String?,
    val LastName: String?,
    val MobileNo: String?,
    val PhoneNo: String?,
    val PostalCode: String?,
    val Product: String?,
    val RelationshipId: String?,
    val SalutationId: String?,
    val Society: String?,
    val State: String?,
    val SubBusinessSegment: String?
)

data class KCafDetail(
    val Area: String?,
    val BusinessSegment: String?,
    val CAFGuid: String?,
    val CafNo: String?,
    val City: String?,
    val CompanyId: String?,
    val Country: String?,
    val CustomerName: String?,
    val EmailId: String?,
    val GSTNo: String?,
    val GroupId: String?,
    val HousePlotFlatNumber: String?,
    val IsGST: String?,
    val MobileNo: String?,
    val PANNo: String?,
    val PhoneNo: String?,
    val PostalCode: String?,
    val Product: String?,
    val RelationshipId: String?,
    val Society: String?,
    val State: String?,
    val SubBusinessSegment: String?,
    val TANNo: String?,
    val Voice: String?
)

data class KDocumentRequired(
    val AadharCard: Boolean?,
    val AadharCardPhotoId: Boolean?,
    val AadharNo: String?,
    val BankPassbook: Boolean?,
    val CentralStateGovtId: Boolean?,
    val CentralStateGovtIdPhotoId: Boolean?,
    val CompanyId: String?,
    val CreditCardStatement: Boolean?,
    val DocGuid: String?,
    val DrivingLicense: Boolean?,
    val DrivingLicensePhotoId: Boolean?,
    val ElectricityBill: Boolean?,
    val ElectricityIssueDate: String?,
    val FirmType: String?,
    val GasConnection: Boolean?,
    val GasConnectionIssueDate: String?,
    val GroupId: String?,
    val PanCardPhotoId: Boolean?,
    val Passport: Boolean?,
    val PassportNo: String?,
    val PassportPhotoId: Boolean?,
    val RationCardPhotoId: Boolean?,
    val RelationshipId: String?,
    val RentAgreement: Boolean?,
    val TelephoneBill: Boolean?,
    val TelephoneIssueDate: String?,
    val VerificationStatus: String?,
    val VoterNo: String?,
    val Votercard: Boolean?,
    val VotercardPhotoId: Boolean?,
    val WaterBill: Boolean?,
    val WaterBillIssueDate: String?,
    val documentData: MutableList<KDocumentData>?,
    val drivingLicenseNo: String?
)

data class KPaymentDetail(
    val Amount: String?,
    val ApprovalCode: String?,
    val BankName: String?,
    val BranchName: String?,
    val ChequeDDDate: String?,
    val ChequeDDNo: String?,
    val CreditCardDigits: String?,
    val DebitCardDigits: String?,
    val OTC: String?,
    val PayGuid: String?,
    val PayInSlip: String?,
    val PaymentDate: String?,
    val PaymentStatus: String?,
    val SecurityDeposit: String?,
    val SecurityDepositType: String?,
    val TransactionId: String?
)

data class KDocumentData(
    val FileContent: String?,
    val FileNameWithExtension: String?
)


data class KCustomerImage(
    val FileContent: String?,
    val FileNameWithExtension: String?
)