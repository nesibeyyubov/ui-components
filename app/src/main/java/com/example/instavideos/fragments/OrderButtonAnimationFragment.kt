package com.example.instavideos.fragments

import android.os.Bundle
import android.view.View
import android.view.animation.AnticipateInterpolator
import android.view.animation.LinearInterpolator
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.example.instavideos.R
import com.example.instavideos.databinding.FragmentOrderButtonAnimationBinding
import com.example.instavideos.onAnimationFinished

class OrderButtonAnimationFragment : Fragment(R.layout.fragment_order_button_animation) {

    private lateinit var binding: FragmentOrderButtonAnimationBinding


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentOrderButtonAnimationBinding.bind(view)



        with(binding) {
            clAnimatedBtn.setOnClickListener {
                clAnimatedBtn.isEnabled = false
                clAnimatedBtn.isClickable = false

                ivBox.animate()
                    .translationX(35f)
                    .setDuration(300)
                    .start()

                val truckAnimator = clTruckContainer.animate()
                    .translationX((-300).toFloat())
                    .setDuration(1000)
                    .setStartDelay(50)

                tvConfirmOrder.alpha = 0f

                truckAnimator.start()
                truckAnimator.onAnimationFinished {

                    val doorAnimator = viewDoorTop.animate()
                        .rotation(45f)
                        .setDuration(500)

                    doorAnimator.start()

                    viewDoorBottom.animate()
                        .rotation(-45f)
                        .setDuration(500)
                        .start()

                    doorAnimator.onAnimationFinished {
                        val boxAnimator = ivBox.animate()
                            .translationX(180f)
                            .setDuration(500)

                        boxAnimator.start()

                        boxAnimator.onAnimationFinished {
                            ivBox.isVisible = false
                            val doorTopAnimator = viewDoorTop.animate()
                                .rotation(-90f)
                                .setDuration(350)

                            doorTopAnimator.start()

                            viewDoorBottom.animate()
                                .rotation(90f)
                                .setStartDelay(50)
                                .setDuration(350)
                                .start()

                            doorTopAnimator.onAnimationFinished {
                                val dashAnimator = viewDash.animate()
                                    .translationX(-1700f)
                                    .setDuration(4000)

                                dashAnimator.start()

                                val truckAnimator = clTruckContainer.animate()
                                    .translationX((700).toFloat())
                                    .setInterpolator(AnticipateInterpolator())
                                    .setDuration(3500)

                                truckAnimator.start()

                                dashAnimator.onAnimationFinished {
                                    viewDash.animate()
                                        .alpha(0f)
                                        .setDuration(250)
                                        .start()
                                    tvConfirmOrder.text = "Confirmed!"
                                    ivDone.isVisible = true
                                    tvConfirmOrder.animate()
                                        .alpha(1f)
                                        .setDuration(250)
                                        .setStartDelay(50)
                                        .start()

                                    ivDone.animate()
                                        .scaleY(1f)
                                        .scaleX(1f)
                                        .setDuration(200)
                                        .setInterpolator(LinearInterpolator())
                                        .setStartDelay(50)
                                        .start()
                                }
                            }
                        }
                    }
                }


            }

        }
    }

}