package com.kakapo.oakane.presentation.feature.wallets.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.kakapo.oakane.presentation.designSystem.component.button.CustomButton
import com.kakapo.oakane.presentation.designSystem.component.button.CustomTextButton
import com.kakapo.oakane.presentation.viewModel.wallets.WalletsEvent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun ConfirmDeleteWalletDialogView(onEvent: (WalletsEvent) -> Unit) {
    BasicAlertDialog(
        onDismissRequest = { onEvent.invoke(WalletsEvent.Dialog(shown = false)) },
        content = {
            Surface(
                shape = MaterialTheme.shapes.medium,
                content = { ConfirmDeleteWalletContentView(onEvent) }
            )
        }
    )
}

@Composable
private fun ConfirmDeleteWalletContentView(onEvent: (WalletsEvent) -> Unit) {
    Column(modifier = Modifier.padding(24.dp)) {
        Text(
            text = "Are you sure want to delete this wallet?",
            style = MaterialTheme.typography.headlineSmall
        )
        Spacer(modifier = Modifier.size(16.dp))
        Text(
            text = "This action can't be undone and will delete all transactions in this wallet",
            style = MaterialTheme.typography.bodyMedium
        )
        Spacer(modifier = Modifier.size(24.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            Spacer(Modifier.weight(1f))
            CustomTextButton(
                onClick = { onEvent.invoke(WalletsEvent.Dialog(shown = false)) },
                content = { Text(text = "Cancel") }
            )
            CustomButton(
                onClick = { onEvent.invoke(WalletsEvent.ConfirmDelete) },
                containerColor = MaterialTheme.colorScheme.error,
                content = { Text(text = "Delete") }
            )
        }
    }
}
