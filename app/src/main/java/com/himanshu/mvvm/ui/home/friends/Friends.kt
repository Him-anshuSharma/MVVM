package com.himanshu.mvvm.ui.home.friends

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.himanshu.mvvm.R
import com.himanshu.mvvm.data.db.entities.User
import com.himanshu.mvvm.data.network.responses.Friend
import com.himanshu.mvvm.util.Coroutines
import kotlinx.coroutines.launch
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
        friendsRecyclerView.adapter = FriendListAdapter(listOf())

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
            friendsRecyclerView.adapter = FriendListAdapter(it)
        }
    }
}
