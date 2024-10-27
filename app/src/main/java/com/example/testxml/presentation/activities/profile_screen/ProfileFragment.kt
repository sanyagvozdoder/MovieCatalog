package com.example.testxml.presentation.activities.profile_screen

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.testxml.R
import com.example.testxml.databinding.ProfileFragmentBinding

class ProfileFragment : Fragment(R.layout.profile_fragment) {
    private lateinit var binding: ProfileFragmentBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = ProfileFragmentBinding.bind(view)


    }
}