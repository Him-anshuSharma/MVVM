package com.himanshu.mvvm.ui.home.calendar

import CalendarCellAdapter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.himanshu.mvvm.R
import com.himanshu.mvvm.databinding.FragmentCalendarBinding


class CalendarFragment : Fragment() {


    private lateinit var viewModel: CalendarViewModel
    private lateinit var dayList: LiveData<List<String>>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val binding: FragmentCalendarBinding = DataBindingUtil.inflate(layoutInflater,R.layout.fragment_calendar,container,false)
        viewModel = ViewModelProvider(requireActivity())[CalendarViewModel::class.java]

        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        binding.calendarRView.layoutManager = GridLayoutManager(this.context,7)
        dayList = viewModel.getMonthListLiveData()
        dayList.observe(this.viewLifecycleOwner, Observer {
            binding.calendarRView.adapter = CalendarCellAdapter(dayList.value!!)

        })

        return binding.root
    }
}