package com.windmill.weatherreport.dataclasses

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize


@Parcelize
public data class Flags(
    @SerializedName("sources") val sources: List<String>,
    @SerializedName("nearest-station") val neareststation: Double,
    @SerializedName("units") val units: String
) : Parcelable