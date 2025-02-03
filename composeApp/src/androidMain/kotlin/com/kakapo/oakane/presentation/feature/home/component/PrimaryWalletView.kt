package com.kakapo.oakane.presentation.feature.home.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kakapo.common.maskText
import com.kakapo.model.toFormatNumber
import com.kakapo.oakane.presentation.designSystem.component.button.CustomIconButton
import com.kakapo.oakane.presentation.designSystem.theme.AppTheme
import com.kakapo.oakane.presentation.ui.component.ColumnWrapper
import com.kakapo.oakane.presentation.ui.component.wallet.PrimaryWalletIcon
import com.kakapo.oakane.presentation.viewModel.home.HomeEvent
import com.kakapo.oakane.presentation.viewModel.home.HomeState

@Composable
internal fun PrimaryWalletView(uiState: HomeState, onEvent: (HomeEvent) -> Unit) {
    val walletModel = uiState.wallet
    val balance = walletModel.balance.toFormatNumber(walletModel.currency)
    val maskBalance = if (uiState.isBalanceVisible) balance else balance.maskText()
    val hideIcon =
        if (uiState.isBalanceVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff

    ColumnWrapper(
        modifier = Modifier.padding(16.dp),
        onClick = { onEvent.invoke(HomeEvent.ToWallets) }) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            PrimaryWalletIcon(walletModel.icon, walletModel.iconName, color = walletModel.color)
            Text(text = walletModel.name, style = MaterialTheme.typography.titleMedium)
        }
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier.align(Alignment.Top),
                text = walletModel.currency.symbol,
                style = MaterialTheme.typography.labelLarge
            )
            Text(
                text = maskBalance,
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.primary,
            )
            CustomIconButton(
                icon = hideIcon,
                onClick = { onEvent.invoke(HomeEvent.ChangeBalanceVisibility) },
            )
        }
    }
}


@Composable
@Preview
private fun WalletBalancePreview() {
    AppTheme {
        PrimaryWalletView(uiState = HomeState(), onEvent = {

        })
    }
}