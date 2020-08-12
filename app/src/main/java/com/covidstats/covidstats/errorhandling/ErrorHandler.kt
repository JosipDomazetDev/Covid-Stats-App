package com.covidstats.covidstats.errorhandling

import android.content.Context
import android.view.View
import android.widget.TextView
import org.json.JSONException
import retrofit2.Retrofit


class ErrorHandler {
    private lateinit var  errorView: View
    private var context: Context

    constructor(context: Context, errorView: View) {
        this.errorView = errorView
        this.context = context
    }

    constructor(context: Context) {
        this.context = context
    }

    fun displayTextViewMessage(msg: String?) {
        if (errorView !is TextView) return
        val textView = errorView as TextView
        textView.isFocusable = true
        textView.isClickable = true
        textView.isFocusableInTouchMode = true
        textView.error = msg
        textView.requestFocus()
    }

    fun displayTextViewErrorMessage(error: Throwable?) {
        if (errorView !is TextView) return
        val textView = errorView as TextView
        textView.isFocusable = true
        textView.isClickable = true
        textView.isFocusableInTouchMode = true
        textView.error = getCentralizedErrorMessage(error)
        textView.requestFocus()
    }

    private fun getCentralizedErrorMessage(error: Throwable?): String {
        val characterLimit = 200
        error?.printStackTrace()
        var errorMsg: String = "Network request failed! Swipe down to try again."



        error?.printStackTrace()

        var exceptionMsg = ""
        val fullExceptionMsg = error?.message
        if (fullExceptionMsg != null) exceptionMsg = "\n\n" + fullExceptionMsg.substring(
            0,
            Math.min(fullExceptionMsg.length, characterLimit)
        ) + "...."
        return errorMsg + exceptionMsg
    }

    fun reset() {
        if (errorView !is TextView) return
        val textView = errorView as TextView
        textView.error = null
    }
}
