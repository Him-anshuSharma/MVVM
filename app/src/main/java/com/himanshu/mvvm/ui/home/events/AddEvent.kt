package com.himanshu.mvvm.ui.home.events

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.himanshu.mvvm.R
import com.himanshu.mvvm.databinding.FragmentAddEventBinding
import com.himanshu.mvvm.util.DateTimePicker
import com.himanshu.mvvm.util.hide
import com.himanshu.mvvm.util.show
import com.himanshu.mvvm.util.snackbar
import org.kodein.di.DI
import org.kodein.di.DIAware
import org.kodein.di.android.x.closestDI
import org.kodein.di.instance

class AddEvent : Fragment(),DIAware,EventsListener {


    private var progressBar: ProgressBar? = null
    private var coordinatorLayout: CoordinatorLayout? = null

    private lateinit var dateTimePicker: DateTimePicker


    override val di: DI by closestDI()
    private val factory:EventViewModelFactory by instance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {


        val binding:FragmentAddEventBinding = DataBindingUtil.inflate(layoutInflater,R.layout.fragment_add_event,container,false)
        val viewModel = ViewModelProvider(this,factory)[EventViewModel::class.java]
        var isStartDateSelected:Boolean = true


        dateTimePicker = DateTimePicker(binding.root.context)

        binding.viewModel = viewModel
        viewModel.listener = this

        binding.lifecycleOwner = viewLifecycleOwner

        progressBar = binding.progressBarEvent
        coordinatorLayout = binding.addEventLayout

        binding.startDateTimeEditText.setOnClickListener {
            isStartDateSelected = true
            dateTimePicker.showDateTimePickerDialog()
        }
        binding.endDateTimeEditText.setOnClickListener {
            isStartDateSelected = false
            dateTimePicker.showDateTimePickerDialog()
        }

        dateTimePicker.setOnDateTimeSelectedListener(object :DateTimePicker.OnDateTimeSelectedListener{
            override fun onDateTimeSelected(dateTime: String?) {
                if(isStartDateSelected) {
                    binding.startDateTimeEditText.setText(dateTime)
                }
                else{
                    binding.endDateTimeEditText.setText(dateTime)
                }
            }
        })

        return binding.root
    }

    override fun onStarted() {
        if(progressBar!=null) {
            progressBar?.show()
        }
    }

    override fun onSuccess() {
        progressBar?.hide()
        coordinatorLayout?.snackbar("Event Created")
        this.findNavController().popBackStack()

    }

    override fun onFailure(message: String) {
        progressBar?.hide()
        coordinatorLayout?.snackbar(message)
        this.findNavController().popBackStack()
    }

}