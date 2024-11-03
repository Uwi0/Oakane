package com.kakapo.oakane.presentation.feature.categories.component.sheet

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kakapo.oakane.model.category.categoryMap
import com.kakapo.oakane.presentation.designSystem.theme.AppTheme
import com.kakapo.oakane.presentation.feature.categories.component.SelectionIconItemView
import com.kakapo.oakane.presentation.viewModel.categories.CategoriesEvent

@Composable
internal fun SelectCategoryIconView(onEvent: (CategoriesEvent) -> Unit) {
    LazyColumn(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Text(
                text = "Chose Icon",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
        }
        items(categoryMap.keys.toList(), key = { it.name }) { parentCategory ->
            SelectionIconItemView(parentCategory, onEvent)
        }
    }
}

@Composable
@Preview
private fun SelectCategoryIconViewPrev() {
    AppTheme {
        Surface {
            SelectCategoryIconView {

            }
        }
    }
}