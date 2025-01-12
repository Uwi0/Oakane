package com.kakapo.oakane.presentation.feature.monthlyBudget.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.kakapo.common.getSavedImageUri
import com.kakapo.model.category.CategoryLimitModel
import com.kakapo.model.toFormatCurrency
import com.kakapo.oakane.presentation.designSystem.component.image.CustomDynamicAsyncImage
import com.kakapo.oakane.presentation.designSystem.component.progressIndicator.CustomProgressIndicatorView
import com.kakapo.oakane.presentation.ui.component.RowWrapper
import com.kakapo.oakane.presentation.ui.component.item.category.CategoryIconView
import com.kakapo.oakane.presentation.ui.model.asIcon
import com.kakapo.oakane.presentation.viewModel.monthlyBudget.MonthlyBudgetEvent
import java.text.NumberFormat

@Composable
internal fun CategoryLimitItemView(
    category: CategoryLimitModel,
    onEvent: (MonthlyBudgetEvent) -> Unit
) {
    val progress = NumberFormat.getInstance().format(category.progress * 100)
    val currency = category.currency
    val spent = category.spent.toFormatCurrency(currency)
    RowWrapper(
        modifier = Modifier.padding(vertical = 12.dp, horizontal = 16.dp),
        onClick = { onEvent.invoke(MonthlyBudgetEvent.Selected(category)) }) {
        CategoryLimitIconView(category)
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    category.name,
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    category.limit.toFormatCurrency(currency),
                    style = MaterialTheme.typography.titleMedium
                )
            }
            CustomProgressIndicatorView(category.progress)
            Text(
                text = "Spent: ${spent}/${progress}%",
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.outline
            )
        }
    }
}


@Composable
private fun CategoryLimitIconView(category: CategoryLimitModel) {
    if (category.isDefault) {
        CategoryIconView(
            icon = category.iconName.asIcon(),
            color = Color(category.formattedColor)
        )
    } else {
        val context = LocalContext.current
        val icon = context.getSavedImageUri(category.fileName).getOrNull()
        CustomDynamicAsyncImage(
            modifier = Modifier
                .size(48.dp)
                .clip(CircleShape),
            imageUrl = icon,
            contentScale = ContentScale.FillBounds
        )
    }
}