package com.kakapo.oakane.presentation.ui.component

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.kakapo.common.getSavedImageUri
import com.kakapo.model.category.CategoryIconName
import com.kakapo.oakane.R
import com.kakapo.oakane.presentation.designSystem.component.image.CustomDynamicAsyncImage
import com.kakapo.oakane.presentation.ui.component.item.category.CategoryIconView
import com.kakapo.oakane.presentation.ui.model.asIcon

data class SelectedIconModel(
    val imageFile: String,
    val defaultIcon: CategoryIconName,
    val color: Long
)

const val FORMAT_IMAGE = ".jpg"

@Composable
fun SelectedIconView(selectedIcon: SelectedIconModel, onClick: () -> Unit) {
    if (!selectedIcon.imageFile.contains(FORMAT_IMAGE)) {
        CategoryIconView(
            icon = selectedIcon.defaultIcon.asIcon(),
            color = Color(selectedIcon.color),
            onClick = onClick
        )
    } else {
        val context = LocalContext.current
        val uri = context.getSavedImageUri(selectedIcon.imageFile).getOrNull()
        CustomDynamicAsyncImage(
            modifier = Modifier
                .size(48.dp)
                .clip(CircleShape)
                .border(
                    color = Color(selectedIcon.color),
                    width = 3.dp,
                    shape = CircleShape
                )
                .clickable(onClick = onClick),
            imageUrl = uri,
            contentScale = ContentScale.FillBounds,
            placeholder = painterResource(R.drawable.fubuki_stare)
        )
    }
}
