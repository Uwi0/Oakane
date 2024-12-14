package com.kakapo.oakane.presentation.ui.component

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.kakapo.oakane.R
import com.kakapo.oakane.common.toColorInt
import com.kakapo.oakane.presentation.ui.component.item.category.CategoryIconView

data class ColorSelector(
    val defaultColor: Int,
    val colorsHex: List<String>
)

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HorizontalColorSelectorView(
    colorSelector: ColorSelector,
    onClickBrush: () -> Unit,
    onClickColor: (String) -> Unit
) {
    LazyRow(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
        stickyHeader {
            CategoryIconView(
                icon = R.drawable.ic_rounded_brush,
                color = Color(colorSelector.defaultColor),
                onClick = { onClickBrush.invoke() }
            )
        }
        items(colorSelector.colorsHex) { hex ->
            CategoryIconView(
                icon = R.drawable.ic_empty,
                color = Color(hex.toColorInt()),
                onClick = { onClickColor.invoke(hex) }
            )
        }
    }
}