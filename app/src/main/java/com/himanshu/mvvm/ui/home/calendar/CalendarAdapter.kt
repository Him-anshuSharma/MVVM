package com.himanshu.mvvm.ui.home.calendar

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.himanshu.mvvm.R
import com.himanshu.mvvm.data.db.entities.Event
import java.time.LocalDate

class CalendarAdapter(private val data: List<String>,private val eventsByDate: HashMap<LocalDate,MutableList<Event>>, private val currDate:LocalDate) : RecyclerView.Adapter<CalendarAdapter.CustomViewHolder>() {


    private lateinit var mDayClickListener: onDayClickListener

    interface onDayClickListener{
        fun onDayClicked(position: String)
    }

    fun setOnDayClickListener(listener: onDayClickListener){
        mDayClickListener = listener
    }


    class CustomViewHolder(itemView: View,listener:onDayClickListener) : RecyclerView.ViewHolder(itemView) {
        val date: TextView = itemView.findViewById(R.id.currentMonthDate)
        val eventsRV: RecyclerView = itemView.findViewById(R.id.calendarEventsRV)

        init {
            itemView.setOnClickListener {
                listener.onDayClicked(date.text.toString())
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val itemView = inflater.inflate(R.layout.calendar_cell, parent, false)
        return CustomViewHolder(itemView,mDayClickListener)
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        val events: MutableList<String> = mutableListOf()
        holder.date.text = data[position]
        if(data[position] != "" && Character.isDigit(data[position][0])){
            val date = currDate.plusDays((Integer.valueOf(data[position])-1).toLong())
            if(eventsByDate.containsKey(date)){
                for(event in eventsByDate[date]!!){
                    events.add(event.title)
                }
            }
        }
        Log.d("date", data[position])
        Log.d("events",events.toString())
        val adapter = CalendarCellAdapter(events)
        holder.eventsRV.layoutManager = LinearLayoutManager(holder.eventsRV.context,LinearLayoutManager.HORIZONTAL,false)
        holder.eventsRV.adapter = adapter
    }

    override fun getItemCount(): Int {
        return data.size
    }
}
