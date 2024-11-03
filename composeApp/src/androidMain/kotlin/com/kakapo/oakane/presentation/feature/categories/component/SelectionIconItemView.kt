package com.kakapo.oakane.presentation.feature.categories.component

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.kakapo.oakane.model.category.CategoryIconName
import com.kakapo.oakane.model.category.ParentCategory
import com.kakapo.oakane.model.category.categoryMap
import com.kakapo.oakane.presentation.ui.model.asIcon
import com.kakapo.oakane.presentation.viewModel.categories.CategoriesEvent

@Composable
fun SelectionIconItemView(parentCategory: ParentCategory, onEvent: (CategoriesEvent) -> Unit) {
    Column {
        SelectionHeaderView(parentCategory)
        ContentItemView(
            parentCategory,
            onEvent = { name -> onEvent.invoke(CategoriesEvent.SelectedIcon(name)) }
        )
    }
}

@Composable
private fun SelectionHeaderView(parentCategory: ParentCategory) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        HorizontalDivider(
            modifier = Modifier.weight(1f)
        )
        Text(
            text = parentCategory.displayName,
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(horizontal = 8.dp)
        )
        HorizontalDivider(
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
@OptIn(ExperimentalLayoutApi::class)
private fun ContentItemView(parentCategory: ParentCategory, onEvent: (CategoryIconName) -> Unit) {
    FlowRow {
        categoryMap[parentCategory]?.forEach { category ->
            SelectionIconView(category, onEvent = onEvent)
        }
    }
}

@Composable
private fun SelectionIconView(category: CategoryIconName, onEvent: (CategoryIconName) -> Unit) {
    Surface(shape = CircleShape, onClick = { onEvent.invoke(category) }) {
        Box(
            modifier = Modifier
                .padding(12.dp)
                .clip(CircleShape)
                .border(
                    width = 2.dp,
                    color = MaterialTheme.colorScheme.outline,
                    shape = CircleShape
                )
        ) {
            Icon(
                painter = painterResource(category.asIcon()),
                contentDescription = null,
                modifier = Modifier.padding(8.dp),
                tint = MaterialTheme.colorScheme.outline
            )
        }
    }
}

