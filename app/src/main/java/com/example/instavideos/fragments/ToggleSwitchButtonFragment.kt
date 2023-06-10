package com.example.instavideos.fragments

import android.animation.ArgbEvaluator
import android.animation.ValueAnimator
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.animation.AccelerateInterpolator
import android.view.animation.DecelerateInterpolator
import android.view.animation.OvershootInterpolator
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.constraintlayout.motion.widget.MotionLayout.TransitionListener
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintLayout.LayoutParams.UNSET
import androidx.core.view.updateLayoutParams
import androidx.fragment.app.Fragment
import com.example.instavideos.R
import com.example.instavideos.animateHeight
import com.example.instavideos.animateWidth
import com.example.instavideos.databinding.FragmentToggleSwitchButtonBinding
import com.example.instavideos.getColor
import com.example.instavideos.toPx

enum class MotionState {
    START_TO_MIDDLE,
    MIDDLE_TO_END,
    END_TO_MIDDLE,
    MIDDLE_TO_START
}

class ToggleSwitchButtonFragment : Fragment(R.layout.fragment_toggle_switch_button) {

    private lateinit var binding: FragmentToggleSwitchButtonBinding

    private var enabled = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentToggleSwitchButtonBinding.bind(view)
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            clToggle.setOnClickListener {
                if (enabled.not()) {
                    itemBg.animateWidth(
                        widthFrom = itemBg.width,
                        widthTo = (clToggle.width - 30.toPx),
                        duration = 250,
                        interpolator = AccelerateInterpolator()
                    ) {
                        itemBg.updateLayoutParams<ConstraintLayout.LayoutParams> {
                            endToEnd = binding.clToggle.id
                            startToStart = UNSET
                        }
                        itemBg.animateWidth(
                            widthFrom = itemBg.width,
                            widthTo = 60.toPx,
                            duration = 150,
                            interpolator = DecelerateInterpolator()
                        )

                        enabled = !enabled
                    }


                } else {
                    itemBg.animateWidth(
                        widthFrom = itemBg.width,
                        widthTo = (clToggle.width - 30.toPx),
                        duration = 250,
                        interpolator = AccelerateInterpolator()
                    ) {
                        itemBg.updateLayoutParams<ConstraintLayout.LayoutParams> {
                            startToStart = binding.clToggle.id
                            endToEnd = UNSET
                        }
                        itemBg.animateWidth(
                            widthFrom = itemBg.width,
                            widthTo = 60.toPx,
                            duration = 150,
                            interpolator = DecelerateInterpolator()
                        )

                        enabled = !enabled
                    }
                }
                animateBackgroundColor()
                ivSun.animate()
                    .rotation(if (enabled.not()) 360f else 0f)
                    .setInterpolator(DecelerateInterpolator())
                    .setDuration(300)
                    .start()

                ivNight.animate()
                    .rotation(if (enabled.not()) 360f else 0f)
                    .setInterpolator(DecelerateInterpolator())
                    .setDuration(300)
                    .start()

            }
        }


    }


    private fun animateBackgroundColor() {
        val black = getColor(R.color.black)
        val white = getColor(R.color.white)

        val animator = if (enabled.not()) ValueAnimator.ofObject(ArgbEvaluator(), white, black)
        else ValueAnimator.ofObject(ArgbEvaluator(), black, white)

        binding.clToggle.setBackgroundResource(if (enabled.not()) R.drawable.rounded_toggle_button_bg else R.drawable.rounded_toggle_bg)

        animator.duration = 250
        animator.addUpdateListener {
            binding.root.setBackgroundColor((it.animatedValue as Int))
        }
        animator.start()
    }
}



