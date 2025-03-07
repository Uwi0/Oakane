package com.kakapo.oakane.presentation.feature.reports.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.kakapo.model.report.ReportModel
import com.kakapo.model.system.Theme
import com.kakapo.model.toFormatCurrency
import com.kakapo.oakane.presentation.ui.component.RowWrapper
import com.kakapo.oakane.presentation.ui.component.SelectedIconModel
import com.kakapo.oakane.presentation.ui.component.SelectedIconView

@Composable
internal fun ReportsItemView(theme: Theme, item: ReportModel) {
    val selectedIcon = SelectedIconModel(
        imageFile = item.icon,
        defaultIcon = item.iconName,
        color = item.formattedColor
    )

    val textColor = if (item.isExpense) MaterialTheme.colorScheme.error
    else MaterialTheme.colorScheme.primary

    val currency = item.currency

    RowWrapper(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp),
        theme = theme,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        SelectedIconView(selectedIcon = selectedIcon, onClick = {})
        Text(text = item.name, style = MaterialTheme.typography.titleMedium)
        Spacer(Modifier.weight(1f))
        Text(
            text = item.amount.toFormatCurrency(currency),
            color = textColor,
            style = MaterialTheme.typography.titleMedium
        )
    }
}