import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.himanshu.mvvm.R

class CalendarAdapter(private val data: List<String>) : RecyclerView.Adapter<CalendarAdapter.CustomViewHolder>() {

    // ViewHolder class
    class CustomViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // Define your views here
        // Example: private val textView: TextView = itemView.findViewById(R.id.textView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val itemView = inflater.inflate(R.layout.calendar_cell, parent, false)
        return CustomViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        // Bind data to views inside the ViewHolder
        // Example: holder.textView.text = data[position]
    }

    override fun getItemCount(): Int {
        return data.size
    }
}
