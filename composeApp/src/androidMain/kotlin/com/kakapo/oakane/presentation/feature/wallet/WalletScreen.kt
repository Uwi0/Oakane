package com.kakapo.oakane.presentation.feature.wallet

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kakapo.model.wallet.WalletItemModel
import com.kakapo.oakane.presentation.designSystem.component.button.CustomIconButton
import com.kakapo.oakane.presentation.designSystem.component.topAppBar.CustomNavigationTopAppBarView
import com.kakapo.oakane.presentation.designSystem.theme.AppTheme
import com.kakapo.oakane.presentation.feature.wallet.content.WalletDetailItemView
import com.kakapo.oakane.presentation.ui.component.item.CardNoteView

@Composable
internal fun WalletRoute(walletId: Long) {
    WalletScreen()
}

@Composable
private fun WalletScreen() {
    Scaffold(
        topBar = { WalletScreenTopAppBar() },
        content = { paddingValues ->
            WalletContentView(
                modifier = Modifier
                    .padding(paddingValues)
                    .padding(vertical = 24.dp, horizontal = 16.dp)
            )
        }
    )
}

@Composable
private fun WalletContentView(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        WalletDetailItemView(item = WalletItemModel())
        CardNoteView(note = "This is a note")
    }
}

@Composable
private fun WalletScreenTopAppBar() {
    CustomNavigationTopAppBarView(
        title = "Wallet",
        actions = {
            CustomIconButton(icon = Icons.Outlined.Edit) { }
            CustomIconButton(icon = Icons.Outlined.Delete) { }
        },
        onNavigateBack = {}
    )
}

@Preview
@Composable
private fun WalletScreenPreview() {
    AppTheme {
        WalletScreen()
    }
}