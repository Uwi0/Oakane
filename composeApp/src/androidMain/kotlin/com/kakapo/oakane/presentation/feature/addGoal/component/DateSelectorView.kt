package com.kakapo.oakane.presentation.feature.addGoal.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedIconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.kakapo.oakane.common.toDateWith

@Composable
internal fun DateSelectorView(
    defaultDate: Long,
    title: String,
    icon: ImageVector,
    onClick: () -> Unit
) {
    Surface(
        onClick = onClick
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            DeadlineIcon(icon = icon)
            Text(title)
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = defaultDate.toDateWith(format = "dd MMM yyyy"),
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.primary
            )
        }
    }

}

@Composable
private fun DeadlineIcon(icon: ImageVector) {
    OutlinedIconButton(
        onClick = {},
        shape = CircleShape,
        border = BorderStroke(2.dp, color = MaterialTheme.colorScheme.primary)
    ) {
        Icon(imageVector = icon, contentDescription = "")
    }
}