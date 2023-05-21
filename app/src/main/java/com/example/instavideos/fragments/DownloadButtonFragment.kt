package com.example.instavideos.fragments

import android.os.Bundle
import android.view.View
import android.view.animation.AccelerateInterpolator
import android.view.animation.BounceInterpolator
import androidx.fragment.app.Fragment
import com.example.instavideos.R
import com.example.instavideos.animateHeight
import com.example.instavideos.databinding.FragmentDownloadButtonBinding
import com.example.instavideos.onAnimationFinished

class DownloadButtonFragment : Fragment(R.layout.fragment_download_button) {
    private lateinit var binding: FragmentDownloadButtonBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentDownloadButtonBinding.bind(view)
        super.onViewCreated(view, savedInstanceState)


        with(binding) {
            box.setOnClickListener {
                startAnimation()
            }
            boxLeft.setOnClickListener {
                startAnimation()
            }
        }

    }

    private fun startAnimation() = with(binding) {
        icArrow.animate()
            .translationY(-100f)
            .setDuration(300)
            .start()

        icArrow.animate()
            .setStartDelay(250)
            .alpha(0f)
            .start()

        progressLayerStick.animate()
            .setStartDelay(300)
            .alpha(1f)

        progressLayer.animateHeight(
            height = box.height,
            duration = 1 * 1000,
            interpolator = AccelerateInterpolator(),
            startDelay = 320,
            onFinish = {
                icDone.animate()
                    .scaleX(1f)
                    .scaleY(1f)
                    .setInterpolator(BounceInterpolator())
                    .setDuration(800)
                    .start()
                tvBoxText.text = "Open file"
            }
        )

        tvBoxText.text = "Downloading..."


    }

}