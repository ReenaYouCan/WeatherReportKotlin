package com.windmill.weatherreport.apiservice

import com.windmill.weatherreport.apputils.AppConstants
import com.windmill.weatherreport.dataclasses.CityWeather
import retrofit2.http.GET
import retrofit2.http.Path
import rx.Observable

interface WeatherAPIInterface {
    @GET(AppConstants.CITY_LAT_LONG_ENDPOINT)
    fun getCategoryOfApp(@Path("latitude") latitude: Double,@Path("longitude") longitude: Double): Observable<CityWeather>
}
