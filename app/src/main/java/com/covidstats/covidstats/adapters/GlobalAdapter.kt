package com.covidstats.covidstats.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.covidstats.covidstats.models.GlobalEntry
import com.covidstats.covidstats.utility.StringUtility
import com.covidstats.sovid.R
import kotlinx.android.synthetic.main.recyclerview_global_entry.view.*

class GlobalAdapter(
    private var contentList: ArrayList<GlobalEntry>,
    private val listener: OnItemClickListener
) :
    RecyclerView.Adapter<GlobalAdapter.GlobalViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GlobalViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.recyclerview_global_entry,
            parent, false
        )
        return GlobalViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: GlobalViewHolder, position: Int) {
        val currentItem = contentList[position]
        holder.textViewAttribute.text = currentItem.attribute
        holder.textViewNumber.text = currentItem.value
        holder.textViewNumber.setTextColor(-1)

        when (currentItem.state) {
            GlobalEntry.POSITIVE -> {
                holder.textViewNumber.setTextColor(
                    ContextCompat.getColor(
                        holder.textViewNumber.context,
                        R.color.colorRecyclerViewTextView_green
                    )
                )
            }
            GlobalEntry.NEGATIVE -> holder.textViewNumber.setTextColor(
                ContextCompat.getColor(
                    holder.textViewNumber.context,
                    R.color.colorRecyclerViewTextView_red
                )
            )
            else -> {
                holder.textViewNumber.setTextColor(
                    ContextCompat.getColor(
                        holder.textViewNumber.context,
                        R.color.colorRecyclerViewTextView
                    )
                )
            }
        }
        if (currentItem.addSepAbove) {
            holder.sep.visibility = View.VISIBLE
        } else holder.sep.visibility = View.GONE


    }

    override fun getItemCount() = contentList.size


    inner class GlobalViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {
        val textViewAttribute: TextView = itemView.attribute_textview_recyclerview
        val textViewNumber: TextView = itemView.number_textview_recyclerview
        val sep: View = itemView.sep

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