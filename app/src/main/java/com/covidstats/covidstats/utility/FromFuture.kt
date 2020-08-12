package com.covidstats.covidstats.utility

import com.covidstats.covidstats.models.Country
import java.util.*

class FromFuture private constructor() {
    companion object {

        fun<T> sortWith(arrayList: List<Country>, key: Int): List<Country> {
            return arrayList.sortedWith(compareBy {
                it.allNumbers[key]?.toDouble()?.times(-1)
            })
        }

    }

    // Private constructor to prevent instantiation
    init {
        throw UnsupportedOperationException()
    }
}
