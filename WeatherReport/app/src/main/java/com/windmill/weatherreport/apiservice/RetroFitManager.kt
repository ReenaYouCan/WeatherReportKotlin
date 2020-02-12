package com.windmill.weatherreport.apiservice

import com.google.gson.Gson
import com.google.gson.GsonBuilder

import java.io.IOException
import java.util.concurrent.TimeUnit

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Base class to create retrofit builder
 */

open class RetroFitManager {

    /*
     * Used for all the calls that require token in header
     */
    val retrofitForUrl: Retrofit
        get() = getRetrofit("https://api.darksky.net/forecast/2fdabc587408aefa992867abfa829548/")


    private fun getRetrofit(url: String): Retrofit {
        val gson = GsonBuilder().setLenient().create()

        val retrofitBuilder = Retrofit.Builder()
            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .baseUrl(url)

        val okHttpClientBuilder = OkHttpClient.Builder()


        okHttpClientBuilder.addInterceptor { chain ->
            val origReq = chain.request()
            val requestBuilder = origReq.newBuilder()
                .addHeader("Content-Type", "application/json")
                .addHeader("Accept", "application/json")
            val request = requestBuilder.build()
            chain.proceed(request)
        }


        val okHttpClient: OkHttpClient

        okHttpClient = okHttpClientBuilder
            .connectTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(20, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .build()


        retrofitBuilder.client(okHttpClient)

        return retrofitBuilder.build()
    }

}
