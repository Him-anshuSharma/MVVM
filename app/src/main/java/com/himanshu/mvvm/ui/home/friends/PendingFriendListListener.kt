package com.himanshu.mvvm.ui.home.friends

interface PendingFriendListListener {
    fun onClick(acceptStatus: Boolean, position: Int)
}