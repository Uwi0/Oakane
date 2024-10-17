package com.kakapo.oakane.presentation.feature.transactions.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.InputChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.kakapo.oakane.model.transaction.TransactionType
import com.kakapo.oakane.presentation.designSystem.component.button.CustomButton
import com.kakapo.oakane.presentation.feature.transactions.TransactionsUiState

@OptIn(ExperimentalLayoutApi::class)
@Composable
internal fun FilterTypeView(state: TransactionsUiState) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        FlowRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            TransactionType.entries.forEach { type ->
                val isNotNull = state.selectedType != null
                val isSelected = state.selectedType == type
                InputChip(
                    label = { Text(type.name) },
                    selected = isNotNull && isSelected,
                    onClick = { state.changeFilterType(type) }
                )
            }
        }

        CustomButton(
            modifier = Modifier.fillMaxWidth(),
            onClick = state::hideBottomSheet,
            content = { Text("Apply Filter Type") }
        )
    }
}