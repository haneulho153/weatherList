package com.fitpet.weatherlist.ui

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fitpet.weatherlist.dto.CityInformationDTO
import com.fitpet.weatherlist.dto.CityWeatherListVO
import com.fitpet.weatherlist.dto.CityWeatherVO
import com.fitpet.weatherlist.dto.WeatherInformationDTO
import com.fitpet.weatherlist.network.RetrofitClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainViewModel : ViewModel() {
    var cityWeatherListVO  = MutableLiveData<CityWeatherListVO>()


    /**
     * 좌표 정보 조회
     */
    fun loadCoordi(context : Context , city : String){
        viewModelScope.launch(Dispatchers.IO) {
        RetrofitClient.getInstance(context).retrofitService
            ?.getCityInfo(city,"1","ea8a046f1a0aa7a5dea331b05ad5a7d2")
            ?.enqueue(object : Callback<CityInformationDTO>{
                override fun onResponse(call: Call<CityInformationDTO>, response: Response<CityInformationDTO>) {
                    var result : CityInformationDTO? = response.body()

                    result?.let {
                        //load 날씨
                        loadWeather(context ,result)
                    }
                }

                override fun onFailure(call: Call<CityInformationDTO>, t: Throwable) {
                    Log.d("YMC", "onFailure 에러: " + t.message.toString());
                }
            })
        }
    }
    /**
     * 날씨 정보 조회
     */
    fun loadWeather(context : Context , cityImfomationDTO: CityInformationDTO){
        viewModelScope.launch(Dispatchers.IO) {

            cityImfomationDTO?.let{
                val lat = cityImfomationDTO.get(0).lat.toString()
                val lon = cityImfomationDTO.get(0).lon.toString()
                RetrofitClient.getInstance(context).retrofitService
                    ?.getWeather(lat,lon,"ea8a046f1a0aa7a5dea331b05ad5a7d2","metric")
                    ?.enqueue(object : Callback<WeatherInformationDTO>{
                        override fun onResponse(call: Call<WeatherInformationDTO>, response: Response<WeatherInformationDTO>) {

                            var result : WeatherInformationDTO? = response.body()

                            var city =  cityImfomationDTO.get(0).name
                            result?.let {
                                when(city){
                                    "Seoul"->{
                                        var tempCityWeatherVO = CityWeatherVO(cityImfomationDTO,result)
                                        var tempCityWeatherListVO = CityWeatherListVO(
                                            tempCityWeatherVO
                                            ,cityWeatherListVO.value?.London
                                            ,cityWeatherListVO.value?.Chicago
                                        )
                                        cityWeatherListVO.postValue(tempCityWeatherListVO)

                                    }
                                    "London"->{
                                        var tempCityWeatherVO = CityWeatherVO(cityImfomationDTO,result)
                                        var tempCityWeatherListVO = CityWeatherListVO(
                                            cityWeatherListVO.value?.Seoul
                                            ,tempCityWeatherVO
                                            ,cityWeatherListVO.value?.Chicago
                                        )
                                        cityWeatherListVO.postValue(tempCityWeatherListVO)
                                    }
                                    "Chicago"->{
                                        var tempCityWeatherVO = CityWeatherVO(cityImfomationDTO,result)
                                        var tempCityWeatherListVO = CityWeatherListVO(
                                            cityWeatherListVO.value?.Seoul
                                            ,cityWeatherListVO.value?.London
                                            ,tempCityWeatherVO
                                        )
                                        cityWeatherListVO.postValue(tempCityWeatherListVO)
                                    }
                                }
                            }
                        }

                        override fun onFailure(call: Call<WeatherInformationDTO>, t: Throwable) {
                            Log.d("YMC", "onFailure 에러: " + t.message.toString())
                        }
                    })
            }
        }
    }
}