package com.example.instavideos.fragments

import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.instavideos.R
import com.example.instavideos.databinding.FragmentAnimatedGradientBorderBinding

class AnimatedGradientBorderFragment : Fragment(R.layout.fragment_animated_gradient_border) {
    private lateinit var binding: FragmentAnimatedGradientBorderBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentAnimatedGradientBorderBinding.bind(view)
        super.onViewCreated(view, savedInstanceState)

        val drawable = binding.rlGradient.background as AnimationDrawable
        drawable.setEnterFadeDuration(700)
        drawable.setExitFadeDuration(800)
        drawable.start()
    }

}