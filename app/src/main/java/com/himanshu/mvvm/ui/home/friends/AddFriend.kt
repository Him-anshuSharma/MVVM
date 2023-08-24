package com.himanshu.mvvm.ui.home.friends

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.himanshu.mvvm.R
import com.himanshu.mvvm.data.db.entities.User
import com.himanshu.mvvm.databinding.FragmentAddFriendBinding
import kotlinx.coroutines.launch
import org.kodein.di.DI
import org.kodein.di.DIAware
import org.kodein.di.android.x.closestDI
import org.kodein.di.instance

class AddFriend : Fragment(), DIAware {

    override val di: DI by closestDI()

    val factory:FriendsViewModelFactory by instance()
    private lateinit var viewModel: FriendsViewModel
    var pendingFriendRequest = false
    private lateinit var binding: FragmentAddFriendBinding
    private lateinit var user: LiveData<User>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(layoutInflater,R.layout.fragment_add_friend,container,false)
        viewModel = ViewModelProvider(this,factory)[FriendsViewModel::class.java]
        binding.viewModel = viewModel
        user = viewModel.user
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        user.observeForever {
            if(pendingFriendRequest){
                lifecycleScope.launch {
                    val message = viewModel.addFriend(user.value!!.id!!)
                    pendingFriendRequest = false
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(context,message,Toast.LENGTH_SHORT).show()
                }
            }
        }
        binding.SendFriendRequestButton.setOnClickListener {
            binding.progressBar.visibility = View.VISIBLE
            if(user.value == null){
                pendingFriendRequest = true
                return@setOnClickListener
            }
            else{
                lifecycleScope.launch {
                    val message = viewModel.addFriend(user.value!!.id!!)
                    pendingFriendRequest = false
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(context,message,Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}