package com.kakapo.oakane.presentation.feature.categories.component

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
import com.kakapo.oakane.common.utils.getSavedImageUri
import com.kakapo.oakane.model.category.CategoryModel
import com.kakapo.oakane.presentation.designSystem.component.image.CustomDynamicAsyncImage
import com.kakapo.oakane.presentation.ui.component.RowWrapper
import com.kakapo.oakane.presentation.ui.model.asIcon
import com.kakapo.oakane.presentation.viewModel.categories.CategoriesEvent

@Composable
internal fun CategoryItemView(category: CategoryModel, onEvent: (CategoriesEvent) -> Unit) {
    val context = LocalContext.current
    RowWrapper(onClick = { onEvent.invoke(CategoriesEvent.OnTapped(category)) }) {
        if (category.isDefault) {
            CategoryIconView(
                icon = category.iconName.asIcon(),
                color = Color(category.formattedColor)
            )
        } else {
            val icon = context.getSavedImageUri(category.icon).getOrNull()
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

