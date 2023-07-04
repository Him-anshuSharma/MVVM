import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.himanshu.mvvm.R

class CalendarAdapter(private val data: List<String>) : RecyclerView.Adapter<CalendarAdapter.CustomViewHolder>() {


    class CustomViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val date: TextView = itemView.findViewById(R.id.currentMonthDate)
        val eventsRV: RecyclerView = itemView.findViewById(R.id.calendarEventsRV)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val itemView = inflater.inflate(R.layout.calendar_cell, parent, false)
        return CustomViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        holder.itemView.setOnClickListener {
            Toast.makeText(it.context,data[position],Toast.LENGTH_SHORT).show()
        }
        holder.date.text = data[position]
        holder.eventsRV.adapter
    }

    override fun getItemCount(): Int {
        return data.size
    }
}