package com.himanshu.mvvm.ui.home.calendar

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.himanshu.mvvm.R

class CalendarCellAdapter(private val dayEventsList: List<String>?): RecyclerView.Adapter<CalendarCellAdapter.CustomViewHolder>() {
    class CustomViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val itemView = inflater.inflate(R.layout.calendar_day_event_layout, parent, false)
        return CustomViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
    }

    override fun getItemCount(): Int{
        return dayEventsList?.size ?: 0
    }
}