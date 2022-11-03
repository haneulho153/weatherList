package com.fitpet.weatherlist.network

class RetrofitManager {
    companion object{
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.openweathermap.org/data/2.5")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}