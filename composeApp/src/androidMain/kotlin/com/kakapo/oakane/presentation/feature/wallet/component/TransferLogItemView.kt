package com.kakapo.oakane.presentation.feature.wallet.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.SyncAlt
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kakapo.model.system.Theme
import com.kakapo.model.toFormatCurrency
import com.kakapo.model.wallet.WalletLogItem
import com.kakapo.model.wallet.WalletTransferModel
import com.kakapo.model.wallet.WalletTransferType
import com.kakapo.oakane.presentation.designSystem.component.button.CustomIconButton
import com.kakapo.oakane.presentation.designSystem.theme.AppTheme
import com.kakapo.oakane.presentation.ui.component.RowWrapper

@Composable
internal fun TransferLogItemView(item: WalletLogItem.WalletTransferLogItem, theme: Theme) {
    val transferLog = item.data
    RowWrapper(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp, horizontal = 16.dp),
        theme = theme,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        TransferLogIconView(transferLog.type)
        TransferLogContentView(walletTransfer = transferLog)
        Spacer(modifier = Modifier.weight(1f))
        TransferLogTrailingContent(transferLog.formattedDate)
    }
}

@Composable
private fun TransferLogIconView(transferType: WalletTransferType) {
    val color = when (transferType) {
        WalletTransferType.Outgoing -> MaterialTheme.colorScheme.error
        WalletTransferType.Incoming -> MaterialTheme.colorScheme.primary
    }
    Surface(shape = CircleShape, color = color, modifier = Modifier.size(48.dp)) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp)
        ) {
            Icon(
                modifier = Modifier.fillMaxSize(),
                imageVector = Icons.Outlined.SyncAlt,
                contentDescription = null
            )
        }
    }
}

@Composable
private fun TransferLogContentView(
    modifier: Modifier = Modifier,
    walletTransfer: WalletTransferModel
) {
    val title = when (walletTransfer.type) {
        WalletTransferType.Outgoing -> "Sent to ${walletTransfer.name}"
        WalletTransferType.Incoming -> "Received from ${walletTransfer.name}"
    }
    val textColor = when (walletTransfer.type) {
        WalletTransferType.Outgoing -> MaterialTheme.colorScheme.error
        WalletTransferType.Incoming -> MaterialTheme.colorScheme.primary
    }
    val amount = walletTransfer.amount.toFormatCurrency(walletTransfer.currency)
    Column(modifier = modifier, verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text(text = title, style = MaterialTheme.typography.titleMedium)
        Text(amount, style = MaterialTheme.typography.titleLarge, color = textColor)
    }
}

@Composable
private fun TransferLogTrailingContent(date: String) {
    val color = MaterialTheme.colorScheme.outline
    Column(horizontalAlignment = Alignment.End) {
        CustomIconButton(icon = Icons.Outlined.Info, onClick = {})
        Text(date, style = MaterialTheme.typography.labelMedium, color = color)
    }
}

@Composable
@Preview
private fun TransferLogIconPreview() {
    AppTheme {
        TransferLogIconView(transferType = WalletTransferType.Outgoing)
    }
}