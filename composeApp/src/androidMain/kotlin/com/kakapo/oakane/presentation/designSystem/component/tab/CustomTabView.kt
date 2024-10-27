package com.kakapo.oakane.presentation.designSystem.component.tab

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun CustomTabView(
    modifier: Modifier = Modifier,
    selected: Boolean,
    onClick: () -> Unit,
    text: @Composable () -> Unit
) {
    Tab(
        selected = selected,
        onClick = onClick,
        modifier = modifier,
        text = {
            val style = MaterialTheme.typography.labelLarge.copy(textAlign = TextAlign.Center)
            ProvideTextStyle(
                value = style
            ) {
                Box(modifier = Modifier.padding(top = CustomTabDefaults.TabTopPadding)) {
                    text.invoke()
                }
            }
        }
    )
}

@Composable
fun CustomTabRowView(
    selectedTabIndex: Int,
    modifier: Modifier = Modifier,
    tabWidth: Dp = 48.dp,
    tabs: @Composable () -> Unit,
) {
    TabRow(
        selectedTabIndex = selectedTabIndex,
        modifier = modifier,
        containerColor = Color.Transparent,
        contentColor = MaterialTheme.colorScheme.onSurface,
        indicator = { tabPositions ->
            TabRowDefaults.PrimaryIndicator(
                modifier = Modifier.tabIndicatorOffset(tabPositions[selectedTabIndex]),
                height = 4.dp,
                width = tabWidth,
                shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
            )
        },
        tabs = tabs,
    )
}

object CustomTabDefaults {
    val TabTopPadding = 7.dp
}