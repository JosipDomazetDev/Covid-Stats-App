package com.covidstats.covidstats.ui

import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.text.HtmlCompat
import androidx.fragment.app.Fragment
import com.covidstats.sovid.R
import kotlinx.android.synthetic.main.fragment_app_info.*


/**
 * A simple [Fragment] subclass.
 * Use the [AppInfoFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AppInfoFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_app_info, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        textView_fragment_app.movementMethod =
            LinkMovementMethod.getInstance()

        textView_fragment_app.text = HtmlCompat.fromHtml(
            getString(
                R.string.app_text
            ), HtmlCompat.FROM_HTML_MODE_LEGACY
        )
    }
}