package com.windmill.weatherreport.apiservice

import com.windmill.weatherreport.dataclasses.CityWeather

interface WeatherLocalDBInterface {
    fun onSuccess(cityweather: CityWeather)
    fun onError(error: String)
}
