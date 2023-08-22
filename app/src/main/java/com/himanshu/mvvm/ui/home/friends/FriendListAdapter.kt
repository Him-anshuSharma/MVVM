package com.himanshu.mvvm.ui.home.friends

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.himanshu.mvvm.R
import com.himanshu.mvvm.data.network.responses.Friend
import org.w3c.dom.Text

class FriendListAdapter(private var friendsList: List<Friend>):RecyclerView.Adapter<FriendListAdapter.FriendViewHolder>() {
    class FriendViewHolder(itemView:View) : RecyclerView.ViewHolder(itemView) {
        val username: TextView = itemView.findViewById(R.id.FriendUsername)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FriendViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.friend_tile, parent, false)
        return FriendViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: FriendViewHolder, position: Int) {
        holder.username.text = friendsList[position].username
    }

    fun updateFriendsList(updatedFriendsList: List<Friend>){
        friendsList = updatedFriendsList
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {

        return friendsList.size
    }
}