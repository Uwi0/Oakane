package com.kakapo.oakane.presentation.feature.reports.component

import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.kakapo.oakane.presentation.ui.component.chart.AnimatedDonutChart

@Composable
internal fun DonutChartView(
    proportions: List<Float>,
    colorsInt: List<Int>,
){
    val colors by remember { mutableStateOf(colorsInt.map { Color(it) }) }
    AnimatedDonutChart(proportions, colors, modifier = Modifier.size(320.dp))
}