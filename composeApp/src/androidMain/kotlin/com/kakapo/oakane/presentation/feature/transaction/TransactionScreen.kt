package com.kakapo.oakane.presentation.feature.transaction

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DeleteOutline
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.AbsoluteAlignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.kakapo.common.showToast
import com.kakapo.common.toFormatCurrency
import com.kakapo.model.transaction.TransactionModel
import com.kakapo.model.transaction.TransactionType
import com.kakapo.oakane.R
import com.kakapo.oakane.presentation.designSystem.component.button.CustomIconButton
import com.kakapo.oakane.presentation.designSystem.component.topAppBar.CustomNavigationTopAppBarView
import com.kakapo.oakane.presentation.designSystem.theme.AppTheme
import com.kakapo.oakane.presentation.ui.component.ColumnWrapper
import com.kakapo.oakane.presentation.ui.component.RowWrapper
import com.kakapo.oakane.presentation.viewModel.transaction.TransactionEffect
import com.kakapo.oakane.presentation.viewModel.transaction.TransactionEvent
import com.kakapo.oakane.presentation.viewModel.transaction.TransactionState
import com.kakapo.oakane.presentation.viewModel.transaction.TransactionViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
internal fun TransactionRoute(
    transactionId: Long,
    navigateToEdit: (Long) -> Unit,
    navigateBack: () -> Unit
) {
    val context = LocalContext.current
    val viewModel = koinViewModel<TransactionViewModel>()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.initializeData(transactionId)
    }

    LaunchedEffect(Unit){
        viewModel.uiEffect.collect { effect ->
            when (effect) {
                TransactionEffect.NavigateBack -> navigateBack()
                is TransactionEffect.EditTransactionBy -> navigateToEdit(effect.id)
                is TransactionEffect.ShowError -> context.showToast(effect.message)
            }
        }
    }

    TransactionScreen(uiState = uiState, onEvent = viewModel::handleEvent)
}

@Composable
private fun TransactionScreen(
    uiState: TransactionState,
    onEvent: (TransactionEvent) -> Unit
) {
    Scaffold(
        topBar = {
            CustomNavigationTopAppBarView(
                title = "Transaction",
                actions = {
                    CustomIconButton(
                        icon = Icons.Default.Edit,
                        onClick = { onEvent.invoke(TransactionEvent.EditTransaction) }
                    )
                    CustomIconButton(
                        icon = Icons.Default.DeleteOutline,
                        onClick = { onEvent.invoke(TransactionEvent.DeleteTransaction) }
                    )
                },
                onNavigateBack = { onEvent.invoke(TransactionEvent.NavigateBack) }
            )
        },
        content = {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it)
                    .padding(vertical = 24.dp, horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                TopContentView(state = uiState)
                DetailContentView(state = uiState)
                NoteContentView(uiState.transaction.note)
                Text("Add Another feature in the future")
            }

        }
    )
}

@Composable
private fun TopContentView(state: TransactionState) {
    val transactionModel = state.transaction
    RowWrapper(modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp)) {
        Image(
            painter = painterResource(R.drawable.fubuki_stare),
            contentDescription = null,
            modifier = Modifier
                .size(48.dp)
                .clip(CircleShape),
            contentScale = ContentScale.Crop
        )
        Spacer(Modifier.weight(1f))
        Column(horizontalAlignment = AbsoluteAlignment.Right) {
            Text(
                text = transactionModel.title,
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = transactionModel.amount.toFormatCurrency(),
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}

@Composable
private fun DetailContentView(state: TransactionState) {
    val transactionModel = state.transaction
    ColumnWrapper(modifier = Modifier.padding(16.dp)) {
        ColumnText(title = "Date", value = transactionModel.formattedDate)
        ColumnText(title = "Category", value = transactionModel.category.name)
        ColumnText(title = "Type", value = transactionModel.type.name)
    }
}

@Composable
private fun NoteContentView(note: String) {
    ColumnWrapper(modifier = Modifier.padding(16.dp)) {
        Text(
            "Note",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.primary
        )
        Text(
            text = note,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.outline
        )
    }
}

@Composable
private fun ColumnText(title: String, value: String) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.primary
        )
        Text(
            text = value,
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.outline
        )
    }
}

@Composable
@Preview
private fun TransactionScreenPreview() {
    val transactionModel = TransactionModel(
        id = 6644,
        title = "Indomie",
        type = TransactionType.Expense,
        dateCreated = 1729655383,
        amount = 1000.0,
        note = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua."
    )
    AppTheme {
        TransactionScreen(uiState = TransactionState(transaction = transactionModel)) {

        }
    }
}

