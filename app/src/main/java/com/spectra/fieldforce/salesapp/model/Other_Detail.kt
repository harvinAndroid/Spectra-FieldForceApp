package com.spectra.fieldforce.salesapp.model


data class Other_Detail(
        val CurrentWorkingLocation: String,
        val ExistingServiceProvider1: String,
        val ExistingServiceProvider2: String,
        val IsDatacenter: Boolean,
        val IsFirewall: Boolean,
        val IsManagesWiFi: Boolean,
        val IsVOIP: Boolean,
        val IsVPN: Boolean,
        val Media: String,
        val ServiceFromServiceProvider1: String,
        val ServiceFromServiceProvider2: String,
        val TargetInstallationPeriod: String
)