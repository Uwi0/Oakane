package com.kakapo.oakane.presentation.feature.categories.component

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.kakapo.oakane.common.toColorInt
import com.kakapo.oakane.model.category.CategoryModel
import com.kakapo.oakane.presentation.ui.component.RowWrapper
import com.kakapo.oakane.presentation.ui.model.asIcon

@Composable
internal fun CategoryItemView(category: CategoryModel) {
    RowWrapper {
        if (category.isDefault) {
            CategoryIconView(
                icon = category.iconName.asIcon(),
                color = Color(category.color.toColorInt())
            )
        } else {
            Text(text = "Not Implemented yet")
        }

        Text(text = category.name, style = MaterialTheme.typography.titleMedium)
    }
}

