package com.example.instavideos.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.instavideos.R
import com.example.instavideos.databinding.FragmentToggleSwitchButtonBinding

class ToggleSwitchButtonFragment : Fragment(R.layout.fragment_toggle_switch_button) {

    private lateinit var binding: FragmentToggleSwitchButtonBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentToggleSwitchButtonBinding.bind(view)
        super.onViewCreated(view, savedInstanceState)


    }
}