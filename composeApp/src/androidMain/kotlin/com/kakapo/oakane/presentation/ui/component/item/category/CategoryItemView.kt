package com.kakapo.oakane.presentation.ui.component.item.category

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.kakapo.common.getImageUriFromFileName
import com.kakapo.model.category.CategoryModel
import com.kakapo.model.system.Theme
import com.kakapo.oakane.presentation.designSystem.component.image.CustomDynamicAsyncImage
import com.kakapo.oakane.presentation.ui.component.RowWrapper
import com.kakapo.oakane.presentation.ui.model.asIcon

@Composable
fun CategoryItemView(category: CategoryModel, theme: Theme, onEvent: () -> Unit) {
    val context = LocalContext.current
    RowWrapper(
        modifier = Modifier.padding(vertical = 12.dp, horizontal = 16.dp),
        theme = theme,
        onClick = { onEvent.invoke() }) {
        if (category.isDefault) {
            CategoryIconView(
                icon = category.iconName.asIcon(),
                color = Color(category.formattedColor)
            )
        } else {
            val icon = context.getImageUriFromFileName(category.icon).getOrNull()
            CustomDynamicAsyncImage(
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape),
                imageUrl = icon,
                contentScale = ContentScale.FillBounds
            )
        }

        Text(text = category.name, style = MaterialTheme.typography.titleMedium)
    }
}

