package com.windmill.weatherreport.locationutils

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.LocationManager
import android.os.Looper
import android.util.Log
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.windmill.weatherreport.apputils.AppConstants.Companion.PERMISSION_ID
import java.io.IOException
import java.util.*

class DeviceLocationManager(context: Context) {
    companion object {
        val TAG: String = "DeviceLocationManager"

        // To check if Location permissions are given or not
        fun checkPermissions(context: Context): Boolean {
            if (ActivityCompat.checkSelfPermission(
                    context,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(
                    context,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                return true
            }
            return false
        }

        // If location permissions are not given then request for permissions
        fun requestPermissions(context: Context) {
            ActivityCompat.requestPermissions(
                context as Activity,
                arrayOf(
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ),
                PERMISSION_ID
            )
        }

        // Check if GPS or network provider location services are enabled
        fun isLocationEnabled(context: Context): Boolean {
            val locationManager: LocationManager =
                context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
            return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
                LocationManager.NETWORK_PROVIDER
            )
        }

        fun requestNewLocationData(
            fusedLocationProviderClient: FusedLocationProviderClient,
            locationCallback: LocationCallback
        ) {
            val mLocationRequest = LocationRequest()
            mLocationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
            mLocationRequest.interval = 0
            mLocationRequest.fastestInterval = 0
            mLocationRequest.numUpdates = 1

            fusedLocationProviderClient.requestLocationUpdates(
                mLocationRequest, locationCallback,
                Looper.myLooper()
            )
        }

        fun getCityNameFromLatLong(
            context: Context,
            latitude: Double,
            longitude: Double,
            cityNameInterface: GetCityNameInterface
        ) {

            var cityName: String? = null
            val geocoder = Geocoder(context, Locale.getDefault())
            // Get the location passed to this service through an extra.
            var addresses: List<Address> = emptyList()
            var errorMessage: String? = ""

            try {
                addresses = geocoder.getFromLocation(
                    latitude,
                    longitude,
                    // In this sample, we get just a single address.
                    1
                )
            } catch (ioException: IOException) {
                // Catch network or other I/O problems.
                errorMessage = "Network Error"
                Log.e(TAG, errorMessage, ioException)
            } catch (illegalArgumentException: IllegalArgumentException) {
                // Catch invalid latitude or longitude values.
                errorMessage = "Invalid Latitude $longitude"
                Log.e(
                    TAG, "$errorMessage. Latitude = $latitude , " +
                            "Longitude =  $longitude", illegalArgumentException
                )
            }


            // Handle case where no address was found.
            if (addresses.isEmpty()) {
                if (errorMessage!!.isEmpty()) {
                    errorMessage = "No address found"
                    Log.e(TAG, errorMessage)
                    cityNameInterface.onError(errorMessage)
                }

            } else {
                val address = addresses[0]
                // Fetch the address lines using getAddressLine,
                // join them, and send them to the thread.
                cityName = address.locality
                cityNameInterface.onSuccess(cityName, latitude, longitude)
            }

        }
    }
}