package com.spectra.fieldforce.salesapp.model

data class GetWanRes(
    val Response: WanResponse,
    val Status: String,
    val StatusCode: String
)

data class WanResponse(
    val Data: ArrayList<WanData>,
    val Message: String,
    val StatusCode: Int
)

data class WanData(
    val AdditionalIPGatewayCPE: String,
    val AdditionalIPPoolAllocationType: String,
    val AdditionalIPPoolbetSpecCPEExistin: String,
    val AdditionalIPconfiguredSpecCPE: String,
    val AllocationtypebetSpectCPEandExistin: String,
    val GatewayAddressISPCPE: String,
    val IPPoolbetISPCPEExistingL3Device: String,
    val IPconfiguredSpectraCPE: String,
    val SiteId: String,
    val SolutionDeploymentMode: String,
    val WANBBProvider1D: String,
    val WANBBProvider2: String,
    val WANBandwidthinMbps: String,
    val WANCircuitID: String,
    val WANGatewaySpectraCPEwithmask: String,
    val WANIPaddressSpecCPEmask: String,
    val WANType: String,
    val WanIPAllocationtype: String,
    val WanNo: String
)