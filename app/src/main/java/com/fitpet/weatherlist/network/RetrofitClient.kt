package com.fitpet.weatherlist.network

import android.content.Context
import android.util.Log
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitClient private constructor(context: Context, baseUrl: String) {

    var retrofitService : RetrofitService ?= null;

    companion object{
        private var INSTANCE : RetrofitClient?=null
        fun getInstance(context : Context) : RetrofitClient = INSTANCE ?: synchronized(this){
            INSTANCE ?: RetrofitClient(context, "https://api.openweathermap.org/")
                .also {
                    Log.d("logger", " retrofit instance create start")

                    INSTANCE = it }
        }
    }


    init {
        Log.d("logger", " retrofit instance create init")
        val retrofit = getRetrofit(baseUrl)
        retrofitService = retrofit.create(RetrofitService::class.java)
    }
    private fun getRetrofit(baseUrl : String): Retrofit{

        Log.d("logger", " getRetrofit")
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }


}