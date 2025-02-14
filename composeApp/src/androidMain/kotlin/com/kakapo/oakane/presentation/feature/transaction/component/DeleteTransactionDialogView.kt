package com.kakapo.oakane.presentation.feature.transaction.component

import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import com.kakapo.oakane.presentation.ui.component.dialog.DeleteContentDialogView
import com.kakapo.oakane.presentation.viewModel.transaction.TransactionEvent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun DeleteTransactionDialogView(onEvent: (TransactionEvent) -> Unit) {
    BasicAlertDialog(onDismissRequest = { onEvent.invoke(TransactionEvent.DialogShown(false))}) {
        Surface(shape = MaterialTheme.shapes.medium) {
            val deleteTitle = "Are you sure want to delete this transaction?"
            val deleteSubtitle = "This action can't be undone"
            DeleteContentDialogView(
                title = deleteTitle,
                subtitle = deleteSubtitle,
                onConfirm = { onEvent.invoke(TransactionEvent.DeleteTransaction) },
                onDismiss = { onEvent.invoke(TransactionEvent.DialogShown(false))}
            )
        }
    }
}