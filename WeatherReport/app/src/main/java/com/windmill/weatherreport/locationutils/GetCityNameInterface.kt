package com.windmill.weatherreport.locationutils

interface GetCityNameInterface {
    fun onSuccess(cityName: String,latitude : Double,longitude : Double)
    fun onError(errorMessage: String)
}