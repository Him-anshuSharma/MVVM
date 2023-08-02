package com.himanshu.mvvm.ui.home.calendar

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
import com.himanshu.mvvm.data.db.entities.Event
import com.himanshu.mvvm.databinding.FragmentCalendarBinding
import com.himanshu.mvvm.util.Coroutines
import org.kodein.di.DI
import org.kodein.di.DIAware
import org.kodein.di.android.x.closestDI
import org.kodein.di.instance
import java.time.LocalDate


class CalendarFragment : Fragment(), DIAware {


    private lateinit var viewModel: CalendarViewModel
    private lateinit var dayList: LiveData<List<String>>
    private var eventsByDate: HashMap<LocalDate, MutableList<Event>> = HashMap()


    override val di: DI by closestDI()
    private val factory: CalendarViewModelFactory by instance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val binding: FragmentCalendarBinding =
            DataBindingUtil.inflate(layoutInflater, R.layout.fragment_calendar, container, false)
        viewModel = ViewModelProvider(requireActivity(), factory)[CalendarViewModel::class.java]

        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        Coroutines.main {
            val events = viewModel.events.await()
            events.observe(viewLifecycleOwner) {
                eventsByDate = viewModel.getEventsByDate(events.value!!)
            }
        }
        binding.calendarRView.layoutManager = GridLayoutManager(this.context, 7)
        dayList = viewModel.getMonthListLiveData()
        dayList.observe(this.viewLifecycleOwner, Observer {
            binding.calendarRView.adapter = CalendarAdapter(dayList.value!!)
        })


        return binding.root
    }
}
