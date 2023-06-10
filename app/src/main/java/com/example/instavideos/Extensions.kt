package com.example.instavideos

import android.animation.Animator
import android.animation.ValueAnimator
import android.content.Context
import android.content.res.ColorStateList
import android.content.res.Resources
import android.util.Log
import android.view.View
import android.view.ViewPropertyAnimator
import android.view.animation.Animation
import android.view.animation.BaseInterpolator
import android.view.animation.BounceInterpolator
import android.view.animation.Interpolator
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment


fun Fragment.getColor(color: Int) = ContextCompat.getColor(requireContext(), color)
fun Context.getColorContext(color: Int) = ContextCompat.getColor(this, color)

val Int.toPx get() = (this * Resources.getSystem().displayMetrics.density).toInt()

val Int.toDp get() = (this / Resources.getSystem().displayMetrics.density).toInt()

fun ImageView.setTint(color: Int) {
    this.imageTintList = ColorStateList.valueOf(this.context.getColorContext(R.color.black))
}

fun ViewPropertyAnimator.onAnimationFinished(callback: () -> Unit) {
    this.setListener(object : Animator.AnimatorListener {
        override fun onAnimationStart(animation: Animator) {}
        override fun onAnimationEnd(animation: Animator) {
            callback()
        }

        override fun onAnimationCancel(animation: Animator) {}
        override fun onAnimationRepeat(animation: Animator) {}
    })
}

fun View.animateWidth(
    widthFrom: Int = 0,
    widthTo: Int,
    duration: Long = 0,
    interpolator: Interpolator? = null,
    startDelay: Long = 0,
    onFinish: (() -> Unit)? = null,
) {

    Log.d("mytag", "animateWidth: ${widthFrom} - ${widthTo}")

    val animator = ValueAnimator
        .ofInt(widthFrom, widthTo)
        .setDuration(duration)

    animator.startDelay = startDelay

    animator.addUpdateListener { animation1: ValueAnimator ->
        val value = animation1.animatedValue as Int
        this.layoutParams.width = value
        this.requestLayout()
    }

    animator.addListener(object : Animator.AnimatorListener {
        override fun onAnimationStart(animation: Animator) {}
        override fun onAnimationEnd(animation: Animator) {
            onFinish?.invoke()
        }

        override fun onAnimationCancel(animation: Animator) {}
        override fun onAnimationRepeat(animation: Animator) {}

    })

    animator.interpolator = interpolator
    animator.start()
}

fun View.animateHeight(
    height: Int,
    duration: Long = 0,
    interpolator: Interpolator? = null,
    startDelay: Long = 0,
    onFinish: (() -> Unit)? = null,
) {

    val animator = ValueAnimator
        .ofInt(0, height)
        .setDuration(duration)

    animator.startDelay = startDelay

    animator.addUpdateListener { animation1: ValueAnimator ->
        val value = animation1.animatedValue as Int
        this.layoutParams.height = value
        Log.d("mytag", "animateHeight: ${animation1.currentPlayTime}, $value")
        this.requestLayout()
    }

    animator.addListener(object : Animator.AnimatorListener {
        override fun onAnimationStart(animation: Animator) {}
        override fun onAnimationEnd(animation: Animator) {
            onFinish?.invoke()
        }

        override fun onAnimationCancel(animation: Animator) {}
        override fun onAnimationRepeat(animation: Animator) {}

    })

    animator.interpolator = interpolator
    animator.start()
}


fun View.animateWidthAndHeight(
    size: Int,
    duration: Long = 0,
    interpolator: Interpolator? = null,
    onFinish: (() -> Unit)? = null
) {

    val slideAnimator = ValueAnimator
        .ofInt(this.width, size)
        .setDuration(duration)

    slideAnimator.addUpdateListener { animation1: ValueAnimator ->
        val value = animation1.animatedValue as Int
        this.layoutParams.width = value
        this.layoutParams.height = value
        this.requestLayout()
        if (value == size) {
            onFinish?.invoke()
        }
    }

    slideAnimator.interpolator = interpolator
    slideAnimator.start()


}

