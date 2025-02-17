package com.kakapo.oakane.presentation.feature.wallets.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kakapo.oakane.presentation.designSystem.component.textField.SearchTextFieldView
import com.kakapo.oakane.presentation.designSystem.component.topAppBar.CustomNavigationMenuTopAppBarView
import com.kakapo.oakane.presentation.designSystem.theme.AppTheme
import com.kakapo.oakane.presentation.viewModel.wallets.WalletsEvent
import com.kakapo.oakane.presentation.viewModel.wallets.WalletsState

@Composable
internal fun WalletsTopAppbarView(uiState: WalletsState, event: (WalletsEvent) -> Unit) {
    Column {
        CustomNavigationMenuTopAppBarView(
            title = "Wallets",
            showDrawer = uiState.showDrawer,
            shadowElevation = 0.dp,
            onNavigateBack = { event.invoke(WalletsEvent.NavigateBack)},
            openMenu = { event.invoke(WalletsEvent.OpenDrawer) }
        )
        SearchTextFieldView(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
            value = uiState.searchQuery,
            placeholder = "Search Wallet...",
            onValueChange = { query -> event.invoke(WalletsEvent.OnSearchBy(query)) }
        )
        Spacer(modifier = Modifier.padding(vertical = 8.dp))
        HorizontalDivider()
    }
}

@Composable
@Preview
private fun WalletsTopAppbarViewPreview() {
    AppTheme {
        Surface {
            WalletsTopAppbarView(uiState = WalletsState(), event = {})
        }
    }
}