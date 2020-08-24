package com.covidstats.covidstats.utility


import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.roundToInt


class StringUtility private constructor() {
    companion object {

        fun formatNumber(number: Number?): String {
            if (number == null) return ""

            val df = DecimalFormat("###,###")
            df.decimalFormatSymbols = DecimalFormatSymbols(Locale.getDefault())
            return df.format(number)
        }

        fun formatNumberPercentage(percentage: Double): String {

            val roundedPercentage = (((percentage) * 10000).roundToInt() / 100.0)

            val df = DecimalFormat(" #,##0.00 '%'")
            df.decimalFormatSymbols = DecimalFormatSymbols(Locale.getDefault())
            return df.format(roundedPercentage)
        }

        fun formatDate(date: String?): String {
            if (date == null) return ""

            var spf = SimpleDateFormat("yyyy-MM-dd'T'kk:mm:ss'Z'")

            val newDate: Date = spf.parse(date)
            spf = SimpleDateFormat("dd.MM.yyyy, hh:mm", Locale.getDefault())
            return spf.format(newDate).toString()

        }


    }

    // Private constructor to prevent instantiation
    init {
        throw UnsupportedOperationException()
    }
}
