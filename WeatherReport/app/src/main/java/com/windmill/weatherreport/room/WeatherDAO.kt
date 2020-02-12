package com.windmill.weatherreport.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.windmill.weatherreport.dataclasses.CityWeather

@Dao
interface WeatherDAO {

    @Query("SELECT * FROM CityWeather")
    fun getWeatherReport(): List<CityWeather>

    @Insert
    fun insertWeather(cityWeather: CityWeather)

    @Query("DELETE FROM CityWeather")
    fun deleteAll()
}