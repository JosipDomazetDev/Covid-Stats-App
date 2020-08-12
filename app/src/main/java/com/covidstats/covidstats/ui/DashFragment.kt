package com.covidstats.covidstats.ui

import android.os.Bundle
import android.util.Log
import android.view.*
import android.view.inputmethod.EditorInfo
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.navGraphViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.covidstats.covidstats.adapters.NationalAdapter
import com.covidstats.covidstats.errorhandling.ErrorHandler
import com.covidstats.covidstats.models.Country
import com.covidstats.covidstats.models.SortCategory
import com.covidstats.covidstats.utility.UserUtility
import com.covidstats.covidstats.viewmodels.DashDetailShared
import com.covidstats.covidstats.viewmodels.DashViewModel
import com.covidstats.covidstats.wrappers.Status
import com.covidstats.sovid.R
import kotlinx.android.synthetic.main.fragment_dash.*


class DashFragment : Fragment(), NationalAdapter.OnItemClickListener {

    companion object {
        fun newInstance() = DashFragment()
    }

    private val viewModel: DashViewModel by navGraphViewModels(R.id.nav_graph)
    private val dashDetailShared: DashDetailShared by activityViewModels()
    private lateinit var adapter: NationalAdapter
    private var sortedCountries: ArrayList<Country> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // https://stackoverflow.com/questions/12090335/menu-in-fragments-not-showing
        setHasOptionsMenu(true)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_dash, container, false)

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = NationalAdapter(sortedCountries, this)
        recycler_view.adapter = adapter
        recycler_view.layoutManager = LinearLayoutManager(context)
        recycler_view.setHasFixedSize(true)

        val errorHandler = ErrorHandler(requireContext(), sortTextView_dash)


        /*   val viewModel2: DashViewModel
                   by navGraphViewModels(R.id.nav_graph)

           viewModel = ViewModelProvider(this).get(DashViewModel::class.java)*/

        viewModel.getSummary().observe(viewLifecycleOwner, Observer {
            when (it.status) {
                Status.SUCCESS -> {
                    val newSortedCountries = it.data?.countries?.sortedWith(compareBy {
                        -it.getDisplayNumber(
                            spinner_dash.selectedItem as SortCategory
                        )
                    }) as MutableList<Country>


                    displayList(newSortedCountries)
                    progress_bar_dash.visibility = View.GONE
                    swipe_refresh_dash.isEnabled = false
                }

                Status.ERROR -> {
                    errorHandler.displayTextViewErrorMessage(it.error)
                }

                Status.LOADING -> {
                    progress_bar_dash.visibility = View.VISIBLE
                    progress_bar_dash.bringToFront()
                }
            }
            if (it.status != Status.ERROR)
                errorHandler.reset();

        })

        Log.e("XXXX", viewModel.hashCode().toString())


        val sortCategories = initSortCategories()


        val arrayAdapter =
            context?.let { ArrayAdapter(it, R.layout.spinner_item, sortCategories) }



        viewModel.getSortLiveData().observe(viewLifecycleOwner, Observer {
            // Set the selection to be consistent with the ViewModel
            spinner_dash.setSelection(SortCategory.TYPES_WITH_STATE[0].indexOf(it.type))

            val newSortedCountries =
                sortedCountries.sortedWith(compareBy { country -> -(country.getDisplayNumber(it) as Double) })


            if (newSortedCountries.isNotEmpty()) {
                displayList(newSortedCountries)
            }
        })

        spinner_dash.adapter = arrayAdapter
        spinner_dash.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                viewModel.setSortCategory(spinner_dash.getItemAtPosition(position) as SortCategory)
            }
        }



        swipe_refresh_dash.setOnRefreshListener {
            when {
                viewModel.getSummary().value?.status == Status.SUCCESS -> {
                    Toast.makeText(context, "Data loaded successfully, no need to reload!", Toast.LENGTH_SHORT).show()
                }

                searchView.isIconified -> {
                    viewModel.reload()
                    swipe_refresh_dash.isRefreshing = false
                }

                else -> {
                    swipe_refresh_dash.isRefreshing = false
                    Toast.makeText(context, "Close searchbar first!", Toast.LENGTH_SHORT).show()
                }
            }
        }

    }

    private fun initSortCategories(): List<SortCategory> {
        val sortCategories: MutableList<SortCategory> = ArrayList()

        resources.getStringArray(R.array.sort).forEachIndexed { index, s ->
            sortCategories.add(SortCategory(SortCategory.TYPES_WITH_STATE[0][index], s))
        }

        return sortCategories
    }

    private lateinit var searchView: SearchView

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)

        val searchItem = menu.findItem(R.id.action_search)
        searchItem.isVisible = true

        searchView = searchItem.actionView as SearchView
        searchView.imeOptions = EditorInfo.IME_ACTION_DONE


        searchView.setOnQueryTextListener(object :
            SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                UserUtility.hideKeyboard(activity)
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                adapter.filter.filter(newText)
                return false
            }
        })

    }


    private fun displayList(newSortedCountries: List<Country>) {
        //newSortedCountries.forEachIndexed { i, country -> country.place = i + 1 }
        sortedCountries.clear()
        sortedCountries.addAll(newSortedCountries)
        // sortedCountries.forEachIndexed { i, country -> country.place = i + 1 }

        adapter.notifyDataSetChangedWithList(newSortedCountries)
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.cancelJobs()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

    override fun onItemClick(position: Int) {
        /*  val clickedItem = sortedCountries?.get(position)
          if (clickedItem?.totalConfirmed != null) {
              clickedItem.totalConfirmed *= 2
          }

          adapter.notifyItemChanged(position)*/
        dashDetailShared.select(sortedCountries[position], adapter.contentListFull)
        NavHostFragment.findNavController(this)
            .navigate(R.id.action_dashFragment_to_detailFragment)

    }

}
