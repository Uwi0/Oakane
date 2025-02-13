package com.kakapo.oakane.presentation.ui.component

import androidx.compose.ui.unit.Dp

data class WindowInsetInfo(
    val bottomInsetDp: Dp,
    val hasBottomInset: Boolean,
    val systemBarVisible: Boolean
)