package com.example.instavideos.fragments

import android.animation.Animator
import android.animation.Animator.AnimatorListener
import android.animation.ValueAnimator
import android.content.res.ColorStateList
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.animation.AccelerateInterpolator
import android.view.animation.AnticipateInterpolator
import android.view.animation.BounceInterpolator
import android.view.animation.DecelerateInterpolator
import android.view.animation.OvershootInterpolator
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.Toast
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.instavideos.R
import com.example.instavideos.animateWidthAndHeight
import com.example.instavideos.databinding.FragmentCircularMenuBinding
import com.example.instavideos.getColor
import com.example.instavideos.onAnimationFinished
import com.example.instavideos.setTint
import com.example.instavideos.toPx
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class CircularMenuFragment : Fragment(R.layout.fragment_circular_menu) {

    private lateinit var binding: FragmentCircularMenuBinding

    private var menuOpened = false

    private val icons: List<ImageView> by lazy {
        listOf(binding.icon1, binding.icon2, binding.icon3, binding.icon4, binding.icon5, binding.icon6)
    }

    private val blurs: List<ImageView> by lazy {
        listOf(binding.blur1, binding.blur2, binding.blur3, binding.blur4, binding.blur5, binding.blur6)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentCircularMenuBinding.bind(view)

        with(binding) {
            buttonFirstInner.setOnClickListener {
                val animator = ValueAnimator.ofFloat(1f, 0.95f)
                animator.addUpdateListener { animator ->
                    val value = animator.animatedValue as Float
                    it.scaleX = value
                    it.scaleY = value

                    if (value == 0.95f) {
                        animator.reverse()
                    }
                }
                animator.duration = 200
                animator.start()

                if (menuOpened) {
                    icons.forEach { icon ->
                        icon.scaleX = 0f
                        icon.scaleY = 0f
                    }
                    icons.forEach {
                        it.isEnabled = false
                        it.isClickable = false
                    }
                    blurs.forEach { it.alpha = 0f }
                    strokeCircle.alpha = 0f
                    arrow.rotation = 0f

                    ivArrow.setColorFilter(getColorForIndex(0))
                    resetColors()


                    cardView.animateWidthAndHeight(
                        size = 110.toPx,
                        duration = 200L,
                        interpolator = AccelerateInterpolator(),
                        onFinish = {
                            buttonSecondInner.animateWidthAndHeight(
                                size = 110.toPx,
                                duration = 100,
                                interpolator = BounceInterpolator()
                            )
                        }
                    )
                } else {
                    initClickListeners()

                    cardView.animateWidthAndHeight(
                        size = 240.toPx,
                        duration = 300L,
                        interpolator = DecelerateInterpolator(),
                        onFinish = {
                            strokeCircle.alpha = 0.6f
                            strokeCircle.setColorFilter(getColorForIndex(0))
                            buttonSecondInner.animateWidthAndHeight(
                                size = 130.toPx,
                                duration = 300,
                                interpolator = OvershootInterpolator()
                            )
                            resetIconAndBlurState()
                            lifecycleScope.launch {
                                icons.forEach { icon ->
                                    icon.animate().scaleX(1f).scaleY(1f).setDuration(100).start()
                                    delay(50)
                                }
                            }
                            icons.forEach {
                                it.isEnabled = true
                                it.isClickable = true
                            }

                            blurs.first().alpha = 0.6f

                        }
                    )
                }
                changeState()

            }
        }


    }

    private fun getColorForIndex(index: Int): Int {
        return when (index) {
            0 -> getColor(R.color.gradient_1)
            1 -> getColor(R.color.gradient_2)
            2 -> getColor(R.color.gradient_3)
            3 -> getColor(R.color.gradient_4)
            4 -> getColor(R.color.gradient_5)
            5 -> getColor(R.color.gradient_6)
            else -> getColor(R.color.gradient_1)
        }
    }


    private fun initClickListeners() = with(binding) {
        icons.forEach {
            it.isEnabled = false
            it.isClickable = false
        }
        icons.forEachIndexed { index, icon ->
            icon.setOnClickListener {
                val animation = arrow.animate()
                    .setDuration(500)
                    .rotation((index * 60).toFloat())
                    .setInterpolator(OvershootInterpolator())

                icons.forEachIndexed { idx, ic ->
                    ic.setColorFilter(getColor(R.color.gray))
                }
                icon.setColorFilter(getColor(R.color.black))
                animation.setListener(object : AnimatorListener {
                    override fun onAnimationStart(animation: Animator) {}
                    override fun onAnimationEnd(animation: Animator) {
                        blurs.forEachIndexed { blurIndex, blur ->
                            blur.alpha = 0f
                        }
                        blurs[index].animate()
                            .alpha(0.6f)
                            .start()
                        ivArrow.setColorFilter(getColorForIndex(index))
                        strokeCircle.setColorFilter(getColorForIndex(index))
                    }

                    override fun onAnimationCancel(animation: Animator) {}
                    override fun onAnimationRepeat(animation: Animator) {}
                })
                animation.start()

            }
        }
    }

    private fun resetColors() {
        icons.forEachIndexed { index, icon ->
            icon.setColorFilter(if (index == 0) getColor(R.color.black) else getColor(R.color.gray))
        }
    }

    private fun resetIconAndBlurState() = with(binding) {
        icon1.setTint(R.color.black)
        blur1.isVisible = true
    }

    private fun changeState() {
        this.menuOpened = !menuOpened
    }
}