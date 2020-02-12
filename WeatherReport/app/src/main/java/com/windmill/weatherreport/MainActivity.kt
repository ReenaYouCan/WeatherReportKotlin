package com.windmill.weatherreport

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.provider.Settings
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.windmill.weatherreport.apiservice.WeatherLocalDBInterface
import com.windmill.weatherreport.apputils.AppConstants
import com.windmill.weatherreport.apputils.AppConstants.Companion.PERMISSION_ID
import com.windmill.weatherreport.apputils.CommonUtils
import com.windmill.weatherreport.databinding.MainActivityBinding
import com.windmill.weatherreport.dataclasses.CityWeather
import com.windmill.weatherreport.locationutils.DeviceLocationManager
import com.windmill.weatherreport.locationutils.GetCityNameInterface
import com.windmill.weatherreport.ui.main.DetailActivity
import com.windmill.weatherreport.ui.main.MainViewModel
import kotlinx.android.synthetic.main.main_activity.*


class MainActivity() : AppCompatActivity(), GetCityNameInterface, WeatherLocalDBInterface {

    private lateinit var mFusedLocationProviderClient: FusedLocationProviderClient
    private lateinit var mContext: Context
    private lateinit var mMainActivityBinding: MainActivityBinding
    private lateinit var mMainViewModel: MainViewModel
    private lateinit var mCityWeather: CityWeather
    private lateinit var mCityName: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initClasses()
        observeResponse()
        handleClickEvents()
        getUsersLastLocation()
    }

    override fun onResume() {
        super.onResume()

    }

    /*
    * Init all necessary classes
    * Binding
    * FusedLocationProviderClient
    * View Model
    * */
    private fun initClasses() {
        mContext = this;
        mMainActivityBinding = DataBindingUtil.setContentView(this, R.layout.main_activity)
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
        mMainViewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
    }

    /*
    * Get Users current location and get the city name to fetch the weather
    * */
    private fun getUsersLastLocation() {
        mMainActivityBinding.pbCircular.visibility = View.VISIBLE
        if (DeviceLocationManager.checkPermissions(this)) {
            if (DeviceLocationManager.isLocationEnabled(this)) {
                mFusedLocationProviderClient.lastLocation.addOnCompleteListener(this) { locationTask ->

                    val location: Location? = locationTask.result
                    if (location == null) {
                        DeviceLocationManager.requestNewLocationData(
                            mFusedLocationProviderClient,
                            object : LocationCallback() {
                                override fun onLocationResult(locationResult: LocationResult?) {
                                    super.onLocationResult(locationResult)
                                    val locationUsingCallback = locationResult?.lastLocation;
                                    if (locationUsingCallback != null)
                                        DeviceLocationManager.getCityNameFromLatLong(
                                            mContext,
                                            locationUsingCallback.latitude,
                                            locationUsingCallback.longitude,
                                            this@MainActivity
                                        )
                                }
                            })

                    } else {

                        DeviceLocationManager.getCityNameFromLatLong(
                            mContext,
                            location.latitude,
                            location.longitude,
                            this@MainActivity
                        )
                    }
                }

            } else {
                // Start Setting Activity to enable location provider
                val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                startActivity(intent)
            }
        } else {
            DeviceLocationManager.requestPermissions(this)
        }
    }


    /*
    * Added observer to fetch the live data from view model and pass it to the view
    * */
    //Observer to capture live data from model
    private fun observeResponse() {
        val cityWeatherObserver = Observer<CityWeather> { cityWeather ->
            // Update UI
            setCityWeather(cityWeather)

        }
        mMainViewModel.cityWeatherMutable?.observe(this, cityWeatherObserver)

        val errorObserver = Observer<String> { error ->
            pbCircular.visibility = View.GONE
            CommonUtils.showToast(this, error)
            pbCircular.visibility = View.VISIBLE
            mMainViewModel.getWeatherFromLocalDB(this, this)
        }
        mMainViewModel.error?.observe(this, errorObserver)
    }

    private fun handleClickEvents() {
        mMainActivityBinding.rvCityWeather.setOnClickListener {
            val intent: Intent = Intent(this, DetailActivity::class.java)
            intent.putExtra(AppConstants.WEATHER_PARCELABLE, mCityWeather)
            intent.putExtra(AppConstants.CITY_NAME, mCityName)
            startActivity(intent)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == PERMISSION_ID) {
            if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                // Granted. Start getting the location information
                getUsersLastLocation()
            }
        }
    }

    /*
    * Callback to get the city name after geo-coding
    * */
    override fun onSuccess(cityName: String, latitude: Double, longitude: Double) {
        mMainActivityBinding.cityName = cityName
        mCityName = cityName;
        CommonUtils.showToast(this, cityName)
        mMainViewModel.getWeather(this, latitude, longitude)

    }

    override fun onSuccess(cityweather: CityWeather) {

        runOnUiThread {
            // Stuff that updates the UI
            mCityWeather = cityweather;
            pbCircular.visibility = View.GONE
            CommonUtils.showToast(this, "Success Local DB")
            setCityWeather(cityweather)

        }

    }


    /*
    * Callback to get error if in case there is issue in geo-coding
    * */
    override fun onError(errorMessage: String) {
        pbCircular.visibility = View.GONE


        mMainActivityBinding.cityName = errorMessage

        Handler(Looper.getMainLooper()).post(Runnable {
            CommonUtils.showToast(this, errorMessage)
        })
    }

    private fun setCityWeather(cityWeather: CityWeather) {
        mCityWeather = cityWeather
        mMainActivityBinding.summary = cityWeather.currently.summary
        pbCircular.visibility = View.GONE

        ivWeather.setImageResource(CommonUtils.getImageResource(cityWeather.currently.icon))
        tvTemperature.text =
            CommonUtils.temperatureConversion(this, cityWeather.currently.temperature)
    }
}