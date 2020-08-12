package com.covidstats.covidstats.models

class SortCategory(var type: Int, var name: String) {

    /*
         <item>Total Cases</item>
        <item>Total Recovered</item>
        <item>Total Deaths</item>
        <item>Active Cases</item>

        <item>New Cases</item>
        <item>New Recovered</item>
        <item>New Deaths</item>

        <item>Recovery Rate</item>
        <item>Lethality Rate</item>
    */


    companion object {
       const val TOTAL_CASES: Int = 2
        const val TOTAL_RECOVERED: Int = 4
        const val TOTAL_DEATHS: Int = 8
        const val ACTIVE_CASES: Int = 16

        const val NEW_CASES: Int = 32
        const val NEW_RECOVERED: Int = 64
        const val NEW_DEATHS: Int = 128

        const val RECOVERY_RATE: Int = 256
        const val LETHALITY_RATE: Int = 512


        const val POSITIVE: Int = 1
        const val NEUTRAL: Int = 0
        const val NEGATIVE: Int = -1

        var TYPES_WITH_STATE =
            arrayOf(
                intArrayOf(
                    TOTAL_CASES,
                    TOTAL_RECOVERED,
                    TOTAL_DEATHS,
                    ACTIVE_CASES,
                    NEW_CASES,
                    NEW_RECOVERED,
                    NEW_DEATHS,
                    RECOVERY_RATE,
                    LETHALITY_RATE
                ), intArrayOf(
                    NEGATIVE,
                    POSITIVE,
                    NEGATIVE,
                    NEUTRAL,
                    NEGATIVE,
                    POSITIVE,
                    NEGATIVE,
                    POSITIVE,
                    NEGATIVE
                )
            )


    }


    override fun toString(): String {
        return name
    }
}