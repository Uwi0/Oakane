package com.kakapo.oakane.presentation.feature.transaction

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DeleteOutline
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.outlined.Event
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.kakapo.common.getImageUriFromFileName
import com.kakapo.common.getSavedImageUri
import com.kakapo.common.showToast
import com.kakapo.model.category.CategoryIconName
import com.kakapo.model.toFormatCurrency
import com.kakapo.model.transaction.TransactionModel
import com.kakapo.model.transaction.TransactionType
import com.kakapo.oakane.presentation.designSystem.component.button.CustomIconButton
import com.kakapo.oakane.presentation.designSystem.component.image.CustomDynamicAsyncImage
import com.kakapo.oakane.presentation.designSystem.component.topAppBar.CustomNavigationTopAppBarView
import com.kakapo.oakane.presentation.designSystem.theme.AppTheme
import com.kakapo.oakane.presentation.ui.component.ColumnWrapper
import com.kakapo.oakane.presentation.ui.component.TransactionTypeIcon
import com.kakapo.oakane.presentation.ui.model.asIcon
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

    LaunchedEffect(Unit) {
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
                ImageTransaction(uiState.transaction.imageFileName)
            }

        }
    )
}

@Composable
private fun TopContentView(state: TransactionState) {
    val transactionModel = state.transaction
    val currency = state.currency
    val formattedAmount = transactionModel.amount.toFormatCurrency(currency)
    val amount = if (transactionModel.type == TransactionType.Income) formattedAmount
    else "-$formattedAmount"
    val textColor = if (transactionModel.type == TransactionType.Income)
        MaterialTheme.colorScheme.primary
    else {
        MaterialTheme.colorScheme.error
    }
    ColumnWrapper(
        modifier = Modifier.padding(vertical = 12.dp, horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = amount, color = textColor, style = MaterialTheme.typography.displayMedium)
        Text(
            text = transactionModel.title,
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.outline
        )
    }
}

@Composable
private fun DetailContentView(state: TransactionState) {
    val transactionModel = state.transaction

    ColumnWrapper(modifier = Modifier.padding(16.dp)) {
        RowText(
            title = "Wallet",
            value = state.wallet.name,
            iconContent = {
                TrailingIcon(
                    state.wallet.isDefaultIcon,
                    state.wallet.iconName,
                    state.wallet.icon
                )
            }
        )
        HorizontalDivider()
        RowText(
            title = "Category",
            value = transactionModel.category.name,
            iconContent = {
                TrailingIcon(
                    transactionModel.category.isDefault,
                    transactionModel.category.iconName,
                    transactionModel.category.icon
                )
            }
        )
        HorizontalDivider()
        RowText(
            title = "Type",
            value = transactionModel.type.name,
            iconContent = {
                TransactionTypeIcon(
                    type = transactionModel.type,
                    iconSize = 18.dp
                )
            }
        )
        HorizontalDivider()
        RowText(
            title = "Date",
            value = transactionModel.formattedDate,
            iconContent = {
                Icon(
                    modifier = Modifier.size(18.dp),
                    imageVector = Icons.Outlined.Event,
                    contentDescription = null
                )
            }
        )
    }
}

@Composable
private fun TrailingIcon(
    isDefaultIcon: Boolean,
    categoryIcon: CategoryIconName,
    fileName: String
) {
    val context = LocalContext.current
    val icon = context.getSavedImageUri(fileName).getOrNull()
    if (isDefaultIcon) {
        Icon(
            modifier = Modifier.size(18.dp),
            painter = painterResource(id = categoryIcon.asIcon()),
            contentDescription = null
        )
    } else {
        CustomDynamicAsyncImage(
            imageUrl = icon,
            modifier = Modifier.size(18.dp),
            contentScale = ContentScale.FillBounds
        )
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
private fun RowText(title: String, value: String, iconContent: @Composable () -> Unit = {}) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.outline
        )
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = value,
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.onSurface
            )
            iconContent.invoke()
        }
    }
}

@Composable
private fun ImageTransaction(imageUrl: String) {
    val context = LocalContext.current
    val imageUri = context.getImageUriFromFileName(imageUrl).getOrNull()
    if (imageUri != null) {
        CustomDynamicAsyncImage(
            imageUrl = imageUri,
            modifier = Modifier
                .fillMaxWidth()
                .height(256.dp)
                .clip(MaterialTheme.shapes.small),
            contentScale = ContentScale.Crop
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

