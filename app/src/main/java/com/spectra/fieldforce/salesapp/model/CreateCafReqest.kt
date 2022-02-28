package com.spectra.fieldforce.salesapp.model

import android.text.Editable

data class CreateCafReqest(
        val Action: String?,
        val Authkey: String?,
        val authorSigDetails: AuthorSigDetails?,
        val billingAddress: CafBillingAddress?,
        val cafDetail: CafDetail?,
        val companyId: String?,
        val groupId: String?,
        val installationAddress: CafInstallationAddress?,
        val oppurtunityID: String?,
        val password: String?,
        val paymentDetail: PaymentDetail?,
        val relationshipId: String?,
        val userName: String?
)

data class AuthorSigDetails(
    val authEmailID: String?,
    val authFatherORhusbandName: String?,
    val authLandmark: String?,
    val authMobileNumber: String?,
    val authName: String?,
    val authResidentialAddress: String?,
    val authcity: String?,
    val authcountry: String?,
    val authpostalCode: String?,
    val authstate: String?
)


data class CafBillingAddress(
    val billBuildingPlotFlatNumber: String?,
    val billBuildingType: String?,
    val billContactName: String?,
    val billEmailId: String?,
    val billFloor: String?,
    val billLandmark: String?,
    val billLocality: String?,
    val billStreet: String?,
    val billarea: String?,
    val billbuilding: String?,
    val billcity: String?,
    val billcountry: String?,
    val billmobileNo: String?,
    val billpostalCode: String?,
    val billstate: String?
)

data class CafDetail(
        val businesDays: String?,
        val businessSegment: String?,
        val cafNo: String?,
        val compSelfPO: String?,
        val customerCategory: String?,
        val customerName: String?,
        val customerWorkingHours: String?,
        val existSircProvider: String?,
        val fireWalAMCExperyDate: String?,
        val fireWall: String?,
        val gstNo: String?,
        val isGST: String?,
        val networkMonitorAlert: String?,
        val panNo: String?,
        val phoneNo: String?,
        val poLock: String?,
        val poNext: String?,
        val preferCommMode: String?,
        val subBusinessSegment: String?,
        val tanNo: String?,
        val vOIP: String?
)

data class CafInstallationAddress(
        val billType: String?,
        val instalBlock: String?,
        val instalBuildingPlotFlatNumber: String?,
        val instalBuildingType: String?,
        val instalEmailId: String?,
        val instalFloor: String?,
        val instalLandmark: String?,
        val instalLocality: String?,
        val instalMobileNo: String?,
        val instalStreet: String?,
        val instalarea: String?,
        val instalbuilding: String?,
        val instalcity: String?,
        val instalcountry: String?,
        val instalpostalCode: String?,
        val instalstate: String?,
        val product: String?
)

data class PaymentDetail(
        val OTC: String?,
        val amount: String?,
        val approvalCode: String?,
        val bankName: String?,
        val branchName: String?,
        val chequeDDDate: String?,
        val chequeDDNo: String?,
        val creditCardDigits: String?,
        val debitCardDigits: String?,
        val payInSlip: String?,
        val paymentDate: String?,
        val paymentStatus: String?,
        val securityDeposit: String?,
        val securityDepositType: String?,
        val transactionId: String?
)