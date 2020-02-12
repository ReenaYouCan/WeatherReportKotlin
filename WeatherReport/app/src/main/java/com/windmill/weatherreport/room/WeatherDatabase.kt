package com.windmill.weatherreport.room

import android.content.Context
import androidx.room.*
import com.windmill.weatherreport.dataclasses.CityWeather

@Database(entities = arrayOf(CityWeather::class), version = 8, exportSchema = false)
@TypeConverters(RoomObjectConverters::class)
abstract class WeatherDatabase : RoomDatabase() {
    abstract fun weatherDAO(): WeatherDAO

    companion object {
        @Volatile
        private var instance: WeatherDatabase? = null

        fun getDatabase(context: Context): WeatherDatabase? {
            if (instance == null) {
                synchronized(WeatherDatabase::class) {
                    instance = Room.databaseBuilder(
                        context.getApplicationContext(),
                        WeatherDatabase::class.java, "weather.db"
                    ).build()
                }
            }
            return instance
        }
    }
}