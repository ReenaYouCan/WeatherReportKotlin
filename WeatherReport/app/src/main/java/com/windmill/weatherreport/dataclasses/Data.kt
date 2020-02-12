package com.windmill.weatherreport.dataclasses

import android.os.Parcelable
import androidx.room.Entity
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize


@Parcelize
public data class Data(
    @SerializedName("time") val time: Int,
    @SerializedName("summary") val summary: String,
    @SerializedName("icon") val icon: String,
    @SerializedName("sunriseTime") val sunriseTime: Int,
//	@SerializedName("sunsetTime") val sunsetTime : Int,
//	@SerializedName("moonPhase") val moonPhase : Double,
//	@SerializedName("precipIntensity") val precipIntensity : Double,
//	@SerializedName("precipIntensityMax") val precipIntensityMax : Double,
//	@SerializedName("precipIntensityMaxTime") val precipIntensityMaxTime : Int,
//	@SerializedName("precipProbability") val precipProbability : Double,
//	@SerializedName("precipType") val precipType : String,
    @SerializedName("temperatureHigh") val temperatureHigh: Double,
    @SerializedName("temperatureHighTime") val temperatureHighTime: Int,
    @SerializedName("temperatureLow") val temperatureLow: Double,
    @SerializedName("temperatureLowTime") val temperatureLowTime: Int,
//	@SerializedName("apparentTemperatureHigh") val apparentTemperatureHigh : Double,
//	@SerializedName("apparentTemperatureHighTime") val apparentTemperatureHighTime : Int,
//	@SerializedName("apparentTemperatureLow") val apparentTemperatureLow : Double,
//	@SerializedName("apparentTemperatureLowTime") val apparentTemperatureLowTime : Int,
//	@SerializedName("dewPoint") val dewPoint : Double,
    @SerializedName("humidity") val humidity: Double,
    @SerializedName("pressure") val pressure: Double,
    @SerializedName("windSpeed") val windSpeed: Double,
//	@SerializedName("windGust") val windGust : Double,
//	@SerializedName("windGustTime") val windGustTime : Int,
//	@SerializedName("windBearing") val windBearing : Int,
//	@SerializedName("cloudCover") val cloudCover : Double,
//	@SerializedName("uvIndex") val uvIndex : Int,
//	@SerializedName("uvIndexTime") val uvIndexTime : Int,
//	@SerializedName("visibility") val visibility : Int,
//	@SerializedName("ozone") val ozone : Double,
    @SerializedName("temperatureMin") val temperatureMin: Double,
//	@SerializedName("temperatureMinTime") val temperatureMinTime : Int,
    @SerializedName("temperatureMax") val temperatureMax: Double
//	@SerializedName("temperatureMaxTime") val temperatureMaxTime : Int,
//	@SerializedName("apparentTemperatureMin") val apparentTemperatureMin : Double,
//	@SerializedName("apparentTemperatureMinTime") val apparentTemperatureMinTime : Int,
//	@SerializedName("apparentTemperatureMax") val apparentTemperatureMax : Double,
//	@SerializedName("apparentTemperatureMaxTime") val apparentTemperatureMaxTime : Int
) : Parcelable