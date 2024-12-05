package com.kakapo.oakane.presentation.feature.monthlyBudget.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.kakapo.oakane.presentation.viewModel.monthlyBudget.MonthlyBudgetEvent

@Composable
internal fun MonthlyBottomContentView(onEvent: (MonthlyBudgetEvent) -> Unit) {
    Column {
        LimitCategoryHeaderView(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
            onEvent = onEvent
        )
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 16.dp)
        ) {
            items(10) {
                LimitCategoryItemView()
            }
        }
    }

}