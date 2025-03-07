package com.kakapo.oakane.presentation.feature.transactions.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.kakapo.oakane.presentation.designSystem.component.button.CustomButton
import com.kakapo.oakane.presentation.ui.component.item.category.CategoryItemView
import com.kakapo.oakane.presentation.viewModel.transactions.TransactionsEvent
import com.kakapo.oakane.presentation.viewModel.transactions.TransactionsState

@Composable
internal fun FilterCategoryView(
    state: TransactionsState,
    onEvent: (TransactionsEvent) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(max = 640.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(vertical = 24.dp, horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(items = state.categories, key = { it.id }) { category ->
                CategoryItemView(
                    category = category,
                    theme = state.theme,
                    onEvent = { onEvent.invoke(TransactionsEvent.FilterByCategory(value = category))}
                )
            }
        }
        CustomButton(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            onClick = { onEvent.invoke(TransactionsEvent.ShowSheet(false)) },
            content = { Text("Apply filter Category") }
        )
    }
}