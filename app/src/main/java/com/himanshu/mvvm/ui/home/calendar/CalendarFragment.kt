package com.himanshu.mvvm.ui.home.calendar

import android.os.Bundle
import android.util.Log
import android.view.*
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
import com.himanshu.mvvm.databinding.FragmentCalendarBinding
import com.himanshu.mvvm.util.Coroutines
import com.himanshu.mvvm.util.snackbar
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

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
    }

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
                Log.d("Events",events.value.toString())
                eventsByDate = viewModel.getEventsByDate(events.value!!)
                binding.calendarRView.layoutManager = GridLayoutManager(this.context, 7)
                dayList = viewModel.getMonthListLiveData()
                dayList.observe(this.viewLifecycleOwner, Observer {
                    val adapter = CalendarAdapter(dayList.value!!,eventsByDate,viewModel.getCurrDate())
                    binding.calendarRView.adapter = adapter
                    adapter.setOnDayClickListener(object :CalendarAdapter.onDayClickListener{
                        override fun onDayClicked(position: String) {
                            val bundle: Bundle = Bundle()
                            if(!position.equals("")){
                                try{
                                    val pos = position.toInt()
                                    val date = viewModel.getCurrDate().plusDays((pos-1).toLong())
                                    if(eventsByDate.containsKey(date)) {
                                        bundle.putSerializable("date", EventsByDates(eventsByDate[date]!!.toList()))
                                        findNavController().navigate(R.id.eventFragment, bundle)
                                    }
                                    else{
                                        binding.calendarRView.snackbar("No Events Scheduled")
                                    }
                                }catch (e:java.lang.Exception){
                                    Log.d("Error",e.message.toString())
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
