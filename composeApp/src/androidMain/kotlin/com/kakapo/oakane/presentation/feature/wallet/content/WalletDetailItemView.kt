package com.kakapo.oakane.presentation.feature.wallet.content

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.kakapo.model.toFormatNumber
import com.kakapo.model.wallet.WalletItemModel
import com.kakapo.oakane.presentation.ui.component.ColumnWrapper
import com.kakapo.oakane.presentation.ui.component.wallet.BudgetItemView
import com.kakapo.oakane.presentation.ui.component.wallet.PrimaryWalletIcon

@Composable
internal fun WalletDetailItemView(item: WalletItemModel) {
    ColumnWrapper(modifier = Modifier.padding(vertical = 12.dp, horizontal = 16.dp)) {
        WalletDetailTopContent(item = item)
        HorizontalDivider()
        WalletDetailBottomContent(item = item)
    }
}

@Composable
private fun WalletDetailTopContent(item: WalletItemModel) {
    val balance = item.balance.toFormatNumber(item.currency)
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            PrimaryWalletIcon(item.icon, item.iconName)
            Text(text = item.name, style = MaterialTheme.typography.titleMedium)
        }
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier.align(Alignment.Top),
                text = item.currency.symbol,
                style = MaterialTheme.typography.labelLarge
            )
            Text(
                text = balance,
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.primary,
            )
        }
    }
}

@Composable
private fun WalletDetailBottomContent(item: WalletItemModel) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        BudgetItemView(amount = item.income, isExpense = false, currency = item.currency)
        VerticalDivider(Modifier.height(48.dp))
        BudgetItemView(amount = item.expense, isExpense = true, currency = item.currency)
    }
}