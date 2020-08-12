package com.covidstats.covidstats.repos

import androidx.lifecycle.MutableLiveData
import com.covidstats.covidstats.api.CovidRetrofitBuilder
import com.covidstats.covidstats.models.Summary
import com.covidstats.covidstats.wrappers.Resource
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import java.lang.Exception

object DashRepository {
    lateinit var fetchJob: CompletableJob
    private lateinit var summaryLiveData: MutableLiveData<Resource<Summary>>

    fun getSummary(): MutableLiveData<Resource<Summary>> {
        if (::summaryLiveData.isInitialized) {
            return summaryLiveData
        }

        fetchJob = Job()
        summaryLiveData = object : MutableLiveData<Resource<Summary>>() {
            override fun onActive() {
                super.onActive()
                fetchJob.let {

                    CoroutineScope(IO + it).launch {
                        withContext(Main) {

                            value = try {
                                value = Resource.loading(null)
                                val summary = CovidRetrofitBuilder.covidApiService.getSummary()

                                if (summary.isSuccessful) {
                                    Resource.success(summary.body())
                                } else {
                                    Resource.error(null)
                                }

                            } catch (e: Exception) {
                                Resource.error(e, null)
                            }

                            it.complete()
                        }
                    }

                }

            }
        }

        return summaryLiveData
    }

    fun cancelFetch() {
        fetchJob.cancel()
    }

    fun reloadSummary() {
        if (!this::summaryLiveData.isInitialized) return

        fetchJob = Job()
        fetchJob.let {
            CoroutineScope(IO + it).launch {
                withContext(Main) {
                    try {
                        summaryLiveData.postValue(Resource.loading(null))
                        val summary = CovidRetrofitBuilder.covidApiService.getSummary()

                        if (summary.isSuccessful) {
                            summaryLiveData.postValue(Resource.success(summary.body()))
                        } else {
                            summaryLiveData.postValue(Resource.error(null))
                        }

                    } catch (e: Exception) {
                        summaryLiveData.postValue(Resource.error(e, null))
                    }

                    it.complete()
                }
            }
        }
    }

}
