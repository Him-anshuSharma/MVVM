package com.himanshu.mvvm.ui.home.events

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.himanshu.mvvm.R
import com.himanshu.mvvm.data.db.entities.Event
import com.himanshu.mvvm.databinding.FragmentEventDetailBinding
import com.himanshu.mvvm.util.Coroutines
import com.himanshu.mvvm.util.snackbar
import kotlinx.coroutines.launch
import org.kodein.di.DI
import org.kodein.di.DIAware
import org.kodein.di.android.x.closestDI
import org.kodein.di.instance

class EventDetailFragment() : Fragment(),DIAware {

    override val di: DI by closestDI()
    private lateinit var viewModel: EventViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (requireActivity() as AppCompatActivity).supportActionBar?.hide()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val binding: FragmentEventDetailBinding = DataBindingUtil.inflate(layoutInflater,R.layout.fragment_event_detail,container,false)
        val factory:EventViewModelFactory by instance()

        viewModel = ViewModelProvider(requireActivity(),factory)[EventViewModel::class.java]

        val event = arguments?.getSerializable("Event") as Event

        binding.descriptionTextView.text = event.description
        binding.endDateTextView.text = event.endDateTime.toString()
        binding.locationTextView.text = event.location
        binding.titleTextView.text = event.title
        binding.startDateTextView.text = event.startDateTime

        binding.toolbar.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.action_delete -> {
                    Coroutines.main {
                        if(viewModel.deleteEvent(event)){
                            deleteFromCache(event)
                            binding.toolbar.snackbar("Event Deleted")
                            findNavController().popBackStack()
                        }
                        else{
                            binding.toolbar.snackbar("Event Deletion Failed")
                        }
                    }// Implement delete event action
                    true
                }
                else -> false
            }
        }

        return binding.root
    }

    private fun deleteFromCache(event: Event) {
        Coroutines.IO {
            viewModel.deleteEventFromCache(event)
        }

    }
}