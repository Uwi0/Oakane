package com.kakapo.oakane.presentation.ui.util

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.kakapo.model.system.Theme

fun cardTonalElevation(theme: Theme): Dp{
    return if(theme == Theme.Dark) 2.dp else 0.dp
}