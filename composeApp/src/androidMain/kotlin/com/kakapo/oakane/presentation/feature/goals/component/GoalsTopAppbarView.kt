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
import com.kakapo.oakane.presentation.designSystem.component.topAppBar.CustomNavigationTopAppBarView
import com.kakapo.oakane.presentation.designSystem.theme.AppTheme

@Composable
internal fun GoalsTopAppbarView() {
    Column {
        CustomNavigationTopAppBarView(
            title = "Goals",
            shadowElevation = 0.dp,
            onNavigateBack = {}
        )
        SearchTextFieldView(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
            value = "",
            placeHolder = "Search Goal...",
            onValueChange = {}
        )
        Spacer(modifier = Modifier.padding(vertical = 16.dp))
        HorizontalDivider()
    }
}

@Composable
@Preview
private fun GoalsTopAppbarViewPreview() {
    AppTheme {
        Surface {
            GoalsTopAppbarView()
        }
    }
}