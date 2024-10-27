package com.kakapo.oakane.presentation.feature.categories.component

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.kakapo.oakane.model.category.CategoryModel
import com.kakapo.oakane.presentation.ui.component.RowWrapper
import com.kakapo.oakane.presentation.ui.model.asIconCategory

@Composable
internal fun CategoryItemView(category: CategoryModel) {
    RowWrapper {
        val (icon, color) = category.icon.asIconCategory()
        val contentColor = if (color.luminance() < 0.5f) Color.White else Color.Black
        Surface(color = color, shape = CircleShape) {
            Icon(
                modifier = Modifier.padding(8.dp),
                painter = painterResource(id = icon),
                contentDescription = null,
                tint = contentColor
            )
        }
        Text(text = category.name, style = MaterialTheme.typography.titleMedium)
    }
}