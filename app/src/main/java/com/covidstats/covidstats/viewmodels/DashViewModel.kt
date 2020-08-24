package com.covidstats.covidstats.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.covidstats.covidstats.models.SortCategory
import com.covidstats.covidstats.models.Summary
import com.covidstats.covidstats.repos.DashRepository
import com.covidstats.covidstats.wrappers.Resource
import com.covidstats.covidstats.wrappers.Status

class DashViewModel : ViewModel() {

    fun cancelJobs() {
        DashRepository.cancelFetch()
    }

    private var summaryLiveData: MutableLiveData<Resource<Summary>> = DashRepository.getSummary()


    fun getSummary(): LiveData<Resource<Summary>> {
        return summaryLiveData
    }

    private val sortLiveData: MutableLiveData<SortCategory> = MutableLiveData()

    fun setSortCategory(sortCategory: SortCategory) {
        sortLiveData.postValue(sortCategory)
    }

    fun getSortLiveData(): LiveData<SortCategory> {
        return sortLiveData
    }

    fun reload() {
        if (summaryLiveData.value?.status != Status.LOADING) {
            DashRepository.reloadSummary()
        }
    }
}

