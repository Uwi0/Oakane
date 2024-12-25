package com.kakapo.oakane.presentation.feature.reports.component

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.kakapo.oakane.common.toFormatIDRWithCurrency
import com.kakapo.oakane.presentation.designSystem.component.button.CustomButton
import com.kakapo.oakane.presentation.ui.component.ColumnWrapper
import com.kakapo.oakane.presentation.ui.component.chart.AnimatedDonutChart

@OptIn(ExperimentalLayoutApi::class)
@Composable
internal fun DonutChartComponentView(
    amount: Double,
    proportions: List<Float>,
    colorsInt: List<Int>,
    categoriesName: List<String>
) {
    val colors = colorsInt.map { Color(it) }
    ColumnWrapper(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            AnimatedDonutChart(
                modifier = Modifier.size(280.dp),
                proportions = proportions,
                colors = colors
            )

            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(amount.toFormatIDRWithCurrency(), style = MaterialTheme.typography.titleMedium)
                Text(
                    text = "Total",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.outline
                )
            }
        }

        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            categoriesName.forEachIndexed { index, name ->
                ChartLegend(title = name, color = colors[index])
            }
        }
    }
}

@Composable
private fun CustomSegmentedButtonView() {
    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        CustomButton(
            contentPadding = PaddingValues(vertical = 8.dp, horizontal = 16.dp),
            onClick = { },
            containerColor = MaterialTheme.colorScheme.outline
        ) {
            Text(text = "Donut Chart")
        }

        CustomButton(
            contentPadding = PaddingValues(vertical = 8.dp, horizontal = 16.dp),
            onClick = { },
            containerColor = MaterialTheme.colorScheme.outlineVariant
        ) {
            Text(text = "Bar Chart", color = MaterialTheme.colorScheme.outline)
        }
    }
}

@Composable
private fun ChartLegend(color: Color, title: String) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Canvas(modifier = Modifier.size(16.dp)) {
            drawCircle(color = color)
        }

        Text(title, style = MaterialTheme.typography.labelMedium)
    }
}