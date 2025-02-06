package com.kakapo.oakane.presentation.ui.component.wallet

import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.kakapo.common.getImageUriFromFileName
import com.kakapo.common.toColorLong
import com.kakapo.model.category.CategoryIconName
import com.kakapo.oakane.R
import com.kakapo.oakane.presentation.designSystem.component.image.CustomDynamicAsyncImage
import com.kakapo.oakane.presentation.ui.component.item.category.CategoryIconView
import com.kakapo.oakane.presentation.ui.model.asIcon

@Composable
fun PrimaryWalletIcon(icon: String, iconName: CategoryIconName, color: String) {
    if (icon.contains("jpg")) {
        val context = LocalContext.current
        val imageUri = context.getImageUriFromFileName(icon).getOrNull()
        val imageUrl by remember { mutableStateOf(imageUri) }
        CustomDynamicAsyncImage(
            imageUrl = imageUrl,
            modifier = Modifier
                .size(30.dp)
                .clip(CircleShape),
            placeholder = painterResource(R.drawable.mona_empty_wallet)
        )
    } else {
        val safeColor = color.ifEmpty { "0xFF4CAF5" }
        CategoryIconView(
            icon = iconName.asIcon(),
            color = Color(safeColor.toColorLong()),
            size = 30.dp
        )
    }
}
