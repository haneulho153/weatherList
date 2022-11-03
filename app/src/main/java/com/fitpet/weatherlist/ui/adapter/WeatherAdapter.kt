package com.fitpet.weatherlist.ui.adapter

import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.fitpet.weatherlist.R
import com.fitpet.weatherlist.databinding.ItemWeatherBinding
import com.fitpet.weatherlist.dto.WeatherInformationItem
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.*

class WeatherAdapter (private val  dataList : ArrayList<WeatherInformationItem>): RecyclerView.Adapter<WeatherAdapter.ViewHolder>() {

    class ViewHolder(private val binding : ItemWeatherBinding):RecyclerView.ViewHolder(binding.root){
        fun bind(dataList:ArrayList<WeatherInformationItem>, position : Int) {

            val data = dataList[position]

            binding.textViewDt.text = data.weather[0].main
            binding.textViewMaxMin.text = String.format("MAX : %s °C  MIN : %s °C",data.main.temp_max , data.main.temp_min)

            //날씨 이미지
            when( data.weather[0].main){
                "Clear"->{  binding.imageViewWeather.load(R.drawable.sunny)}
                "Clouds"->{  binding.imageViewWeather.load(R.drawable.cloudy)}
                "Cloud"->{  binding.imageViewWeather.load(R.drawable.cloud)}
                "Rain"->{  binding.imageViewWeather.load(R.drawable.rain)}
                "Snow"->{  binding.imageViewWeather.load(R.drawable.snow)}

            }

            //날짜
            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
            val customLocalDateTime = LocalDateTime.parse(data.dt_txt, formatter)
            val currentLocalDateTime = LocalDateTime.now()

            if(currentLocalDateTime.dayOfYear == customLocalDateTime.dayOfYear){

                binding.layoutCity.visibility = View.VISIBLE
                binding.textViewCity.text = data.city
                binding.textViewDate.text = "Today"


            }else if((currentLocalDateTime.dayOfYear+1) == customLocalDateTime.dayOfYear){
                binding.layoutCity.visibility = View.GONE
                binding.textViewDate.text = "Tomorrow"
            }else{
                binding.layoutCity.visibility = View.GONE
                var day = (customLocalDateTime.dayOfWeek).getDisplayName(TextStyle.SHORT, Locale.US)
                val date = DateTimeFormatter.ofPattern("dd MMM").format(customLocalDateTime)
                binding.textViewDate.text = day+" "+date
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding  = ItemWeatherBinding.inflate(LayoutInflater.from(parent.context), parent ,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(dataList, position)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }
}