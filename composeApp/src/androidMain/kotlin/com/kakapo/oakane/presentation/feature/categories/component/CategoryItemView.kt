package com.kakapo.oakane.presentation.feature.categories.component

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.kakapo.oakane.model.category.CategoryModel
import com.kakapo.oakane.presentation.ui.component.RowWrapper
import com.kakapo.oakane.presentation.ui.model.asIcon

@Composable
internal fun CategoryItemView(category: CategoryModel) {
    RowWrapper {
        CategoryIconView(
            icon = category.icon.asIcon(),
            color = Color(category.formattedColor)
        )
        Text(text = category.name, style = MaterialTheme.typography.titleMedium)
    }
}

