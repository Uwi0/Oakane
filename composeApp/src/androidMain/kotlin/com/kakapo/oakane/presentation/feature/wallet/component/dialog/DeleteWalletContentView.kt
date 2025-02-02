package com.kakapo.oakane.presentation.feature.wallet.component.dialog

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
import com.kakapo.oakane.presentation.viewModel.wallet.WalletEvent

@Composable
fun DeleteContentView(event: (WalletEvent) -> Unit) {
    Column(
        modifier = Modifier.padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        DeleteTopContentView()
        DeleteBottomContentView(event)
    }
}

@Composable
private fun DeleteTopContentView() {
    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        Text(
            text = "Are you sure want to delete this wallet?",
            style = MaterialTheme.typography.headlineSmall
        )
        Text(
            text = "This action can't be undone and will delete all transactions in this wallet",
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

@Composable
private fun DeleteBottomContentView(onEvent: (WalletEvent) -> Unit) {
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
        Spacer(modifier = Modifier.weight(1f))
        Spacer(Modifier.weight(1f))
        CustomTextButton(
            onClick = { onEvent.invoke(WalletEvent.ShowDialog(shown = false)) },
            content = { Text(text = "Cancel") }
        )
        CustomButton(
            onClick = { onEvent.invoke(WalletEvent.ConfirmDelete) },
            containerColor = MaterialTheme.colorScheme.error,
            content = { Text(text = "Delete") }
        )
    }
}