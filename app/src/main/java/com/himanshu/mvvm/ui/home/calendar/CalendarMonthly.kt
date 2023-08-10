package com.himanshu.mvvm.ui.home.calendar

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.himanshu.mvvm.R
import com.himanshu.mvvm.data.db.entities.Event
import com.himanshu.mvvm.data.models.EventsByDates
import com.himanshu.mvvm.databinding.FragmentCalendarMonthlyBinding
import com.himanshu.mvvm.util.Coroutines
import com.himanshu.mvvm.util.snackbar
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.kodein.di.DI
import org.kodein.di.DIAware
import org.kodein.di.android.x.closestDI
import org.kodein.di.instance
import java.time.LocalDate


class CalendarMonthly : Fragment(), DIAware {

    private lateinit var viewModel: CalendarViewModel
    private lateinit var dayList: LiveData<List<String>>
    private var eventsByDate: HashMap<LocalDate, MutableList<Event>> = HashMap()

    override val di: DI by closestDI()
    private val factory: CalendarViewModelFactory by instance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding: FragmentCalendarMonthlyBinding =
            DataBindingUtil.inflate(layoutInflater, R.layout.fragment_calendar_monthly, container, false)

        viewModel = ViewModelProvider(requireActivity(), factory)[CalendarViewModel::class.java]

        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        (activity as AppCompatActivity?)!!.supportActionBar!!.hide()

        binding.calViewMonthly.setOnClickListener {
            findNavController().navigate(R.id.calendarThreeDays)
        }

        Coroutines.main {
            val events = viewModel.events.await()
            events.observe(viewLifecycleOwner) {

                eventsByDate = viewModel.getEventsByDate(events.value!!)
                binding.calendarRView.layoutManager = GridLayoutManager(this.context, 7)
                dayList = viewModel.getMonthListLiveData()
                dayList.observe(this.viewLifecycleOwner, Observer {
                    val adapter = CalendarAdapter(
                        dayList.value!!,
                        eventsByDate,
                        viewModel.getFirstDateOfMonth()
                    )
                    binding.calendarRView.adapter = adapter

                    adapter.setOnDayClickListener(object : CalendarAdapter.onDayClickListener {
                        override fun onDayClicked(position: String) {
                            val bundle: Bundle = Bundle()
                            if (!position.equals("")) {
                                try {
                                    val pos = position.toInt()
                                    val date = viewModel.getFirstDateOfMonth().plusDays((pos - 1).toLong())

                                    bundle.putString("date", "$date%")
                                    findNavController().navigate(R.id.eventFragment, bundle)

                                } catch (e: Exception) {
                                    Log.d("Error", e.message.toString())
                                }
                            }
                        }

                    })

                })
            }
        }
        return binding.root
    }
}