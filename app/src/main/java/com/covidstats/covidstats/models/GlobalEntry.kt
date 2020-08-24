package com.covidstats.covidstats.models

import com.covidstats.covidstats.utility.StringUtility

class GlobalEntry {
    var attribute: String
    var value: String
    var state: Int
    var addSepAbove: Boolean


    companion object {
        const val POSITIVE: Int = 1
        const val NEUTRAL: Int = 0
        const val NEGATIVE: Int = -1
    }


    constructor(
        attribute: String,
        value: String,
        state: Int = NEGATIVE,
        addSepAbove: Boolean = false
    ) {
        this.attribute = attribute
        this.value = value
        this.state = state
        this.addSepAbove = addSepAbove
    }

    constructor(
        attribute: String,
        value: Number?,
        state: Int = NEGATIVE,
        addSepAbove: Boolean = false
    ) {
        this.attribute = attribute
        this.value = StringUtility.formatNumber(value)
        this.state = state
        this.addSepAbove = addSepAbove
    }


}