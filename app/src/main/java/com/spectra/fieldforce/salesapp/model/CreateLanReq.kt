package com.spectra.fieldforce.salesapp.model




data class CreateLanReq(
    val Action: String?,
    val Authkey: String?,
    private val lanIPAllocationtoUser: String?,
    private val ipAllBetIspexistingL3: String?,
    private val additionalIpPoolallocation: String?,
    private val ipPoolBetIspexistingL3: String?,
    private val lanIpasIspCpe: String?,
    private val lanGatewayAddress: String?,
    private val additionalIpConfigured: String?,
    private val ipAllocationRange: String?,
    private val lanIpPool: String?,

//private string owner;
    private val lanIpAddress: String?,
    private val ipPoolGateway: String?,

//end left
//start right
    private val lanIpallocation2: String?,
    private val lanIpPool2: String?,
    private val additionalIpGateway: String?,
    private val lanIpPool3: String?,
    private val lanIpAddress2: String?,
    private val ipPoolGateway2: String?,
    private val ipAllocationUser: String?,
    private val customerexistingIppool1: String?,
    private val customerexistingIppool2: String?,
    private val additionalIpPoolBetIspandFw: String?,
    val password: String?,
    val siteId: String?,
    val userName: String?,
    val lanNo:String?
   /* val Action: String?,
    val Authkey: String?,
    val additionalIpConfigured: String?,
    val additionalIpGateway: String?,
    val additionalIpPoolBetIspandFw: String?,
    val additionalIpPoolallocation: String?,
    val customerexistingIppool1: String?,
    val customerexistingIppool2: String?,
    val ipAllBetIspexistingL3: String?,
    val ipAllocationRange: String?,
    val ipAllocationUser: String?,
    val ipPoolBetIspexistingL3: String?,
    val ipPoolGateway: String?,
    val ipPoolGateway2: String?,
    val lanGatewayAddress: String?,
    val lanIPAllocationtoUser: String?,
    val lanIpAddress: String?,
    val lanIpAddress2: String?,
    val lanIpPool: String?,
    val lanIpPool2: String?,
    val lanIpPool3: String?,
    val lanIpallocation2: String?,
    val lanIpasIspCpe: String?,
    val password: String?,
    val siteId: String?,
    val userName: String?,
    val lanNo:String?*/
)

