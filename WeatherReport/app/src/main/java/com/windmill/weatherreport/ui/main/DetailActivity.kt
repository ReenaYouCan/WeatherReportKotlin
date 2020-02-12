package com.windmill.weatherreport.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.windmill.weatherreport.R
import com.windmill.weatherreport.apputils.AppConstants
import com.windmill.weatherreport.apputils.CommonUtils
import com.windmill.weatherreport.databinding.ActivityDetailBinding
import com.windmill.weatherreport.dataclasses.CityWeather
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.content_detail.*

class DetailActivity : AppCompatActivity() {

    private lateinit var mCityWeather: CityWeather
    private lateinit var mActivityDetailBinding: ActivityDetailBinding
    private lateinit var mLinearLayoutManager: LinearLayoutManager


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mActivityDetailBinding = DataBindingUtil.setContentView(this, R.layout.activity_detail)

        getDataFromIntent()
        setDataToView()
    }

    /**
     * Get Weather details and City name from Intent and populate onto the screen
     */
    private fun getDataFromIntent() {
        val intent = getIntent();
        if (intent != null) {
            mCityWeather = intent.getParcelableExtra(AppConstants.WEATHER_PARCELABLE) as CityWeather
            toolbar.setTitle(intent.getStringExtra(AppConstants.CITY_NAME))
            setSupportActionBar(toolbar)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)

        }
    }

    /*
    * Bind data to the view
    * */

    private fun setDataToView() {
        ivWeather.setImageResource(
            CommonUtils.getImageResource(
                mCityWeather.currently.icon
            )
        )
        mActivityDetailBinding.content.temperature =
            CommonUtils.temperatureConversion(this, mCityWeather.currently.temperature)
        mActivityDetailBinding.content.pressure =
            mCityWeather.currently.pressure.toString() + CommonUtils.WeatherUnits.PRESSURE.unit
        mActivityDetailBinding.content.humidity =
            mCityWeather.currently.humidity.toString() + CommonUtils.WeatherUnits.HUMIDITY.unit
        mActivityDetailBinding.content.wind =
            mCityWeather.currently.windSpeed.toString() + CommonUtils.WeatherUnits.WIND.unit
        mActivityDetailBinding.content.currently = mCityWeather.currently

        val weatherAdapter = WeatherAdapter(mCityWeather.daily.data, this)
        mLinearLayoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        rvCityWeather.layoutManager = mLinearLayoutManager
        rvCityWeather.adapter = weatherAdapter;

    }
}
