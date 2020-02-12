package com.windmill.weatherreport.room
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.windmill.weatherreport.dataclasses.Currently
import com.windmill.weatherreport.dataclasses.Daily
import com.windmill.weatherreport.dataclasses.Data

public class RoomObjectConverters {
    @TypeConverter
    fun fromCurrentlyString(value: String?): Currently? {
        val listType =
            object : TypeToken<Currently?>() {}.type
        return Gson().fromJson(value, listType)
    }


    @TypeConverter
    fun fromCurrentlyJsonObject(currently: Currently?): String? {
        val gson = Gson()
        return gson.toJson(currently)
    }
    @TypeConverter
    fun fromDailyJsonObject(daily: Daily?): String? {
        val gson = Gson()
        return gson.toJson(daily)
    }
    @TypeConverter
    fun fromDailyString(value: String?): Daily? {
        val listType =
            object : TypeToken<Daily?>() {}.type
        return Gson().fromJson(value, listType)
    }


    @TypeConverter
    fun fromDataString(value: String?): List<Data?>? {
        val listType = object :
            TypeToken<List<Data?>?>() {}.type
        return Gson().fromJson<List<Data?>>(
            value,
            listType
        )
    }

    @TypeConverter
    fun fromDataJsonObject(dataList: List<Data?>?): String? {
        val gson = Gson()
        return gson.toJson(dataList)
    }


}