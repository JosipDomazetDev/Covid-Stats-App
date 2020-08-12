package com.covidstats.covidstats.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.covidstats.covidstats.models.Country
import com.covidstats.covidstats.models.SortCategory
import com.covidstats.sovid.R
import kotlinx.android.synthetic.main.recyclerview_country_entry.view.*
import java.util.*
import kotlin.collections.ArrayList
import kotlin.math.max

class NationalAdapter(
    private var contentList: ArrayList<Country>,
    private val listener: OnItemClickListener
) :
    RecyclerView.Adapter<NationalAdapter.NationalViewHolder>(), Filterable {

    var contentListFull: List<Country> = ArrayList(contentList)
    private var longestListLength: Int = 0


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NationalViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.recyclerview_country_entry,
            parent, false
        )
        return NationalViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: NationalViewHolder, position: Int) {
        val currentItem = contentList[position]
        holder.textViewPos.text = currentItem.getDisplayPlace(contentListFull)
        holder.textViewCountry.text = currentItem.displayName
        holder.textViewStat.text = currentItem.formattedDisplayNumber

        val circularProgressDrawable = CircularProgressDrawable(holder.imageView.context)
        circularProgressDrawable.strokeWidth = 5f
        circularProgressDrawable.centerRadius = 30f
        circularProgressDrawable.start()

        Glide.with(holder.imageView.context)
            .load(currentItem.imageURL)
            .centerCrop()
            .diskCacheStrategy(DiskCacheStrategy.DATA)
            .placeholder(circularProgressDrawable)
            .into(holder.imageView)

        val indexOfType = SortCategory.TYPES_WITH_STATE[0].indexOf(currentItem.currentSort.type)
        val state = SortCategory.TYPES_WITH_STATE[1][indexOfType]
        holder.textViewStat.setTextColor(-1)

        when (state) {
            SortCategory.POSITIVE -> {
                holder.textViewStat.setTextColor(
                    ContextCompat.getColor(
                        holder.textViewStat.context,
                        R.color.colorRecyclerViewTextView_green
                    )
                )
            }
            SortCategory.NEGATIVE -> holder.textViewStat.setTextColor(
                ContextCompat.getColor(
                    holder.textViewStat.context,
                    R.color.colorRecyclerViewTextView_red
                )
            )
            else -> {
                holder.textViewStat.setTextColor(
                    ContextCompat.getColor(
                        holder.textViewStat.context,
                        R.color.colorRecyclerViewTextView
                    )
                )
            }
        }
    }

    override fun getItemCount() = contentList.size

    override fun getFilter(): Filter {
        return countryFilter
    }

    fun notifyDataSetChangedWithList(contentListFull: List<Country>) {
        notifyDataSetChanged()
        if (contentListFull.size >= longestListLength) {
            // Original List
            this.contentListFull = contentListFull
            longestListLength = max(longestListLength, contentListFull.size)
        }
    }

    fun replaceList(newSortedCountries: List<Country>) {
        /*  contentList = newSortedCountries*/
    }


    private val countryFilter: Filter = object : Filter() {

        override fun performFiltering(searchMsg: CharSequence?): FilterResults? {
            val filteredList: MutableList<Country> = ArrayList()

            if (searchMsg == null || searchMsg.isEmpty()) {
                filteredList.addAll(contentListFull)
            } else {
                val searchMsgAsString = searchMsg.toString().toLowerCase(Locale.ROOT).trim()

                for (country in contentListFull) {
                    if (country.containsForSearch(searchMsgAsString)) {
                        filteredList.add(country)
                    }
                }
            }

            val results = FilterResults()
            results.values = filteredList
            return results
        }

        override fun publishResults(constraint: CharSequence?, results: FilterResults) {
            contentList.clear()
            contentList.addAll(results.values as Collection<Country>)
            notifyDataSetChanged()
        }


    }

    inner class NationalViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {
        val textViewPos: TextView = itemView.position_textview_recyclerview
        val textViewCountry: TextView = itemView.country_textview_recyclerview
        val textViewStat: TextView = itemView.stat_textview_recyclerview
        val imageView: ImageView = itemView.imageView_recyclerview

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            val position = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                listener.onItemClick(position)
            }
        }
    }

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }
}