package com.windmill.weatherreport.dataclasses

import android.os.Parcelable
import androidx.room.Entity
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize


@Parcelize
 public data class Currently (
	@SerializedName("time") val time : Int,
	@SerializedName("summary") val summary : String,
	@SerializedName("icon") val icon : String,
//	@SerializedName("precipIntensity") val precipIntensity : Int,
//	@SerializedName("precipProbability") val precipProbability : Int,
	@SerializedName("temperature") val temperature : Double,
	@SerializedName("apparentTemperature") val apparentTemperature : Double,
//	@SerializedName("dewPoint") val dewPoint : Double,
	@SerializedName("humidity") val humidity : Double,
	@SerializedName("pressure") val pressure : Double,
	@SerializedName("windSpeed") val windSpeed : Double,
	@SerializedName("windGust") val windGust : Double,
	@SerializedName("windBearing") val windBearing : Int,
	@SerializedName("cloudCover") val cloudCover : Double,
//	@SerializedName("uvIndex") val uvIndex : Int,
	@SerializedName("visibility") val visibility : Int,
	@SerializedName("ozone") val ozone : Double
) : Parcelable