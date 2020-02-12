package com.windmill.weatherreport.apputils

import android.content.Context
import android.widget.Toast
import com.windmill.weatherreport.R
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*


class CommonUtils {
    companion object {
        fun showToast(context: Context, message: String) {
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        }

        fun temperatureConversion(context: Context, fahrenheit: Double): String {
            return DecimalFormat(AppConstants.TWO_PLACE_DECIMAL_FORMAT).format((fahrenheit - 32) * (0.5556)).toString() + " \u2103"

        }

        fun getDayFromUnixTime(timeinMiliseconds: Long): String {
            return SimpleDateFormat(AppConstants.DAY_TIME_FORMAT).format(Date(timeinMiliseconds * 1000))
        }

        fun getImageResource(weatherInfo: String): Int {
            when (weatherInfo) {
                WeatherSummary.CLEAR_DAY.summary -> return R.drawable.ic_sunrise
                WeatherSummary.CLOUDY.summary -> return R.drawable.ic_cloudy
                WeatherSummary.PARTLY_CLOUDY_DAY.summary -> return R.drawable.ic_partialy_cloudy_day
                WeatherSummary.PARTLY_CLOUDY_NIGHT.summary -> return R.drawable.ic_partly_cloudy_night
                WeatherSummary.PARTLY_CLOUDY.summary -> return R.drawable.ic_cloudy
                else -> return R.drawable.ic_humidity
            }
        }
    }


    enum class WeatherSummary(val summary: String) {
        CLEAR_DAY("clear-day"),
        CLOUDY("cloudy"),
        PARTLY_CLOUDY_DAY("partly-cloudy-day"),
        PARTLY_CLOUDY_NIGHT("partly-cloudy-night"),
        PARTLY_CLOUDY("partly-cloudy"),
    }

    enum class WeatherUnits(val unit: String) {
        PRESSURE("hPa"),
        WIND("m/s"),
        HUMIDITY("%")
    }
}