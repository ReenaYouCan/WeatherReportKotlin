package com.windmill.weatherreport.dataclasses

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
public data class Hourly (

	@SerializedName("summary") val summary : String,
	@SerializedName("icon") val icon : String,
	@SerializedName("data") val data : List<Data>
) : Parcelable