package com.himanshu.mvvm.ui.home.friends

import com.himanshu.mvvm.data.network.responses.Friend

interface FriendsListClickListener {
    fun onClick(friend: Friend)
}