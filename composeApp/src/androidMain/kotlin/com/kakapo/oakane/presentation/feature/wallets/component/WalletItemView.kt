package com.kakapo.oakane.presentation.feature.wallets.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kakapo.common.toColorLong
import com.kakapo.model.Currency
import com.kakapo.model.system.Theme
import com.kakapo.model.toFormatCurrency
import com.kakapo.model.wallet.WalletItemModel
import com.kakapo.oakane.presentation.designSystem.component.button.OutlinedCheckmarkRadioButton
import com.kakapo.oakane.presentation.designSystem.theme.AppTheme
import com.kakapo.oakane.presentation.ui.component.ColumnWrapper
import com.kakapo.oakane.presentation.ui.component.SelectedIconModel
import com.kakapo.oakane.presentation.ui.component.SelectedIconView
import com.kakapo.oakane.presentation.viewModel.wallets.WalletsEvent

@Composable
internal fun WalletItemView(
    wallet: WalletItemModel,
    theme: Theme,
    onEvent: (WalletsEvent) -> Unit
) {
    val currency = wallet.currency
    ColumnWrapper(
        modifier = Modifier.padding(vertical = 12.dp, horizontal = 16.dp),
        theme = theme,
        horizontalAlignment = Alignment.CenterHorizontally,
        onClick = { onEvent.invoke(WalletsEvent.ClickedWallet(wallet)) }
    ) {
        WalletTopContent(wallet = wallet, onEvent = onEvent)
        Text(
            text = wallet.balance.toFormatCurrency(currency),
            style = MaterialTheme.typography.headlineMedium
        )
        HorizontalDivider()
        WalletBottomContent(wallet = wallet)
    }
}

@Composable
private fun WalletTopContent(wallet: WalletItemModel, onEvent: (WalletsEvent) -> Unit) {
    val selectedIcon = SelectedIconModel(
        imageFile = wallet.icon,
        defaultIcon = wallet.iconName,
        color = wallet.color.ifEmpty { "0xFF4CAF50" }.toColorLong()
    )
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        SelectedIconView(selectedIcon = selectedIcon, onClick = {})
        Text(text = wallet.name, style = MaterialTheme.typography.titleMedium)
        Spacer(modifier = Modifier.weight(1f))
        OutlinedCheckmarkRadioButton(
            selected = wallet.isSelected,
            onClick = { onEvent.invoke(WalletsEvent.SelectPrimaryWalletBy(wallet.id)) },
        )
    }
}

@Composable
private fun WalletBottomContent(wallet: WalletItemModel) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Spacer(Modifier.weight(1f))
        BalanceContent(
            title = "Income This Month",
            amount = wallet.expense,
            color = MaterialTheme.colorScheme.primary,
            currency = wallet.currency
        )
        VerticalDivider(Modifier.height(40.dp), thickness = 2.dp)
        BalanceContent(
            title = "Expense This Month",
            amount = wallet.income,
            color = MaterialTheme.colorScheme.error,
            currency = wallet.currency
        )
        Spacer(Modifier.weight(1f))
    }
}

@Composable
private fun BalanceContent(title: String, amount: Double, color: Color, currency: Currency) {
    Column(
        modifier = Modifier
            .sizeIn(minWidth = 120.dp)
            .padding(top = 8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(title, color = color, style = MaterialTheme.typography.labelMedium)
        Text(text = amount.toFormatCurrency(currency))
    }
}


@Preview
@Composable
private fun WalletItemPreview() {
    AppTheme {
        WalletItemView(
            wallet = WalletItemModel(
                id = 2486,
                name = "Alfonso Gibbs",
                isDefault = false,
                icon = "fringilla",
                currency = Currency.IDR,
                balance = 20_000_000.0,
                income = 50_000.0,
                expense = 50_000.0,
                isSelected = false
            ),
            theme = Theme.Light
        ) {

        }
    }

}