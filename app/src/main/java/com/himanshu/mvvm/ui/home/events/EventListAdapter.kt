package com.himanshu.mvvm.ui.home.events

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.himanshu.mvvm.R
import com.himanshu.mvvm.data.db.entities.Event
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter

class EventListAdapter(private val events: List<Event>) : RecyclerView.Adapter<EventListAdapter.EventViewHolder>() {

    private lateinit var mListener:EventOnClickListener

    interface EventOnClickListener{
        fun onClick(event: Event)
    }

    fun setOnDayClickListener(listener: EventOnClickListener){
        mListener = listener
    }

    class EventViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleTextView: TextView = itemView.findViewById(R.id.titleTextView)
        val descriptionTextView: TextView = itemView.findViewById(R.id.descriptionTextView)
        val datesTextView: TextView = itemView.findViewById(R.id.datesTextView)
        val locationTextView: TextView = itemView.findViewById(R.id.locationTextView)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.event, parent, false)
        return EventViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        val event = events[position]
        val formatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME
        val offsetDateTime = OffsetDateTime.parse(event.startDateTime, formatter)
        val date = offsetDateTime.toLocalDate()
        val time = offsetDateTime.toLocalTime()
        holder.titleTextView.text = event.title
        holder.descriptionTextView.text = event.description
        holder.datesTextView.text = "Start: ${date} ${time}"
        holder.locationTextView.text = event.location

        holder.itemView.setOnClickListener {
            mListener.onClick(events[position])
        }

    }

    override fun getItemCount(): Int {
        return events.size
    }
}
