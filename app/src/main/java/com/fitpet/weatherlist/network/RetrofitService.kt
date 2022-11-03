package com.fitpet.weatherlist.network

import com.fitpet.weatherlist.dto.CityInformationDTO
import com.fitpet.weatherlist.dto.WeatherInformationDTO
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitService {

    @GET("data/2.5/forecast")
    fun getWeather(@Query("lat") lat:String, @Query("lon") lon:String , @Query("appid") appid:String , @Query("units") units:String)
    : Call<WeatherInformationDTO>
    @GET("geo/1.0/direct")
    fun getCityInfo(@Query("q") q:String, @Query("limit") limit:String, @Query("appid") appid:String )
    : Call<CityInformationDTO>
}