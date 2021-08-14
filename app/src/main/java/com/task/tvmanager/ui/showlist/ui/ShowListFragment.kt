package com.task.tvmanager.ui.showlist.ui

import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.android.material.snackbar.Snackbar
import com.task.tvmanager.FetchMoviesPaginatedQuery
import com.task.tvmanager.R
import com.task.tvmanager.ui.showlist.adapter.ShowListAdapter
import com.task.tvmanager.ui.showlist.viewmodel.ShowListViewModel
import com.task.tvmanager.utils.Resource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ShowListFragment : Fragment(R.layout.fragment_show_list), SwipeRefreshLayout.OnRefreshListener {

    private val viewModel: ShowListViewModel by viewModels()
    private val adapter = ShowListAdapter()

    private lateinit var swipeRefresh: SwipeRefreshLayout
    private lateinit var recyclerView : RecyclerView
    private lateinit var errorLabel: TextView
    private lateinit var loadingIndicator: ProgressBar

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI(view)
        setupObservers()
    }

    private fun setupUI(view: View) {
        loadingIndicator = view.findViewById(R.id.loadingIndicator)
        errorLabel = view.findViewById(R.id.errorLabel)
        swipeRefresh = view.findViewById(R.id.swipeRefresh)
        swipeRefresh.setOnRefreshListener(this)
        recyclerView = view.findViewById(R.id.showListRV)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (!recyclerView.canScrollVertically(1)) {
                    viewModel.fetch()
                }
            }
        })
    }

    private fun setupObservers() {
        viewModel.paginatedMovies.observe(viewLifecycleOwner, { result ->
            when(result.status) {
                Resource.Status.LOADING -> {
                    if (adapter.itemCount > 0) {
                        // Loading more
                        uiVisibilityState(list = View.VISIBLE, load = View.GONE, err = View.GONE)
                    } else {
                        // Initial load
                        uiVisibilityState(list = View.GONE, load = View.VISIBLE, err = View.GONE)
                    }
                }
                Resource.Status.SUCCESS -> {
                    val movies = result.data?.movies?.edges
                    adapter.updateData(ArrayList(movies!!))
                    uiVisibilityState(list = View.VISIBLE, load = View.GONE, err = View.GONE)
                    setPageInfo(result.data.movies)
                }
                Resource.Status.ERROR -> {
                    if (adapter.itemCount > 0) {
                        // Loading more
                        uiVisibilityState(list = View.VISIBLE, load = View.GONE, err = View.GONE)
                        Snackbar.make(requireView(), "Failed to load more", Snackbar.LENGTH_LONG).show()
                    } else {
                        // Initial load
                        uiVisibilityState(list = View.GONE, load = View.GONE, err = View.VISIBLE)
                    }
                }
            }
            if (swipeRefresh.isRefreshing) swipeRefresh.isRefreshing = false
        })

        viewModel.hasNext.observe(viewLifecycleOwner, {
            swipeRefresh.isEnabled = it
        })

    }

    /**
     * Setting next page info for load more
     */
    private fun setPageInfo(info : FetchMoviesPaginatedQuery.Movies) {
        info.pageInfo.apply {
            viewModel.hasNext.value = hasNextPage
            viewModel.endCursor.value = endCursor
        }
    }

    /**
     * callback of swipeRefresh
     * clearing values of hasNext and endCursor
     *  to make the fetch request to load the initial data
     */
    override fun onRefresh() {
        viewModel.hasNext.value = true
        viewModel.endCursor.value = ""
        viewModel.fetch()
    }

    private fun uiVisibilityState(list: Int, load: Int, err: Int) {
        recyclerView.visibility = list
        loadingIndicator.visibility = load
        errorLabel.visibility = err
    }
}