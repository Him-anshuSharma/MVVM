package com.himanshu.mvvm.ui.home.friends

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.himanshu.mvvm.R
import com.himanshu.mvvm.data.network.responses.Friend
import com.himanshu.mvvm.databinding.FragmentFriendDetailsBinding
import kotlinx.coroutines.launch
import org.kodein.di.DIAware
import org.kodein.di.DirectDI
import org.kodein.di.android.x.closestDI
import org.kodein.di.instance

class FriendDetails() : Fragment(),DIAware {

    private lateinit var binding:FragmentFriendDetailsBinding
    private lateinit var viewModel: FriendsViewModel
    override val di by closestDI()
    private var friend:Friend? = null

    val factory:FriendsViewModelFactory by instance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        friend = arguments?.getSerializable("friend") as Friend

        if(friend == null){
            Toast.makeText(context,"Some Error Occurred!",Toast.LENGTH_SHORT).show()
            findNavController().popBackStack()
        }

        binding = DataBindingUtil.inflate(layoutInflater,R.layout.fragment_friend_details,container,false)
        viewModel = ViewModelProvider(this,factory)[FriendsViewModel::class.java]
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.FriendDetailUsername.text = friend?.username
        binding.deleteFriendshipButton.setOnClickListener {
            this.lifecycleScope.launch {
                viewModel.removeFriend(friend!!)
                findNavController().popBackStack()
            }
        }
    }
}