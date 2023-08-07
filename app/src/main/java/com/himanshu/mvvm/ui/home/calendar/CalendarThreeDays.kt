package com.himanshu.mvvm.ui.home.calendar

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.himanshu.mvvm.R
import com.himanshu.mvvm.databinding.FragmentCalendarThreeDaysBinding
import org.kodein.di.DI
import org.kodein.di.DIAware
import org.kodein.di.android.x.closestDI
import org.kodein.di.instance
import kotlin.math.abs

class CalendarThreeDays : Fragment(), DIAware {

    private lateinit var viewModel: CalendarViewModel
    private lateinit var dayTextViews: Array<TextView?>

    override val di: DI by closestDI()
    private val factory: CalendarViewModelFactory by instance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding: FragmentCalendarThreeDaysBinding =
            DataBindingUtil.inflate(
                layoutInflater,
                R.layout.fragment_calendar_three_days,
                container,
                false
            )

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
        for (textView in dayTextViews) {
            textView?.isFocusable = false
        }
        val swipeListener  = SwipeListener(dayTextViews,viewModel)
        binding.dayList.setOnTouchListener(swipeListener)

        val list = viewModel.getDatesList()
        for(i in 0..6){
            dayTextViews[i]?.text = list[i].toString()
        }

        return binding.root
    }
    class SwipeListener(private val textViews: Array<TextView?>, val viewModel: CalendarViewModel) : View.OnTouchListener {
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
                }
            }
            return true
        }
    }
}
