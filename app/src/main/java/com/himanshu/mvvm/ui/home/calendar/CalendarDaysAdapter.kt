package com.himanshu.mvvm.ui.home.calendar

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.himanshu.mvvm.R

class CalendarDaysAdapter(val data: List<Int>): RecyclerView.Adapter<CalendarDaysAdapter.CustomViewHolder>() {



    class CustomViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val date: TextView = itemView.findViewById(R.id.currentDate)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val itemView = inflater.inflate(R.layout.calendar_date_cell_weekly, parent, false)
        return CustomViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        holder.date.text = data[position].toString()
    }

    override fun getItemCount(): Int {
        return data.size
    }
}