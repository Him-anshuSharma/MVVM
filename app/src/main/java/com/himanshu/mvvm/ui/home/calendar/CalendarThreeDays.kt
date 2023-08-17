package com.himanshu.mvvm.ui.home.calendar

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.himanshu.mvvm.R
import com.himanshu.mvvm.data.db.entities.Event
import com.himanshu.mvvm.data.models.EventsByDates
import com.himanshu.mvvm.databinding.FragmentCalendarThreeDaysBinding
import com.himanshu.mvvm.util.Coroutines
import com.himanshu.mvvm.util.snackbar
import org.kodein.di.DI
import org.kodein.di.DIAware
import org.kodein.di.android.x.closestDI
import org.kodein.di.instance
import org.w3c.dom.Text
import java.time.LocalDate
import kotlin.math.abs

class CalendarThreeDays : Fragment(), DIAware {

    private lateinit var viewModel: CalendarViewModel
    private lateinit var dayTextViews: Array<TextView?>

    override val di: DI by closestDI()
    private val factory: CalendarViewModelFactory by instance()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {


        val binding: FragmentCalendarThreeDaysBinding =
            DataBindingUtil.inflate(layoutInflater, R.layout.fragment_calendar_three_days, container, false)

        (activity as AppCompatActivity?)!!.supportActionBar!!.hide()

        viewModel = ViewModelProvider(requireActivity(), factory)[CalendarViewModel::class.java]


        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        dayTextViews = arrayOf(
            binding.day1,
            binding.day2,
            binding.day3,
            binding.day4,
            binding.day5,
            binding.day6,
            binding.day7
        )


        val swipeListener  = SwipeListener(binding,dayTextViews,viewModel)

        for (textView in dayTextViews) {
            textView?.setOnTouchListener(swipeListener)
        }

        binding.calViewWeekly.setOnClickListener {
            findNavController().popBackStack()
        }

        val list = viewModel.getDatesList()
        for(i in 0..6){
            dayTextViews[i]?.text = list[i].toString()
        }

        for(tv in dayTextViews){
            if(tv?.text.toString() == viewModel.getCurrDate().dayOfMonth.toString()){
                tv?.setTextColor(Color.WHITE)
                tv?.setBackgroundColor(Color.BLACK)
            }
            else{
                tv?.setTextColor(Color.GRAY)
                tv?.setBackgroundColor(Color.TRANSPARENT)
            }
        }

        Coroutines.main {
            val events = viewModel.events.await()
            events.observe(viewLifecycleOwner,Observer{
                viewModel.getEventsByDate(events = events!!.value!!)
                binding.calendarThreeDaysRV.adapter = CalendarDayEventAdapter(
                    viewModel.getDayEvents(
                        viewModel.getCurrDate()
                    )
                )
            })
        }
        val events = viewModel.getDayEvents(viewModel.getCurrDate())
        Log.d("TEST",events.toString())
        binding.calendarThreeDaysNoEvents.visibility =
            if (events.isEmpty()) View.VISIBLE else View.GONE
        binding.calendarThreeDaysRV.layoutManager = LinearLayoutManager(context)
        binding.calendarThreeDaysRV.adapter = CalendarDayEventAdapter(
            viewModel.getDayEvents(
                viewModel.getCurrDate()
            )
        )

        return binding.root
    }


    class SwipeListener(private val binding: FragmentCalendarThreeDaysBinding,private val textViews: Array<TextView?>, val viewModel: CalendarViewModel) : View.OnTouchListener {
        private var startX = 0f
        private var startY = 0f

        override fun onTouch(v: View?, event: MotionEvent?): Boolean {
            when (event?.action) {
                MotionEvent.ACTION_DOWN -> {
                    startX = event.x
                    startY = event.y
                }
                MotionEvent.ACTION_UP -> {
                    val endX = event.x
                    val endY = event.y
                    val deltaX = endX - startX
                    val deltaY = endY - startY

                    if (abs(deltaX) > abs(deltaY)) {
                        // Horizontal swipe detected
                        if (deltaX > 0) {
                            viewModel.minusWeek()
                            val list = viewModel.getDatesList()
                            for(i in 0..6){
                                textViews[i]?.text = list[i].toString()
                            }
                        } else {
                            viewModel.addWeek()
                            val list = viewModel.getDatesList()
                            for(i in 0..6){
                                textViews[i]?.text = list[i].toString()
                            }
                        }
                    }
                    else{
                        val textView = v as TextView
                        for(tv in textViews){
                            if(tv?.text.toString() == textView.text.toString()){
                                tv?.setTextColor(Color.WHITE)
                                tv?.setBackgroundColor(Color.BLACK)
                            }
                            else{
                                tv?.setTextColor(Color.GRAY)
                                tv?.setBackgroundColor(Color.TRANSPARENT)
                            }
                        }

                        val events = viewModel.getDayEvents(LocalDate.of(viewModel.getCurrDate().year,viewModel.getCurrDate().month,textView.text.toString().toInt()))
                        Log.d("TEST",events.toString())
                        binding.calendarThreeDaysNoEvents.visibility =
                            if (events.isEmpty()) View.VISIBLE else View.GONE
                        binding.calendarThreeDaysRV.adapter = CalendarDayEventAdapter(
                            viewModel.getDayEvents(
                                LocalDate.of(viewModel.getCurrDate().year,viewModel.getCurrDate().month,textView.text.toString().toInt())
                            )
                        )

                    }
                }
            }
            return true
        }
    }
}
