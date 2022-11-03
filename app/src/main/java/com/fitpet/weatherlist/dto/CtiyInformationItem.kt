package com.fitpet.weatherlist.dto

data class CtiyInformationItem(
    val country: String,
    val lat: Double,
    val local_names: LocalNames,
    val lon: Double,
    val name: String,
    val state: String
)