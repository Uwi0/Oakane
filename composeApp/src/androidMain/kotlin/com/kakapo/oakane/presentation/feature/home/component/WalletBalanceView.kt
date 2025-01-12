package com.kakapo.oakane.presentation.feature.home.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kakapo.common.getSavedImageUri
import com.kakapo.common.toFormatIDR
import com.kakapo.model.wallet.WalletModel
import com.kakapo.oakane.R
import com.kakapo.oakane.presentation.designSystem.component.button.CustomIconButton
import com.kakapo.oakane.presentation.designSystem.component.image.CustomDynamicAsyncImage
import com.kakapo.oakane.presentation.designSystem.theme.AppTheme
import com.kakapo.oakane.presentation.ui.component.ColumnWrapper
import com.kakapo.oakane.presentation.ui.component.item.category.CategoryIconView
import com.kakapo.oakane.presentation.ui.model.asIcon

@Composable
internal fun WalletBalanceView(walletModel: WalletModel, onClick: () -> Unit) {
    ColumnWrapper(modifier = Modifier.padding(16.dp), onClick = onClick) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            WalletIcon(walletModel)
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
                text = walletModel.balance.toFormatIDR(),
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.primary,
            )
            CustomIconButton(
                icon = Icons.Default.Visibility,
                onClick = {},
            )
        }
    }
}

@Composable
private fun WalletIcon(walletModel: WalletModel) {
    if (walletModel.icon.contains("jpg")) {
        val context = LocalContext.current
        val imageUri = context.getSavedImageUri(walletModel.icon).getOrNull()
        val imageUrl by remember { mutableStateOf(imageUri) }
        CustomDynamicAsyncImage(
            imageUrl = imageUrl,
            modifier = Modifier
                .size(30.dp)
                .clip(CircleShape),
            placeholder = painterResource(R.drawable.mona_empty_wallet)
        )
    } else {
        CategoryIconView(
            icon = walletModel.iconName.asIcon(),
            color = MaterialTheme.colorScheme.primary,
            size = 30.dp
        )
    }

}

@Composable
@Preview
private fun WalletBalancePreview() {
    AppTheme {
        WalletBalanceView(WalletModel()) {

        }
    }
}