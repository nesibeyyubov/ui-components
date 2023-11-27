package com.example.instavideos.fragments

import android.content.Context
import android.graphics.Canvas
import android.os.Bundle
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.instavideos.Component
import com.example.instavideos.GenericListAdapter
import com.example.instavideos.R
import com.example.instavideos.components
import com.example.instavideos.databinding.ComponentLayoutBinding
import com.example.instavideos.databinding.FragmentHomeBinding
import com.example.instavideos.databinding.FragmentHorizontalMovingChipsBinding
import com.example.instavideos.databinding.FragmentThreeDImagesBinding
import com.example.instavideos.databinding.ItemLayoutBinding
import com.example.instavideos.toDp
import com.example.instavideos.toPx
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.launch


class ThreeDImagesFragment : Fragment(R.layout.fragment_three_d_images) {

    private lateinit var binding: FragmentThreeDImagesBinding

    private val views by lazy { mutableListOf(binding.view1, binding.view2, binding.view3, binding.view4) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentThreeDImagesBinding.bind(view)
        super.onViewCreated(view, savedInstanceState)


        val param = views.first().layoutParams

        lifecycleScope.launch {
            while (true) {

                delay(1500)

                val animator = views[0].animate()
                    .scaleX(0.7f)
                    .scaleY(0.7f)
                    .setDuration(300)
                    .alpha(0f)
//                    .translationX(75.toDp.toFloat())

                animator.start()

                views[1].animate()
                    .scaleX(1f)
                    .scaleY(1f)
//                    .translationX(45.toDp.toFloat())
                    .setDuration(300)
                    .start()

                views[2].animate()
                    .scaleX(0.9f)
                    .scaleY(0.9f)
//                    .translationX(15.toDp.toFloat())
                    .setDuration(300)
                    .start()

                views[3].animate()
                    .scaleX(0.8f)
                    .scaleY(0.8f)
//                    .translationX((-15).toDp.toFloat())
                    .setDuration(300)
                    .start()

                animator.withEndAction {
                    val removedView = views.removeFirst()
                    removedView.alpha = 1f
                    views.add(removedView)
                }


            }
        }


    }


}
