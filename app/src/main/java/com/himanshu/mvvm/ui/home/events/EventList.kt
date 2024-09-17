package com.himanshu.mvvm.ui.home.events

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.himanshu.mvvm.R
import com.himanshu.mvvm.data.db.entities.Event
import com.himanshu.mvvm.databinding.FragmentEventListBinding
import org.kodein.di.DI
import org.kodein.di.DIAware
import org.kodein.di.android.x.closestDI
import org.kodein.di.instance

class EventList : Fragment(), DIAware {

    private lateinit var events: LiveData<List<Event>>
    private lateinit var viewModel: EventViewModel
    private lateinit var eventListAdapter: EventListAdapter
    override val di: DI by closestDI()
    private lateinit var date:String
    private lateinit var binding: FragmentEventListBinding
    private val factory: EventViewModelFactory by instance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        date = arguments?.getString("date").toString()
        viewModel = ViewModelProvider(this, factory)[EventViewModel::class.java]
        events = viewModel.getEventByDate(date)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        events.observeForever {
            eventListAdapter = EventListAdapter(it)
            eventListAdapter.setOnDayClickListener(
                object : EventListAdapter.EventOnClickListener {
                    override fun onClick(event: Event) {
                        val bundle = Bundle()
                        bundle.putSerializable("Event", event)
                        findNavController().navigate(R.id.eventDetailFragment, bundle)
                    }

                }
            )
            binding.EventsRecyclerView.adapter = eventListAdapter
        }
        binding =
            DataBindingUtil.inflate(layoutInflater, R.layout.fragment_event_list, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        binding.EventsRecyclerView.layoutManager = LinearLayoutManager(requireContext())

        eventListAdapter = if (events.value != null) {
            EventListAdapter(events.value!!)
        } else {
            EventListAdapter(listOf())
        }
        eventListAdapter.setOnDayClickListener(
            object : EventListAdapter.EventOnClickListener {
                override fun onClick(event: Event) {
                    val bundle = Bundle()
                    bundle.putSerializable("Event", event)
                    findNavController().navigate(R.id.eventDetailFragment, bundle)
                }

            }
        )
        binding.EventsRecyclerView.adapter = eventListAdapter
//        (activity as AppCompatActivity?)!!.supportActionBar!!.show()
        return binding.root
    }
}