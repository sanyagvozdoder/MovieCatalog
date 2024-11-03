package com.example.testxml.presentation.activities.friends_activity.util

import android.content.res.ColorStateList
import android.graphics.Color
import android.text.Layout.Alignment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.TEXT_ALIGNMENT_CENTER
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.testxml.R
import com.example.testxml.data.room.entities.Friend
import com.example.testxml.domain.models.MovieGridCarousel
import com.squareup.picasso.Picasso

class FriendsGridCarouselAdapter : RecyclerView.Adapter<FriendsGridCarouselAdapter.PosterViewHolder>() {
    private val friendsList = mutableListOf<Friend>()
    private lateinit var onItemClickListener: (String,String?,String)->Unit
    class PosterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val avatar:ImageView = itemView.findViewById(R.id.avatar)
        val closeBtn: ImageButton = itemView.findViewById(R.id.close_button)
        val nick:TextView = itemView.findViewById(R.id.nickName)
    }

    fun setOnItemClickListener(onItemClickListener: (String,String?,String)->Unit) {
        this.onItemClickListener = onItemClickListener
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PosterViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.friends_content, parent, false)
        return PosterViewHolder(view)
    }

    override fun getItemCount(): Int {
        return friendsList.size
    }

    override fun onBindViewHolder(holder: PosterViewHolder, position: Int) {
        if(!friendsList[position].avatarLink.isNullOrEmpty()){
            Picasso.get().load(friendsList[position].avatarLink).into(holder.avatar)
        }
        else{
            holder.avatar.setImageResource(R.drawable.blank_avatar)
        }
        holder.nick.text = friendsList[position].name
        holder.closeBtn.setOnClickListener(View.OnClickListener{
            onItemClickListener(friendsList[position].friendId, friendsList[position].avatarLink, friendsList[position].name)
            if (position < friendsList.size) {
                friendsList.removeAt(position)
                notifyItemRemoved(position)
                notifyItemRangeChanged(position, friendsList.size)
            }
        })
    }

    fun addFriends(friends : List<Friend>) {
        friendsList.addAll(friends)
        notifyDataSetChanged()
    }
}