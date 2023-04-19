package com.example.instavideos.fragments

import android.animation.ValueAnimator
import android.content.Context
import android.content.res.Resources
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.animation.AccelerateInterpolator
import android.view.animation.Animation
import android.view.animation.BounceInterpolator
import android.view.animation.LinearInterpolator
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.instavideos.R
import com.example.instavideos.databinding.FragmentLikeButtonAnimationBinding

private const val COMPONENT_DURATION = 100L


class LikeButtonAnimationFragment : Fragment(R.layout.fragment_like_button_animation) {

    private lateinit var binding: FragmentLikeButtonAnimationBinding

    private var liked = false


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentLikeButtonAnimationBinding.bind(view)
        super.onViewCreated(view, savedInstanceState)



        with(binding) {
            likeButton.setOnClickListener {
                this@LikeButtonAnimationFragment.liked = !this@LikeButtonAnimationFragment.liked

                if (liked) {
                    likeButton.animateWidth(135.toPx)

                    ivLike.animate()
                        .setDuration(150)
                        .translationX(-40f)
                        .alpha(0f)
                        .start()

                    tvLikeCount.animate()
                        .alpha(0f)
                        .setDuration(50L)
                        .setUpdateListener {
                            if (it.animatedValue == 1f) {
                                startCountAnimation()
                            }
                        }
                        .start()



                    tvLike.animate()
                        .setDuration(COMPONENT_DURATION)
                        .translationX(-40f)
                        .setInterpolator(LinearInterpolator())
                        .setUpdateListener {
                            val currentValue = it.animatedValue as? Float
                            if (currentValue == 1f) {
                                tvLike.text = "Liked"
                            }
                        }


                } else {
                    likeButton.animateWidth(125.toPx)

                    ivLike.animate()
                        .setDuration(COMPONENT_DURATION)
                        .translationX(0f)
                        .alpha(1f)
                        .start()

                    tvLikeCount.animate()
                        .alpha(0f)
                        .setDuration(50L)
                        .setUpdateListener {
                            if (it.animatedValue == 1f) {
                                startCountAnimation(false)
                            }
                        }
                        .start()

                    tvLike.animate()
                        .setDuration(COMPONENT_DURATION)
                        .translationX(0f)
                        .setUpdateListener {
                            val currentValue = it.animatedValue as? Float
                            if (currentValue == 1f) {
                                tvLike.text = "Like"
                            }
                        }
                        .start()


                }
            }
        }


    }

    private fun startCountAnimation(fromBottom: Boolean = true) = with(binding) {
        val animator = if (fromBottom) ValueAnimator.ofFloat(30f, 0f) else ValueAnimator.ofFloat(-30f, 0f)
        animator.setDuration(COMPONENT_DURATION)

        var textChanged = false

        animator.interpolator = LinearInterpolator()
        animator.addUpdateListener {
            if (!textChanged) {
                binding.tvLikeCount.text = if (fromBottom) "25" else "24"
                textChanged = true
            }

            tvLikeCount.alpha = 1f
            tvLikeCount.setTextColor(if (fromBottom) getColor(R.color.white) else Color.parseColor("#676767"))
            tvLikeCount.translationY = it.animatedValue as Float

        }
        animator.start()
    }

    fun Fragment.getColor(color: Int) = ContextCompat.getColor(requireContext(), color)

    val Int.toPx get() = (this * Resources.getSystem().displayMetrics.density).toInt()

    val Int.toDp get() = (this / Resources.getSystem().displayMetrics.density).toInt()

    private fun View.animateWidth(toWidth: Int) {

        val slideAnimator = ValueAnimator
            .ofInt(this.width, toWidth)
            .setDuration(500)

        slideAnimator.addUpdateListener { animation1: ValueAnimator ->
            val value = animation1.animatedValue as Int
            this.layoutParams.width = value
            this.requestLayout()

        }

        slideAnimator.interpolator = BounceInterpolator()
        slideAnimator.start()


    }

}