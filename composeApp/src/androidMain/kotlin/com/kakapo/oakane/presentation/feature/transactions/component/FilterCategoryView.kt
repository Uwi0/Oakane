package com.kakapo.oakane.presentation.feature.transactions.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.kakapo.oakane.presentation.designSystem.component.button.CustomButton
import com.kakapo.oakane.presentation.feature.transactions.TransactionsUiState

@Composable
internal fun FilterCategoryView(state: TransactionsUiState) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text("This feature is not implemented yet", style = MaterialTheme.typography.titleLarge)
        CustomButton(
            modifier = Modifier.fillMaxWidth(),
            onClick = { state.changeFilterCategory(selectedCategory = "") },
            content = { Text("Apply filter Category") }
        )
    }
}