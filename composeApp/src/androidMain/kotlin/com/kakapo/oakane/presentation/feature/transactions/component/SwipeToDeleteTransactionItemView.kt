package com.kakapo.oakane.presentation.feature.transactions.component

import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import com.kakapo.model.transaction.TransactionModel
import com.kakapo.oakane.presentation.ui.component.SwipeToDeleteBackgroundView
import com.kakapo.oakane.presentation.ui.component.item.TransactionItemView
import com.kakapo.oakane.presentation.viewModel.transactions.TransactionsEvent

@Composable
internal fun SwipeToDeleteTransactionView(
    item: TransactionModel,
    onEvent: (TransactionsEvent) -> Unit
) {
    val dismissState = rememberSwipeToDismissBoxState(
        confirmValueChange = {
            when (it) {
                SwipeToDismissBoxValue.StartToEnd -> onEvent.invoke(TransactionsEvent.Delete(item))
                SwipeToDismissBoxValue.EndToStart -> onEvent.invoke(TransactionsEvent.Delete(item))
                SwipeToDismissBoxValue.Settled -> return@rememberSwipeToDismissBoxState false
            }
            return@rememberSwipeToDismissBoxState true
        },
        positionalThreshold = { it * .25f }
    )

    SwipeToDismissBox(
        state = dismissState,
        backgroundContent = { SwipeToDeleteBackgroundView(dismissState) },
        content = {
            TransactionItemView(
                transaction = item,
                onClick = { onEvent.invoke(TransactionsEvent.ToDetail(item.id)) }
            )
        }
    )
}