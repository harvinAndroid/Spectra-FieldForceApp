package com.spectra.fieldforce.salesapp.model

data class GetLanRes(
    val Response: LanResponse,
    val Status: String,
    val StatusCode: String
)

data class LanResponse(
    val Data: ArrayList<LanData>,
    val Message: String,
    val StatusCode: Int
)

data class LanData(
    val AdditionalIpConfigured: String,
    val AdditionalIpGateway: String,
    val AdditionalIpPoolBetIspandFw: String,
    val AdditionalIpPoolallocation: String,
    val CustomerexistingIppool1: String,
    val CustomerexistingIppool2: String,
    val IpAllBetIspexistingL3: String,
    val IpAllocationRange: String,
    val IpAllocationUser: String,
    val IpPoolBetIspexistingL3: String,
    val IpPoolGateway: String,
    val IpPoolGateway2: String,
    val LanGatewayAddress: String,
    val LanIPAllocationtoUser: String,
    val LanIpAddress: String,
    val LanIpAddress2: String,
    val LanIpPool: String,
    val LanIpPool2: String,
    val LanIpPool3: String,
    val LanIpallocation2: String,
    val LanIpasIspCpe: String,
    val LanNo: String,
    val SiteId: String,
    val SolutionDeploymentMode: String
)