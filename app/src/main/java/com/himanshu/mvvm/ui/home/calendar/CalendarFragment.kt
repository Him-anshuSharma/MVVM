package com.himanshu.mvvm.ui.home.calendar

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.himanshu.mvvm.R
import com.himanshu.mvvm.databinding.FragmentCalendarBinding

class CalendarFragment : Fragment() {


    private lateinit var viewModel: CalendarViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding: FragmentCalendarBinding = DataBindingUtil.inflate(layoutInflater,R.layout.fragment_calendar,container,false)
        viewModel = ViewModelProvider(this)[CalendarViewModel::class.java]

        binding.viewModel = viewModel

        return binding.root
    }
}