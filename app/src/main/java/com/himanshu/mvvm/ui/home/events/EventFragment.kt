package com.himanshu.mvvm.ui.home.events

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.himanshu.mvvm.R
import com.himanshu.mvvm.data.db.entities.Event
import com.himanshu.mvvm.data.models.EventsByDates
import com.himanshu.mvvm.databinding.FragmentEventBinding
import com.himanshu.mvvm.util.*
import org.kodein.di.DI
import org.kodein.di.DIAware
import org.kodein.di.android.x.closestDI
import org.kodein.di.instance

class EventFragment : Fragment(),DIAware{

    private lateinit var events:List<Event>
    private lateinit var viewModel: EventViewModel
    private lateinit var eventAdapter: EventAdapter
    override val di: DI by closestDI()
    private lateinit var binding:FragmentEventBinding
    private val factory:EventViewModelFactory by instance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val data = arguments?.getSerializable("date") as? EventsByDates
        events = data!!.eventsByDates
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = DataBindingUtil.inflate(layoutInflater,R.layout.fragment_event,container,false)
        viewModel = ViewModelProvider(this,factory)[EventViewModel::class.java]
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        binding.EventsRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        if(events!=null){
            eventAdapter = EventAdapter(events)
            binding.EventsRecyclerView.adapter = eventAdapter

        }
        (activity as AppCompatActivity?)!!.supportActionBar!!.show()
        return binding.root
    }


}