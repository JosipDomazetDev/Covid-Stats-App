package com.covidstats.covidstats.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.text.HtmlCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.navGraphViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.covidstats.covidstats.adapters.GlobalAdapter
import com.covidstats.covidstats.errorhandling.ErrorHandler
import com.covidstats.covidstats.models.GlobalEntry
import com.covidstats.covidstats.utility.StringUtility
import com.covidstats.covidstats.viewmodels.DashViewModel
import com.covidstats.covidstats.wrappers.Status
import com.covidstats.sovid.R
import kotlinx.android.synthetic.main.fragment_dash.*
import kotlinx.android.synthetic.main.global_fragment.*

class GlobalFragment : Fragment(), GlobalAdapter.OnItemClickListener {

    companion object {
        fun newInstance() = GlobalFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.global_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)


    }

    private val viewModel: DashViewModel by navGraphViewModels(R.id.nav_graph)
    private lateinit var adapter: GlobalAdapter
    private var globalStats: ArrayList<GlobalEntry> = ArrayList()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = GlobalAdapter(globalStats, this)
        recycler_view_global.adapter = adapter
        recycler_view_global.layoutManager = LinearLayoutManager(context)
        recycler_view_global.setHasFixedSize(true)

        val errorHandler = ErrorHandler(requireContext(), dateTextView_global)


        viewModel.getSummary().observe(viewLifecycleOwner, Observer {
            when (it.status) {
                Status.SUCCESS -> {
                    val global = it.data?.global


                    dateTextView_global.text = (HtmlCompat.fromHtml(
                        getString(
                            R.string.date_of_last_update_global,
                            StringUtility.formatDate(it.data?.date)
                        ), HtmlCompat.FROM_HTML_MODE_LEGACY
                    ))

                    val totDeath: Double = global?.totalDeaths?.toDouble() ?: 0.0
                    val totConf: Double = global?.totalConfirmed?.toDouble() ?: 1.0
                    val peopleInTheWorld = 7802005691.0


                    val globalShare =
                        ((totConf - totDeath) / peopleInTheWorld)

                    /*val lethRate =
                        ((totDeath / (totDeath + totRec)) * 10000).roundToInt() / 100.0*/

                    val lethRate =
                        (totDeath / (totConf))

                    val recRate =
                        1 - lethRate


                    globalStats.clear()
                    globalStats.add(
                        GlobalEntry(
                            "Global Spread",
                            StringUtility.formatNumberPercentage(globalShare)
                        )
                    )

                    globalStats.add(
                        GlobalEntry(
                            "Recovery Rate",
                            StringUtility.formatNumberPercentage(recRate),
                            GlobalEntry.POSITIVE
                        )
                    )

                    globalStats.add(
                        GlobalEntry(
                            "Case Fatality Rate",
                            StringUtility.formatNumberPercentage(lethRate)
                        )
                    )


                    globalStats.add(
                        GlobalEntry(
                            "Total Cases",
                            global?.totalConfirmed,
                            addSepAbove = true
                        )
                    )
                    globalStats.add(
                        GlobalEntry(
                            "Total Recovered",
                            global?.totalRecovered,
                            GlobalEntry.POSITIVE
                        )
                    )

                    globalStats.add(GlobalEntry("Total Deaths", global?.totalDeaths))


                    val totGlobPending =
                        global?.totalConfirmed?.minus((global.totalDeaths + global.totalRecovered))

                    globalStats.add(
                        GlobalEntry(
                            "Active Cases",
                            totGlobPending,
                            GlobalEntry.NEUTRAL
                        )
                    )





                    globalStats.add(
                        GlobalEntry(
                            "New Cases",
                            global?.newConfirmed,
                            addSepAbove = true
                        )
                    )

                    globalStats.add(
                        GlobalEntry(
                            "New Recovered",
                            global?.newRecovered,
                            GlobalEntry.POSITIVE
                        )
                    )


                    globalStats.add(GlobalEntry("New Deaths", global?.newDeaths))



                    adapter.notifyDataSetChanged()
                    dateTextView_global.visibility = View.VISIBLE
                    progress_bar_global.visibility = View.GONE
                    swipe_refresh_global.isEnabled = false
                }

                Status.ERROR -> {
                    errorHandler.displayTextViewErrorMessage(it.error)
                }

                Status.LOADING -> {
                    progress_bar_global.visibility = View.VISIBLE
                    progress_bar_global.bringToFront()
                }
            }
            if (it.status != Status.ERROR)
                errorHandler.reset();

        })



        swipe_refresh_global.setOnRefreshListener {
            if (viewModel.getSummary().value?.status == Status.SUCCESS) {
                Toast.makeText(
                    context,
                    "Data loaded successfully, no need to reload!",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                viewModel.reload()
                swipe_refresh_dash.isRefreshing = false
            }

        }


    }

    override fun onItemClick(position: Int) {
    }

}