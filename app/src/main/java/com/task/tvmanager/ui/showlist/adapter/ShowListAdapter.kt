package com.task.tvmanager.ui.showlist.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.task.tvmanager.FetchMoviesPaginatedQuery
import com.task.tvmanager.R
import com.task.tvmanager.ui.showlist.viewholder.ShowListItemViewHolder

class ShowListAdapter : RecyclerView.Adapter<ShowListItemViewHolder>() {

    private var items = ArrayList<FetchMoviesPaginatedQuery.Edge?>()

    fun updateData(update : ArrayList<FetchMoviesPaginatedQuery.Edge?>) {
        items.addAll(update)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShowListItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_showlist, parent, false)
        return ShowListItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ShowListItemViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount() = items.size
}