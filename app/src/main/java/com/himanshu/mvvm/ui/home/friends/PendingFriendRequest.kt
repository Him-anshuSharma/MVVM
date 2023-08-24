package com.himanshu.mvvm.ui.home.friends

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.himanshu.mvvm.R
import com.himanshu.mvvm.data.db.entities.User
import com.himanshu.mvvm.data.network.responses.PendingFriendRequest
import kotlinx.coroutines.launch
import org.kodein.di.DI
import org.kodein.di.DIAware
import org.kodein.di.android.x.closestDI
import org.kodein.di.instance


class PendingFriendRequest : Fragment(), DIAware {

    override val di: DI by closestDI()

    private val factory:FriendsViewModelFactory by instance()

    private lateinit var listener: PendingFriendListListener
    private lateinit var recyclerView: RecyclerView
    private lateinit var user: LiveData<User>
    private lateinit var viewModel: FriendsViewModel
    private lateinit var adapter: PendingFriendsAdapter
    private lateinit var requests: MutableList<PendingFriendRequest>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        viewModel = ViewModelProvider(this,factory)[FriendsViewModel::class.java]
        user = viewModel.user
        requests = mutableListOf()
        adapter = PendingFriendsAdapter(requests)
        listener = object : PendingFriendListListener{
            override fun onClick(acceptStatus: Boolean, position: Int) {
                lifecycleScope.launch {
                    val response = if(acceptStatus){
                        viewModel.acceptFriendRequest(requests[position]._id)
                    }
                    else{
                        viewModel.rejectFriendRequest(requests[position]._id)
                    }
                    showResponseToUser(response.message)
                    removeFriendFromPendingRequests(position)
                }
            }
        }
        adapter.setListener(listener)
        return inflater.inflate(R.layout.fragment_pending_friend_request, container, false)
    }

    private fun removeFriendFromPendingRequests(position: Int) {
        requests.removeAt(position)
        adapter.notifyItemChanged(position)
    }

    private fun showResponseToUser(message: String) {
        if(message == "Friend request accepted" || message == "Friend request rejected"){
            Toast.makeText(context,message,Toast.LENGTH_SHORT).show()
        }
        else{
            Toast.makeText(context,"Some Error Occurred",Toast.LENGTH_SHORT).show()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById(R.id.pendingFriendRequestRV)
        recyclerView.layoutManager = GridLayoutManager(context,2)
        recyclerView.adapter = adapter

        user.observeForever {
            if(it == null) return@observeForever
            lifecycleScope.launch {
                requests.clear()
                requests.addAll(viewModel.getPendingFriendRequests(it.id!!).pendingRequests)
                adapter.updateDate(requests)
            }
        }
    }

}