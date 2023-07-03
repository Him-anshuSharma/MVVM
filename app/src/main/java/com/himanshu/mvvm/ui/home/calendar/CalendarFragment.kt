package com.himanshu.mvvm.ui.home.calendar

import CalendarCellAdapter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.himanshu.mvvm.R
import com.himanshu.mvvm.databinding.FragmentCalendarBinding


class CalendarFragment : Fragment() {


    private lateinit var viewModel: CalendarViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val binding: FragmentCalendarBinding = DataBindingUtil.inflate(layoutInflater,R.layout.fragment_calendar,container,false)
        viewModel = ViewModelProvider(this)[CalendarViewModel::class.java]
        val list: MutableList<String> = mutableListOf()
        for(i in 1..30){
            list.add(i.toString())
        }
        binding.viewModel = viewModel
        val rv = binding.calendarRView
        rv.layoutManager = GridLayoutManager(this.context,10)
        rv.adapter = CalendarCellAdapter(list)
        return binding.root
    }
}