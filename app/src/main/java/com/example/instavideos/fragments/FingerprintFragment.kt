package com.example.instavideos.fragments

import android.animation.Animator
import android.animation.ArgbEvaluator
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.ViewPropertyAnimator
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.AccelerateInterpolator
import android.view.animation.BounceInterpolator
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.animation.doOnEnd
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.interpolator.view.animation.FastOutSlowInInterpolator
import androidx.lifecycle.lifecycleScope
import com.example.instavideos.R
import com.example.instavideos.databinding.FragmentAnimatedGradientBorderBinding
import com.example.instavideos.databinding.FragmentFingerprintBinding
import com.example.instavideos.onAnimationFinished
import com.example.instavideos.setTint
import com.example.instavideos.toDp
import com.example.instavideos.toPx
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.random.Random

class FingerprintFragment : Fragment(R.layout.fragment_fingerprint) {
    private lateinit var binding: FragmentFingerprintBinding

    private var successShown = false
    private var animationCancelled = false

    private val allAnimators = mutableListOf<ViewPropertyAnimator>()
    private var objectAnimators = mutableListOf<ObjectAnimator>()
    private val waves = mutableListOf<View>()

    @SuppressLint("ClickableViewAccessibility")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentFingerprintBinding.bind(view)
        super.onViewCreated(view, savedInstanceState)



        with(binding) {

            btnTouch.setOnTouchListener { v, event ->
                if (event.action == MotionEvent.ACTION_DOWN) {
                    successShown = false
                    ivSuccess.scaleX = 0f
                    ivSuccess.scaleY = 0f
                    ivSuccess.alpha = 0f

                    btnTouch.animate()
                        .scaleX(0.9f)
                        .scaleY(0.9f)
                        .start()

                    startWholeAnimation()
                } else if (event.action == MotionEvent.ACTION_UP) {
                    successShown = true
                    animationCancelled = true
                    btnTouch.animate()
                        .scaleX(1f)
                        .scaleY(1f)
                        .start()
                    resetToDefault()

                }
                true
            }
        }
    }

    private fun resetToDefault() = with(binding) {
        allAnimators.forEach { it.cancel() }
        objectAnimators.forEach {
            it.removeAllListeners()
            it.cancel()

        }
        progressBar.progress = 0
        progressBar.isVisible = false

        waves.forEach { binding.root.removeView(it) }

        allAnimators.clear()
        objectAnimators.clear()
        waves.clear()

        val fingerPrintScaleAnimator = ivFingerprint.animate()
            .scaleX(1.4f)
            .scaleY(1.4f)
            .setDuration(550)

        fingerPrintScaleAnimator.setUpdateListener {
            val value = it.animatedValue as Float
            if (value < 1.2f) {
                ivFingerprint.setTint(R.color.blue)
            }
        }

        fingerPrintScaleAnimator.start()

        viewBg.animate()
            .scaleX(0f)
            .scaleY(0f)
            .alpha(0f)
            .setDuration(550)
            .start()
    }

    private fun startWholeAnimation() = with(binding) {
        val fingerPrintScaleAnimator = ivFingerprint.animate()
            .scaleX(1f)
            .scaleY(1f)
            .setDuration(550)
            .apply { allAnimators.add(this) }

        fingerPrintScaleAnimator.setUpdateListener {
            val value = it.animatedValue as Float
            if (value > 0.4f) {
                ivFingerprint.setTint(R.color.white)
            }
        }

        fingerPrintScaleAnimator.start()

        viewBg.animate()
            .scaleX(1f)
            .scaleY(1f)
            .alpha(1f)
            .setDuration(550)
            .withEndAction {
                startCircularWaveAnimation()
                startProgressAnimation()
            }
            .apply { allAnimators.add(this) }
            .start()
    }

    private fun startProgressAnimation() = with(binding) {
        progressBar.isVisible = true
        progressBar.max = 700
        val animator = ObjectAnimator.ofInt(progressBar, "progress", 0, 700)
        animator.interpolator = FastOutSlowInInterpolator()
        animator.duration = 2500

        animator.start()

        animator.doOnEnd {
            Log.d("mytag", "do on end: ")
            ivSuccess.animate()
                .scaleX(1f)
                .scaleY(1f)
                .alpha(1f)
                .setDuration(600)
                .withEndAction {
                    successShown = true
                }
                .also { allAnimators.add(it) }
                .start()

            viewBg.animate()
                .scaleY(0f)
                .scaleX(0f)
                .alpha(0f)
                .also { allAnimators.add(it) }
                .setDuration(400)
                .start()
        }

        objectAnimators.add(animator)

    }


    private fun startCircularWaveAnimation() = with(binding) {
        lifecycleScope.launch {
            while (successShown.not()) {
                val outline = createNewOutline()
                waves.add(outline)

                binding.root.addView(outline)

                outline.animate()
                    .scaleX(1.9f)
                    .scaleY(1.9f)
                    .setDuration(1500)
                    .alpha(0f)
                    .withEndAction {
                        binding.root.removeView(outline)
                    }
                    .also { allAnimators.add(it) }
                    .start()




                delay(500)
            }
        }


    }

    private fun createNewOutline(): View = with(binding) {
        val newOutline = View(requireContext())
        newOutline.layoutParams = ConstraintLayout.LayoutParams(195.toPx, 195.toPx)
            .apply {
                bottomToBottom = viewBg.id
                topToTop = viewBg.id
                startToStart = viewBg.id
                endToEnd = viewBg.id
            }
        newOutline.background =
            ContextCompat.getDrawable(requireContext(), R.drawable.bg_stroke_blue)
        newOutline.isVisible = true

        newOutline.id = Random.nextInt()

        return@with newOutline
    }

}