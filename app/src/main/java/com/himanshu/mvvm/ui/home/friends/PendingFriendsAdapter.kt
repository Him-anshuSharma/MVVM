package com.himanshu.mvvm.ui.home.friends

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.himanshu.mvvm.R
import com.himanshu.mvvm.data.network.responses.PendingFriendRequest

class PendingFriendsAdapter(private var requests:MutableList<PendingFriendRequest>): RecyclerView.Adapter<PendingFriendsAdapter.CustomViewHolder>() {

    private lateinit var listener: PendingFriendListListener

    fun setListener(listener: PendingFriendListListener){
        this.listener = listener
    }


    class CustomViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {
        val username: TextView = itemView.findViewById(R.id.pendingFriendRequestUsername)
        val accept: ImageButton = itemView.findViewById(R.id.PendingFriendRequestButtonAccept)
        val reject: ImageButton = itemView.findViewById(R.id.PendingFriendRequestButtonReject)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.friend_request_tile,parent,false)
        return CustomViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        Log.d("Himanshu","In Bind View Holder")
        holder.username.text = requests[position].senderUserId.toString()
        holder.accept.setOnClickListener {
            listener.onClick(true,position)
        }
        holder.reject.setOnClickListener {
            listener.onClick(false,position)
        }
    }

    override fun getItemCount() = requests.size

    fun updateDate(requests:MutableList<PendingFriendRequest>){
        this.requests = requests
        notifyDataSetChanged()
    }

}