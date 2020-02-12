package com.windmill.weatherreport.ui.main


import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.windmill.weatherreport.apiservice.WeatherAPIClient
import com.windmill.weatherreport.apiservice.WeatherLocalDBInterface
import com.windmill.weatherreport.dataclasses.CityWeather
import com.windmill.weatherreport.room.WeatherDatabase
import rx.Subscriber
import java.util.concurrent.Executors


class MainViewModel : ViewModel() {
    var cityWeatherMutable: MutableLiveData<CityWeather>? = MutableLiveData()
    var error: MutableLiveData<String> = MutableLiveData()
    private var weatherDatabase: WeatherDatabase? = null
    var cityWeather: CityWeather? = null

    fun getWeather(context: Context, latitude: Double, longitude: Double) {
        val apiClient = WeatherAPIClient();
        apiClient.getWeatherReport(object : Subscriber<CityWeather>() {
            override fun onNext(t: CityWeather?) {
                if (t != null) {

                    Executors.newSingleThreadExecutor()
                        .execute {
                            weatherDatabase = WeatherDatabase.getDatabase(context)!!

                            if (weatherDatabase != null) {
                                if (!(weatherDatabase!!.weatherDAO().getWeatherReport().isNotEmpty())) {
                                    weatherDatabase!!.weatherDAO().deleteAll()
                                }
                                weatherDatabase!!.weatherDAO().insertWeather(t)
                            }
                        }
                    cityWeatherMutable?.value = t
                }
            }

            override fun onCompleted() {
            }

            override fun onError(e: Throwable?) {
                error.value = e?.message
            }
        }, latitude, longitude)
    }

    fun getWeatherFromLocalDB(context: Context, localDBInterface: WeatherLocalDBInterface) {
        Executors.newSingleThreadExecutor()
            .execute {
                weatherDatabase = WeatherDatabase.getDatabase(context)!!
                if (weatherDatabase!!.weatherDAO().getWeatherReport().size > 0) {
                    cityWeather = weatherDatabase!!.weatherDAO().getWeatherReport().get(0)
                    localDBInterface.onSuccess(cityWeather!!)
                } else {
                    localDBInterface.onError("Local BD Error")
                }
            }
    }
}
