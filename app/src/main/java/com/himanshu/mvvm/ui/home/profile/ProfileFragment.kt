package com.himanshu.mvvm.ui.home.profile

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.provider.ContactsContract.Profile
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.himanshu.mvvm.R
import com.himanshu.mvvm.databinding.FragmentProfileBinding
import org.kodein.di.DI
import org.kodein.di.DIAware
import org.kodein.di.android.x.closestDI
import org.kodein.di.instance

class ProfileFragment : Fragment(),DIAware {

    override val di: DI by closestDI()
    private val factory:ProfileViewModelFactory by instance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding:FragmentProfileBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_profile,container,false)
        val viewModel = ViewModelProvider(this,factory).get(ProfileViewModel::class.java)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
//        (activity as AppCompatActivity?)!!.supportActionBar!!.show()
        return binding.root
    }
}