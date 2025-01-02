package com.kakapo.oakane.presentation.feature.addTransaction.component

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.kakapo.model.category.CategoryModel
import com.kakapo.model.transaction.TransactionType
import com.kakapo.oakane.presentation.designSystem.animation.slidingContentAnimation
import com.kakapo.oakane.presentation.designSystem.component.tab.CustomTabRowView
import com.kakapo.oakane.presentation.designSystem.component.tab.CustomTabView
import com.kakapo.oakane.presentation.designSystem.component.textField.SearchTextFieldView
import com.kakapo.oakane.presentation.ui.component.item.category.CategoryItemView
import com.kakapo.oakane.presentation.viewModel.addTransaction.AddTransactionEvent
import com.kakapo.oakane.presentation.viewModel.addTransaction.AddTransactionState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun SelectCategorySheet(
    sheetState: SheetState,
    uiState: AddTransactionState,
    onEvent: (AddTransactionEvent) -> Unit
) {

    var query by remember { mutableStateOf("") }
    var categories by remember { mutableStateOf(uiState.categories) }

    LaunchedEffect(query) {
        categories = uiState.categories.filter { it.name.contains(query, ignoreCase = true) }
    }

    ModalBottomSheet(
        sheetState = sheetState,
        onDismissRequest = { onEvent.invoke(AddTransactionEvent.Sheet(shown = false)) }
    ) {
        Column(modifier = Modifier.heightIn(max = 640.dp)) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(text = "Categories")
                SearchTextFieldView(
                    modifier = Modifier.fillMaxWidth(),
                    value = query,
                    placeHolder = "Search Categories...",
                    onValueChange = { query = it }
                )
                SelectCategoryTabView(uiState, onEvent)
            }
            SelectCategoriesContentView(
                tab = uiState.transactionType.ordinal,
                categories = categories,
                onEvent = onEvent
            )
            Spacer(Modifier.size(24.dp))
        }

    }
}

@Composable
private fun SelectCategoryTabView(
    uiState: AddTransactionState,
    onEvent: (AddTransactionEvent) -> Unit
) {
    CustomTabRowView(
        selectedTabIndex = uiState.transactionType.ordinal,
        tabWidth = 180.dp
    ) {
        TransactionType.entries.forEach { tab ->
            val selected = uiState.transactionType == tab
            CustomTabView(
                selected = selected,
                onClick = { onEvent.invoke(AddTransactionEvent.ChangeType(tab)) }
            ) {
                Text(tab.name)
            }
        }
    }
}

@Composable
private fun SelectCategoriesContentView(
    tab: Int,
    categories: List<CategoryModel>,
    onEvent: (AddTransactionEvent) -> Unit
) {
    AnimatedContent(
        targetState = tab,
        transitionSpec = { slidingContentAnimation() },
        label = "CategoriesContentViewAnimation",
    ) { selectedTab ->
        val selectedCategories = categories.filter { it.type.ordinal == selectedTab }
        LazyColumn(
            contentPadding = PaddingValues(vertical = 24.dp, horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(selectedCategories, key = { it.id }) { category ->
                CategoryItemView(
                    category,
                    onEvent = { onEvent.invoke(AddTransactionEvent.SetCategory(value = category)) }
                )
            }
        }
    }
}