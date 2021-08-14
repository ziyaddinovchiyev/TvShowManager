package com.task.tvmanager.ui.dashboard

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Button
import androidx.navigation.fragment.findNavController
import com.task.tvmanager.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DashboardFragment : Fragment(R.layout.fragment_dashboard) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<Button>(R.id.gotoAddShowFragment).setOnClickListener {
            findNavController().navigate(DashboardFragmentDirections.actionDashboardFragmentToAddShowFragment())
        }

        view.findViewById<Button>(R.id.gotoShowListFragment).setOnClickListener {
            findNavController().navigate(DashboardFragmentDirections.actionDashboardFragmentToShowListFragment())
        }

    }
}