package com.example.instavideos

val components = listOf(
    Component("Animated gradient border", R.id.animatedGradientBorderFragment),
    Component("Horizontally moving chips", R.id.horizontalMovingChipsFragment),
    Component("Like Button Animation", R.id.likeButtonAnimationFragment),
    Component("Circular menu with animation", R.id.circularMenuFragment),
    Component("Download button animation", R.id.downloadButtonFragment),
    Component("Toggle button", R.id.toggleSwitchButtonFragment),
)

data class Component(
    val title: String,
    val fragmentId: Int
)