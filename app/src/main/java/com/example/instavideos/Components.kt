package com.example.instavideos

val components = listOf(
    Component("Animated gradient border", R.id.animatedGradientBorderFragment),
    Component("Horizontally moving chips", R.id.horizontalMovingChipsFragment),
    Component("Like Button Animation", R.id.likeButtonAnimationFragment),
)

data class Component(
    val title: String,
    val fragmentId: Int
)