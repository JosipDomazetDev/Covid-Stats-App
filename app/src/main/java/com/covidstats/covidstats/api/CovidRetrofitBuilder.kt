package com.covidstats.covidstats.api


import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object CovidRetrofitBuilder {

    private const val BASE_URL: String = "https://api.covid19api.com/"

    private val retrofitBuilder: Retrofit.Builder by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
    }

    val covidApiService: CovidApi by lazy {

        retrofitBuilder
            .build()
            .create(CovidApi::class.java)
    }

}