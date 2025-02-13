package com.kakapo.oakane.presentation.feature.wallet.component.dialog

import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import com.kakapo.oakane.presentation.ui.component.dialog.DeleteContentDialogView
import com.kakapo.oakane.presentation.viewModel.wallet.WalletDialogContent
import com.kakapo.oakane.presentation.viewModel.wallet.WalletEvent
import com.kakapo.oakane.presentation.viewModel.wallet.WalletState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun WalletDialogView(uiState: WalletState, onEvent: (WalletEvent) -> Unit) {
    BasicAlertDialog(
        onDismissRequest = { onEvent.invoke(WalletEvent.ShowDialog(shown = false)) },
        content = { DialogContentView(uiState, onEvent) }
    )
}

@Composable
private fun DialogContentView(uiState: WalletState, onEvent: (WalletEvent) -> Unit) {
    val deleteTitle = "Are you sure want to delete this wallet?"
    val deleteSubtitle =
        "This action can't be undone and will delete all transactions in this wallet"
    Surface(shape = MaterialTheme.shapes.large) {
        when (uiState.dialogContent) {
            WalletDialogContent.DeleteWallet -> DeleteContentDialogView(
                title = deleteTitle,
                subtitle = deleteSubtitle,
                onDismiss = { onEvent.invoke(WalletEvent.ShowDialog(shown = false)) },
                onConfirm = { onEvent.invoke(WalletEvent.ConfirmDelete) }
            )

            WalletDialogContent.MoveBalance -> MoveBalanceDialogContentView(uiState, onEvent)
        }
    }
}

