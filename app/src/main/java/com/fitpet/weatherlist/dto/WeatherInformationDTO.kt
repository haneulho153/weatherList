package com.fitpet.weatherlist.dto

data class WeatherInformationDTO(
    val city: City,
    val cnt: Int,
    val cod: String,
    val list: List<WeatherInformationItem>,
    val message: Int
)