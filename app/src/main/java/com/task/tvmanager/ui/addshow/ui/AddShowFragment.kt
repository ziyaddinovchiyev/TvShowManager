package com.task.tvmanager.ui.addshow.ui

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.apollographql.apollo.api.Input
import com.task.tvmanager.R
import com.task.tvmanager.type.CreateMovieFieldsInput
import com.task.tvmanager.type.CreateMovieInput
import com.task.tvmanager.ui.addshow.viewmodel.AddShowViewModel
import com.task.tvmanager.utils.Resource
import dagger.hilt.android.AndroidEntryPoint
import java.util.*


@AndroidEntryPoint
class AddShowFragment : Fragment(R.layout.fragment_add_show), View.OnClickListener {

    private val viewModel: AddShowViewModel by viewModels()

    private lateinit var inputTitle: EditText
    private lateinit var inputReleaseDate: EditText
    private lateinit var inputSeasons: EditText
    private lateinit var addMovie: Button
    private lateinit var releaseDatePicker: Button

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI(view)
        setupObservers()
    }

    private fun setupUI(view: View) {
        inputTitle = view.findViewById(R.id.inputTitle)
        inputReleaseDate = view.findViewById(R.id.inputReleaseDate)
        inputSeasons = view.findViewById(R.id.inputSeasons)
        addMovie = view.findViewById(R.id.addMovie)
        releaseDatePicker = view.findViewById(R.id.releaseDatePicker)
        addMovie.setOnClickListener(this)
        releaseDatePicker.setOnClickListener(this)
    }

    private fun addMovieClick() {
        viewModel.addMovie(
            CreateMovieInput(
                fields = Input.fromNullable(
                    CreateMovieFieldsInput(
                        title = inputTitle.text.toString(),
                        seasons = Input.fromNullable(inputSeasons.text.toString().toDouble()),
                        releaseDate = Input.fromNullable(inputReleaseDate.text.toString())
                    )
                )
            )
        )
    }


    @SuppressLint("SetTextI18n")
    private fun showDatePicker() {
        val datePickerDialog = DatePickerDialog(requireContext(),
            { _, year, monthOfYear, dayOfMonth -> inputReleaseDate.setText(year.toString() + "-" + (monthOfYear + 1) + "-" + dayOfMonth.toString()) },
            Calendar.getInstance().get(Calendar.YEAR),
            Calendar.getInstance().get(Calendar.MONTH),
            Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
        )
        datePickerDialog.show()
    }

    private fun setupObservers() {
        viewModel.addMovieResult.observe(viewLifecycleOwner, {
            when (it.status) {
                Resource.Status.LOADING -> {
                    Toast.makeText(requireContext(), "Please wait...", Toast.LENGTH_SHORT).show()
                }
                Resource.Status.SUCCESS -> {
                    Toast.makeText(requireContext(), "Movie added successfully!", Toast.LENGTH_LONG).show()
                    findNavController().navigateUp()
                }
                Resource.Status.ERROR -> {
                    Toast.makeText(requireContext(), "Failed to add movie.", Toast.LENGTH_LONG).show()
                }
            }
        })
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.addMovie -> addMovieClick()
            R.id.releaseDatePicker -> showDatePicker()
        }
    }
}