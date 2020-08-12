package com.covidstats.covidstats.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.covidstats.covidstats.models.Country

class DashDetailShared : ViewModel() {
    val selectedCountry = MutableLiveData<Country>()

    fun select(
        item: Country,
        sortedCountries: List<Country>
    ) {

        item.getAllPositions(sortedCountries)
        selectedCountry.value = item
    }

    fun getSelectedCountry(): LiveData<Country> {
        return selectedCountry
    }

}