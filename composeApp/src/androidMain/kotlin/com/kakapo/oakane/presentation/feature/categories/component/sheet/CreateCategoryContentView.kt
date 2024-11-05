package com.kakapo.oakane.presentation.feature.categories.component.sheet

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.kakapo.oakane.R
import com.kakapo.oakane.common.toColorInt
import com.kakapo.oakane.model.transaction.TransactionType
import com.kakapo.oakane.presentation.designSystem.component.button.CustomButton
import com.kakapo.oakane.presentation.feature.categories.component.CategoryIconView
import com.kakapo.oakane.presentation.model.CategoriesSheetContent
import com.kakapo.oakane.presentation.ui.model.asIcon
import com.kakapo.oakane.presentation.viewModel.categories.CategoriesEvent
import com.kakapo.oakane.presentation.viewModel.categories.CategoriesState


@Composable
fun CreateCategoryContentView(
    uiState: CategoriesState,
    onEvent: (CategoriesEvent) -> Unit
) {
    Column(
        modifier = Modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        TitleView(text = "Create Category")
        CategoryNameFieldView(uiState, onEvent)
        TitleView(text = "Category Type")
        SegmentTransactionTypeView(uiState, onEvent)
        TitleView(text = "Category Color")
        CategoryColorSelectionView(uiState, onEvent)
        CustomButton(
            modifier = Modifier.fillMaxWidth(),
            text = { Text(text = "Create") },
            onClick = { onEvent.invoke(CategoriesEvent.SaveCategory)}
        )
        Spacer(Modifier.size(8.dp))
    }
}

@Composable
private fun TitleView(text: String) {
    Text(text = text, style = MaterialTheme.typography.titleMedium)
}

@Composable
private fun CategoryNameFieldView(
    uiState: CategoriesState,
    onEvent: (CategoriesEvent) -> Unit
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        CategoryIconView(
            icon = uiState.selectedIcon.asIcon(),
            color = Color(uiState.defaultSelectedColor),
            onClick = {
                onEvent.invoke(CategoriesEvent.ChangeSheet(CategoriesSheetContent.SelectIcon))
            }
        )
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = uiState.categoryName,
            onValueChange = { categoryName ->
                onEvent.invoke(CategoriesEvent.ChangeCategory(categoryName))
            },
            shape = MaterialTheme.shapes.medium,
            placeholder = { Text(text = "Category Name") }
        )
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun SegmentTransactionTypeView(
    uiState: CategoriesState,
    onEvent: (CategoriesEvent) -> Unit
) {
    SingleChoiceSegmentedButtonRow(modifier = Modifier.fillMaxWidth()) {
        TransactionType.entries.forEachIndexed { index, type ->
            val isSelected = uiState.selectedType == type
            SegmentedButton(
                shape = SegmentedButtonDefaults.itemShape(index, TransactionType.entries.size),
                onClick = { onEvent.invoke(CategoriesEvent.Selected(type)) },
                selected = isSelected
            ) {
                Text(text = type.name)
            }
        }
    }
}

@Composable
private fun CategoryColorSelectionView(
    uiState: CategoriesState,
    onEvent: (CategoriesEvent) -> Unit
) {
    LazyRow(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
        item {
            CategoryIconView(
                icon = R.drawable.ic_rounded_brush,
                color = Color(uiState.defaultSelectedColor),
                onClick = {
                    onEvent.invoke(CategoriesEvent.ChangeSheet(CategoriesSheetContent.SelectColor))
                }
            )
        }
        items(uiState.categoriesColor) { hex ->
            CategoryIconView(
                icon = R.drawable.ic_empty,
                color = Color(hex.toColorInt()),
                onClick = { onEvent.invoke(CategoriesEvent.SelectedColor(hex)) }
            )
        }
    }
}