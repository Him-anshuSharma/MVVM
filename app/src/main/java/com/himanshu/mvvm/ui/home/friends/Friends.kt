package com.himanshu.mvvm.ui.home.friends

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.himanshu.mvvm.R
import com.himanshu.mvvm.data.db.entities.User
import com.himanshu.mvvm.data.network.responses.Friend
import com.himanshu.mvvm.util.Coroutines
import org.kodein.di.DI
import org.kodein.di.DIAware
import org.kodein.di.android.x.closestDI
import org.kodein.di.instance

class Friends : Fragment(), DIAware {

    override val di: DI by closestDI()
    private val factory: FriendsViewModelFactory by instance()
    private lateinit var user: LiveData<User>
    private lateinit var friendList: LiveData<List<Friend>>
    private lateinit var viewModel: FriendsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_friends, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this, factory)[FriendsViewModel::class.java]

        val friendsRecyclerView = view.findViewById<RecyclerView>(R.id.friendsRecyclerView)
        friendsRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        val adapter = FriendListAdapter(listOf())
        val listener = object : FriendsListClickListener {
            override fun onClick(friend: Friend) {
                val bundle = Bundle()
                bundle.putSerializable("friend",friend)
                findNavController().navigate(R.id.friendDetails,bundle)
            }
        }
        adapter.setListener(listener)
        friendsRecyclerView.adapter = adapter

        (activity as AppCompatActivity?)!!.supportActionBar!!.hide()

        user = viewModel.user
        friendList = viewModel.friends

        user.observeForever {
            if (it.id != null) {
                Coroutines.main {
                    viewModel.getFriendsList(it.id!!)
                }
            }
        }

        friendList.observeForever {
            adapter.updateFriendsList(it)
        }
    }
}
