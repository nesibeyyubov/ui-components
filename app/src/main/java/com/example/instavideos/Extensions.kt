package com.example.instavideos

import android.content.res.Resources
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment


fun Fragment.getColor(color: Int) = ContextCompat.getColor(requireContext(), color)

val Int.toPx get() = (this * Resources.getSystem().displayMetrics.density).toInt()

val Int.toDp get() = (this / Resources.getSystem().displayMetrics.density).toInt()