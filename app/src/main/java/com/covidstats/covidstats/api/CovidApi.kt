package com.covidstats.covidstats.api

import com.covidstats.covidstats.models.Summary
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET

interface CovidApi {

    @GET("summary")
    suspend fun getSummary(): Response<Summary>
}