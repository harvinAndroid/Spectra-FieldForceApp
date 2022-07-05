package com.spectra.fieldforce.salesapp.model

data class FFASRBarChartResponse(
    val Response: ChartResponse,
    val Status: String,
    val StatusCode: String
)

data class ChartResponse(
    val Data: ArrayList<ChartData>,
    val Message: String,
    val StatusCode: Int
)

data class ChartData(
    val BusinessSegment: String,
    val CaseID: String,
    val CaseIDCRM: String,
    val CaseStatus: String,
    val CreatedOn: String,
    val CrmStatus: String,
    val Customer: String,
    val SubSubType: String,
    val SubType: String,
    val Title: String,
    val Type: String
)