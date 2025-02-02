package com.kakapo.oakane.presentation.ui.component

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.kakapo.common.toColorLong
import com.kakapo.oakane.R
import com.kakapo.oakane.presentation.ui.component.item.category.CategoryIconView


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HorizontalColorSelectorView(
    defaultColor: Long,
    colorsHex: List<String>,
    onClickBrush: () -> Unit,
    onClickColor: (String) -> Unit
) {
    LazyRow(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
        stickyHeader {
            CategoryIconView(
                icon = R.drawable.ic_rounded_brush,
                color = Color(defaultColor),
                onClick = { onClickBrush.invoke() }
            )
        }
        items(colorsHex) { hex ->
            CategoryIconView(
                icon = R.drawable.ic_empty,
                color = Color(hex.ifEmpty { "0xFF4CAF50" }.toColorLong()),
                onClick = { onClickColor.invoke(hex) }
            )
        }
    }
}