package com.task.tvmanager.ui.showlist.viewholder

import android.annotation.SuppressLint
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.task.tvmanager.FetchMoviesPaginatedQuery
import com.task.tvmanager.R
import com.task.tvmanager.utils.Utils

class ShowListItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val movieTitle = view.findViewById<TextView>(R.id.movieTitle)
    private val movieReleaseDate = view.findViewById<TextView>(R.id.movieReleaseDate)
    private val movieSeasons = view.findViewById<TextView>(R.id.movieSeasons)

    @SuppressLint("SetTextI18n")
    fun bind(movie: FetchMoviesPaginatedQuery.Edge?) {
        movie?.node?.apply {
            movieTitle.text = title
            movieReleaseDate.text = "Released on " + Utils.parse(releaseDate.toString())
            movieSeasons.text = seasons?.toInt().toString() + " seasons"
        }
    }
}