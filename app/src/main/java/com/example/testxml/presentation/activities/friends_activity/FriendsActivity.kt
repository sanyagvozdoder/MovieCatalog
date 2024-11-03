package com.example.testxml.presentation.activities.friends_activity

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.example.testxml.R
import com.example.testxml.databinding.FriendsActivityBinding
import com.example.testxml.presentation.activities.friends_activity.util.FriendsGridCarouselAdapter
import com.example.testxml.presentation.activities.friends_activity.util.FriendsSpacingDecoration
import com.example.testxml.presentation.activities.movie_details_screen.MovieDetailsActivity
import com.example.testxml.presentation.activities.movies_screen.components.GridCarouselAdapter
import com.example.testxml.presentation.activities.movies_screen.components.GridSpacingItemDecoration

class FriendsActivity:AppCompatActivity() {
    private lateinit var binding:FriendsActivityBinding
    private lateinit var userLogin:String
    private val viewModel:FriendsActivityViewModel by viewModels()

    companion object{
        val login = "login"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FriendsActivityBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        userLogin = intent.getStringExtra(FriendsActivity.login).toString()
        viewModel.getFriends(userLogin)

        val friendsList = binding.friendsList
        friendsList.addItemDecoration(FriendsSpacingDecoration(3,4))

        val gridManager = object: GridLayoutManager(this,3){
            override fun canScrollVertically(): Boolean {
                return false
            }
        }
        val gridAdapter = FriendsGridCarouselAdapter()

        friendsList.apply {
            layoutManager = gridManager
            adapter = gridAdapter
        }

        viewModel.friendsState.observe(this){state->
            if(state.isSuccess){
                state.value?.let {
                    gridAdapter.addFriends(it)
                    gridAdapter.setOnItemClickListener(fun(friendId:String, avatarLink:String?, name:String){
                        viewModel.deleteFriend(userLogin, friendId, avatarLink, name)
                    })
                }
            }
        }
    }
}