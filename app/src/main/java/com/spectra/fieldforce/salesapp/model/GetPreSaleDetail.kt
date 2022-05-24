package com.spectra.fieldforce.salesapp.model

data class GetPreSaleDetail(
    val Response: PreSaleResponse,
    val Status: String,
    val StatusCode: String
)

data class PreSaleResponse(
    val Data: ArrayList<PreSaleData>,
    val Message: String,
    val StatusCode: Int
)

data class PreSaleData(
    val AreTheselinksbeingManlinks: String,
    val CompanyName: String,
    val ContactPersonName: String,
    val CurrentOperationalCity: String,
    val EmailId: String,
    val ITsupportcustomer: String,
    val IndOfITSpent: String,
    val IsCusUsiILLServices: String,
    val IsCususingBroServices: String,
    val IsCususinganyRouting: String,
    val IsexistingMPLSbackbone: String,
    val IsthereredundancyMPLS: String,
    val MobileNo: String,
    val NetSecServusedbycustomer: String,
    val NoOfLocations: String,
    val OppName: String,
    val PlsMenNumOfLinks: String,
    val PlsMenNumOfLinks2: String,
    val PresalesTaskID: String,
    val Remarks: String,
    val SafId: String,
    val WhenFirewallUTMlicenseexpire: String,
    val WhenMPLScontractrenewed: String,
    val WherearetheAppHosted: String,
    val CreatedBY:String,
    val Status:String
)