package com.spectra.fieldforce.salesapp.model

data class CreateWanReq(
    val Action: String?,
    val Authkey: String?,
    val additionalIPGatewayCPE: String?,
    val additionalIPPoolAllocationType: String?,
    val additionalIPPoolbetSpecCPEExistin: String?,
    val additionalIPconfiguredSpecCPE: String?,
    val allocationtypebetSpectCPEandExistin: String?,
    val gatewayAddressISPCPE: String?,
    val iPPoolbetISPCPEExistingL3Device: String?,
    val iPconfiguredSpectraCPE: String?,
    val password: String?,
    val siteId: String?,
    val userName: String?,
    val wANBBProvider1D: String?,
    val wANBBProvider2: String?,
    val wANBandwidthinMbps: String?,
    val wANCircuitID: String?,
    val wANGatewaySpectraCPEwithmask: String?,
    val wANIPaddressSpecCPEmask: String?,
    val wANType: String?,
    val wanIPAllocationtype: String?,
    val wanNo:String?
)