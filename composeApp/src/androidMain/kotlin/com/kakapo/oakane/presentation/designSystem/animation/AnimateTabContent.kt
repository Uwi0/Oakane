package com.kakapo.oakane.presentation.designSystem.animation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.ContentTransform
import androidx.compose.animation.core.TweenSpec
import androidx.compose.animation.core.tween
import androidx.compose.animation.togetherWith
import androidx.compose.ui.unit.IntOffset

const val ANIMATION_DURATION = 3_00

fun AnimatedContentTransitionScope<Int>.slidingContentAnimation(): ContentTransform {
    val animationSpec: TweenSpec<IntOffset> = tween(ANIMATION_DURATION)
    val direction = getTransitionDirection(initialState, targetState)
    return slideIntoContainer(
        towards = direction,
        animationSpec = animationSpec,
    ) togetherWith slideOutOfContainer(
        towards = direction,
        animationSpec = animationSpec
    )
}

private fun getTransitionDirection(
    initialIndex: Int,
    targetIndex: Int
): AnimatedContentTransitionScope.SlideDirection {
    return if (targetIndex > initialIndex) {
        AnimatedContentTransitionScope.SlideDirection.Left
    } else {
        AnimatedContentTransitionScope.SlideDirection.Right
    }
}