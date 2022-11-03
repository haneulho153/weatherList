package com.fitpet.weatherlist.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.fitpet.weatherlist.databinding.ActivityMainBinding
import com.fitpet.weatherlist.dto.WeatherInformationDTO
import com.fitpet.weatherlist.dto.WeatherInformationItem
import com.fitpet.weatherlist.ui.adapter.WeatherAdapter

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val mMainViewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        mMainViewModel.cityWeatherListVO.observe(this, Observer { it ->

            if(it.Seoul != null && it.London != null && it.Chicago != null ){

                var arrayList = ArrayList<WeatherInformationItem>()

                it.Seoul?.weather?.let{weather->
                    arrayList.addAll( dailyCheck(weather) )
                }
                it.London?.weather?.let { weather ->
                    arrayList.addAll(dailyCheck(weather))
                }
                it.Chicago?.weather?.let { weather ->
                    arrayList.addAll(dailyCheck(weather))
                }

                binding.recyclerViewList.layoutManager = LinearLayoutManager(this,LinearLayoutManager.VERTICAL, false)
                binding.recyclerViewList.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
                binding.recyclerViewList.adapter = WeatherAdapter(arrayList)

            }
            }
        )

        mMainViewModel.loadCoordi(this,"Seoul")
        mMainViewModel.loadCoordi(this,"London")
        mMainViewModel.loadCoordi(this,"Chicago")
    }

    /**
     * 일일 데이타 추출
     * ?.list as ArrayList
     */
    fun dailyCheck(weatherInformationDTO : WeatherInformationDTO) : ArrayList<WeatherInformationItem>{
        val list= weatherInformationDTO?.list as ArrayList

        var arrayList = ArrayList<WeatherInformationItem>()
        var tempDT = 0

        for( i in 0 until list.size ){
            val item = list[i]
            item.city = weatherInformationDTO.city.name
            when(i){
                0->{
                    tempDT =  item.dt + (24*60*60)
                    arrayList.add(item)
                }
                else->{
                    if((tempDT.toString().substring(0 until 7))
                            .equals( item.dt.toString().substring(0 until 7)) ){
                        tempDT = ( item.dt + (24*60*60) )
                        arrayList.add(item)
                    }
                }
            }
        }
        return arrayList
    }
}