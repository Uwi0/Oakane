package com.kakapo.oakane.presentation.feature.goals.component

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
import com.kakapo.oakane.presentation.viewModel.goals.GoalsEvent
import com.kakapo.oakane.presentation.viewModel.goals.GoalsState

@Composable
internal fun GoalsTopAppbarView(uiState: GoalsState, onEvent: (GoalsEvent) -> Unit) {
    Column {
        CustomNavigationMenuTopAppBarView(
            title = "Goals",
            showDrawer = uiState.showDrawer,
            shadowElevation = 0.dp,
            onNavigateBack = { onEvent.invoke(GoalsEvent.NavigateBack) },
            openMenu = { onEvent.invoke(GoalsEvent.OpenDrawer) }
        )
        SearchTextFieldView(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            value = uiState.searchQuery,
            placeholder = "Search Goal...",
            onValueChange = { query -> onEvent.invoke(GoalsEvent.FilterBy(query)) }
        )
        Spacer(modifier = Modifier.padding(vertical = 8.dp))
        HorizontalDivider()
    }
}

@Composable
@Preview
private fun GoalsTopAppbarViewPreview() {
    AppTheme {
        Surface {
            GoalsTopAppbarView(uiState = GoalsState(), onEvent = {})
        }
    }
}