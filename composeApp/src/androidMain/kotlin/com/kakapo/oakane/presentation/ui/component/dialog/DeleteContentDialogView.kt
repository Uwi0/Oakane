package com.kakapo.oakane.presentation.ui.component.dialog

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.kakapo.oakane.presentation.designSystem.component.button.CustomButton
import com.kakapo.oakane.presentation.designSystem.component.button.CustomTextButton

@Composable
fun DeleteContentDialogView(title: String, subtitle: String, onDismiss: () -> Unit, onConfirm: () -> Unit) {
    Column(
        modifier = Modifier.padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        DeleteTopContentView(title, subtitle)
        DeleteBottomContentView(onDismiss, onConfirm)
    }
}

@Composable
private fun DeleteTopContentView(title: String, subtitle: String) {
    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        Text(
            text = title,
            style = MaterialTheme.typography.headlineSmall
        )
        Text(
            text = subtitle,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

@Composable
private fun DeleteBottomContentView(onDismiss: () -> Unit, onConfirm: () -> Unit) {
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
        Spacer(modifier = Modifier.weight(1f))
        Spacer(Modifier.weight(1f))
        CustomTextButton(
            onClick = onDismiss,
            content = { Text(text = "Cancel") }
        )
        CustomButton(
            onClick = onConfirm,
            containerColor = MaterialTheme.colorScheme.error,
            content = { Text(text = "Delete") }
        )
    }
}