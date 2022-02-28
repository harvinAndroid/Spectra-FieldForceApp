package com.spectra.fieldforce.salesapp.model

data class GetRFSResponse(
    val Response: RFSResponse,
    val Status: String,
    val StatusCode: String
)

data class RFSResponse(
    val Data: ArrayList<RFSData>,
    val Message: String,
    val StatusCode: Int
)

data class RFSData(
    val BackenedConfigDone: String,
    val CafNo: String,
    val ConsumptionStatus: String,
    val CustomerIpPool: String,
    val FailoverTested: String,
    val Gateway: String,
    val GatewayCity: String,
    val NagiosHostName: String,
    val NagiosIp: String,
    val Name: String,
    val Otp: String,
    val Owner: String,
    val RfsDate: String,
    val RfsTaskStatus: String,
    val ServiceId: String,
    val SpeedTest: String,
    val DownTime:String
)