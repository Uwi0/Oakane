package com.kakapo.oakane.presentation.feature.settings.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForwardIos
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kakapo.oakane.presentation.designSystem.component.button.CustomOutlinedButton
import com.kakapo.oakane.presentation.designSystem.theme.AppTheme
import com.kakapo.oakane.presentation.designSystem.theme.Typography

@Composable
internal fun ButtonSettingsNavigation(icon: ImageVector, title: String, onClick: () -> Unit) {
    CustomOutlinedButton(
        contentPadding = PaddingValues(vertical = 14.dp, horizontal = 16.dp),
        modifier = Modifier.fillMaxWidth(),
        onClick = onClick
    ) {
        Icon(imageVector = icon, contentDescription = title)
        Spacer(Modifier.size(16.dp))
        Text(title, style = Typography.titleMedium)
        Spacer(Modifier.weight(1f))
        Icon(Icons.AutoMirrored.Filled.ArrowForwardIos, contentDescription = title)
    }
}

@Composable
@Preview
private fun ButtonSettingsNavigatePreview() {
    AppTheme {
        Surface {
            ButtonSettingsNavigation(icon = Icons.Outlined.Notifications,title = "hello world", onClick = {})
        }
    }
}