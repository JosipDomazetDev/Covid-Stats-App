package com.covidstats.covidstats.viewmodels

import android.content.Context
import android.os.Build
import android.text.Html
import androidx.lifecycle.ViewModel
import com.covidstats.covidstats.models.Country
import com.covidstats.sovid.R

class DetailViewModel : ViewModel() {
    lateinit var country: Country

    fun getEntry(context: Context, key: Int): String {
        return context.getString(R.string.number_pos)
            .format(
                country.getFormattedDisplayNumber(country.allNumbers[key], key),
                country.allPositions[key]
            )
    }
}