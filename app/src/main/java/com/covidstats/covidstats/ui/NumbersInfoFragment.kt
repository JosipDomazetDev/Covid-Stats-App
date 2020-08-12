package com.covidstats.covidstats.ui

import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.covidstats.sovid.R
import kotlinx.android.synthetic.main.fragment_app_info.*
import kotlinx.android.synthetic.main.fragment_numbers_info.*

/**
 * A simple [Fragment] subclass.
 * Use the [NumbersInfoFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class NumbersInfoFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_numbers_info, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        textView_fragment_numbers.movementMethod =
            LinkMovementMethod.getInstance()

    }
}