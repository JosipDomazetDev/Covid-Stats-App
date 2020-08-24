package com.covidstats.covidstats.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.covidstats.covidstats.viewmodels.DashDetailShared
import com.covidstats.covidstats.viewmodels.DetailViewModel
import com.covidstats.sovid.R
import com.covidstats.sovid.databinding.FragmentDetailBinding


/**
 * A simple [Fragment] subclass.
 * Use the [DetailFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class DetailFragment : Fragment() {
    companion object {
        fun newInstance() = GlobalFragment()
    }

    lateinit var binding: FragmentDetailBinding
    private val dashDetailShared: DashDetailShared by activityViewModels()
    private val detailViewModel: DetailViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_detail, container, false)
        binding.detailViewModel = detailViewModel

        return binding.root


    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)


    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dashDetailShared.getSelectedCountry().observe(viewLifecycleOwner, Observer {
            detailViewModel.country = it

            val circularProgressDrawable = context?.let { it1 -> CircularProgressDrawable(it1) }
            circularProgressDrawable?.strokeWidth = 5f
            circularProgressDrawable?.centerRadius = 30f
            circularProgressDrawable?.start()

            context?.let { it1 ->
                Glide.with(it1)
                    .load(it.imageURL)
                    .centerCrop()
                    .diskCacheStrategy(DiskCacheStrategy.DATA)
                    .placeholder(circularProgressDrawable)
                    .into(binding.imageViewDetail)
            }

        })
    }
}