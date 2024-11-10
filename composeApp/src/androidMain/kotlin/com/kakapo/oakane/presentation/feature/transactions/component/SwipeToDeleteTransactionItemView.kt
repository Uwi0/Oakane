package com.kakapo.oakane.presentation.feature.transactions.component

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import com.kakapo.oakane.model.transaction.TransactionModel
import com.kakapo.oakane.presentation.feature.transactions.OnDelete
import com.kakapo.oakane.presentation.feature.transactions.OnNavigateToTransaction
import com.kakapo.oakane.presentation.feature.transactions.TransactionsUiEvent
import com.kakapo.oakane.presentation.ui.component.SwipeToDeleteBackgroundView
import com.kakapo.oakane.presentation.ui.component.item.TransactionItemView

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun SwipeToDeleteTransactionView(
    item: TransactionModel,
    onEvent: (TransactionsUiEvent) -> Unit
) {
    val dismissState = rememberSwipeToDismissBoxState(
        confirmValueChange = {
            when (it) {
                SwipeToDismissBoxValue.StartToEnd -> onEvent.invoke(OnDelete(item))
                SwipeToDismissBoxValue.EndToStart -> onEvent.invoke(OnDelete(item))
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
                onClick = { onEvent.invoke(OnNavigateToTransaction(item.id)) }
            )
        }
    )
}