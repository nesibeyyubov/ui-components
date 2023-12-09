package com.example.instavideos.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.instavideos.R
import com.example.instavideos.databinding.FragmentThreeDImagesBinding
import com.example.instavideos.toPx
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class ThreeDImagesFragment : Fragment(R.layout.fragment_three_d_images) {

    private lateinit var binding: FragmentThreeDImagesBinding

    private val views by lazy {
        mutableListOf(binding.view1, binding.view2, binding.view3, binding.view4)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentThreeDImagesBinding.bind(view)
        super.onViewCreated(view, savedInstanceState)


        lifecycleScope.launch {
            while (true) {

                delay(1500)


                views[0].animate()
                    .scaleX(0.8f)
                    .scaleY(0.8f)
                    .translationX(45f.toPx)
                    .setDuration(300)
                    .start()

                views[1].animate()
                    .scaleX(0.9f)
                    .scaleY(0.9f)
                    .translationX(15f.toPx)
                    .setDuration(300)
                    .start()

                views[2].animate()
                    .scaleX(1f)
                    .scaleY(1f)
                    .translationX((-15f).toPx)
                    .setDuration(300)
                    .start()

                val topAnimator = views[3].animate()
                    .alpha(0f)
                    .translationX((75f).toPx)
                    .setDuration(300)

                topAnimator.withEndAction {
                    val removedTopView = views.removeLast()


                    views.add(0, removedTopView)

                    views.first()
                        .animate()
                        .alpha(1f)
                        .scaleX(0.7f)
                        .scaleY(0.7f)
                        .setDuration(300)
                        .start()

                    views[0].elevation = -4f
                    views[1].elevation = -3f
                    views[2].elevation = -2f
                    views[3].elevation = -1f


                }

            }
        }


    }


}
