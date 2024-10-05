package com.kakapo.oakane.presentation.feature.home.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.kakapo.oakane.presentation.designSystem.component.button.CustomTextButton

@Composable
internal fun ShowMoreButtonView(
    isVisible: Boolean,
    onClick: () -> Unit
) {
    if (isVisible) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
            CustomTextButton(
                modifier = Modifier.height(24.dp),
                contentPadding = PaddingValues(0.dp),
                onClick = { /*TODO*/ }) {
                Text(
                    modifier = Modifier.clickable { onClick.invoke() },
                    text = "Show More",
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}