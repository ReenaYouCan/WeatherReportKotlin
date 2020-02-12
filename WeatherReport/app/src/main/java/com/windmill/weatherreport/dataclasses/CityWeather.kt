package com.windmill.weatherreport.dataclasses

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import java.io.Serializable

@Parcelize
@Entity
public data class CityWeather(
    @SerializedName("latitude") val latitude: Double,
    @SerializedName("longitude") val longitude: Double,
    @SerializedName("timezone") val timezone: String,
    @SerializedName("currently") val currently: Currently,
    @SerializedName("daily") val daily: Daily,
    @SerializedName("offset") val offset: Double
) : Parcelable {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0;
}