package com.windmill.weatherreport.apiservice

import com.windmill.weatherreport.dataclasses.CityWeather
import rx.Observable
import rx.Subscriber
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

class WeatherAPIClient : RetroFitManager() {

    fun getWeatherReport(
        subscriber: Subscriber<CityWeather>,
        latitude: Double,
        longitude: Double
    ) {
        val weatherAPIInterface: WeatherAPIInterface =
            retrofitForUrl.create(WeatherAPIInterface::class.java)
        val response: Observable<CityWeather> =
            weatherAPIInterface.getCategoryOfApp(latitude, longitude)
        response.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
            .subscribe(subscriber)

    }

}