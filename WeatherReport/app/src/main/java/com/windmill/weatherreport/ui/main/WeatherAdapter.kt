package com.windmill.weatherreport.ui.main


import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.windmill.weatherreport.R
import com.windmill.weatherreport.apputils.CommonUtils
import com.windmill.weatherreport.databinding.WeatherListItemBinding
import com.windmill.weatherreport.dataclasses.Data
import kotlinx.android.synthetic.main.weather_list_item.view.*

class WeatherAdapter(val dailyWeather: List<Data>, val contextE: Context) :
    RecyclerView.Adapter<WeatherAdapter.WeatherViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.weather_list_item, parent, false)
        return WeatherViewHolder(view)
    }

    override fun getItemCount(): Int {
        return dailyWeather.size
    }

    override fun onBindViewHolder(holder: WeatherViewHolder, position: Int) {
        holder.bind(dailyWeather.get(position))
    }


    inner class WeatherViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: Data) {
            with(itemView)
            {
                tvTemperature.text =
                    CommonUtils.temperatureConversion(contextE,item.temperatureHigh)+ "/" + CommonUtils.temperatureConversion(contextE,item.temperatureLow)
                tvDay.text = CommonUtils.getDayFromUnixTime(item.time.toLong())
                tvTime.visibility = View.GONE
                ivWeather.setImageResource(CommonUtils.getImageResource(item.icon))

            }
        }
    }

}