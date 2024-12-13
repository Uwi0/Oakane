package com.kakapo.oakane.presentation.feature.home.component

import android.net.Uri
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kakapo.oakane.R
import com.kakapo.oakane.common.toFormatIDR
import com.kakapo.oakane.model.wallet.WalletModel
import com.kakapo.oakane.presentation.designSystem.component.button.CustomIconButton
import com.kakapo.oakane.presentation.designSystem.component.image.CustomDynamicAsyncImage
import com.kakapo.oakane.presentation.designSystem.theme.AppTheme
import com.kakapo.oakane.presentation.ui.component.ColumnWrapper

@Composable
internal fun WalletBalanceView(walletModel: WalletModel, onClick: () -> Unit) {
    ColumnWrapper(modifier = Modifier.padding(16.dp), onClick = onClick) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            WalletIcon(modifier = Modifier)
            Text(text = walletModel.name, style = MaterialTheme.typography.titleMedium,)
        }
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp), verticalAlignment = Alignment.CenterVertically) {
            Text(
                modifier = Modifier.align(Alignment.Top),
                text = "Rp",
                style = MaterialTheme.typography.labelMedium
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
private fun WalletIcon(modifier: Modifier) {
    val imageUrl by remember { mutableStateOf<Uri?>(null) }
    CustomDynamicAsyncImage(
        imageUrl = imageUrl,
        modifier = modifier
            .size(30.dp)
            .clip(CircleShape),
        placeholder = painterResource(R.drawable.mona_empty_wallet)
    )
}

@Composable
@Preview
private fun WalletBalancePreview() {
    AppTheme {
        WalletBalanceView(WalletModel()){

        }
    }
}