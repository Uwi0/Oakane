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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.kakapo.oakane.R
import com.kakapo.oakane.model.transaction.TransactionType
import com.kakapo.oakane.presentation.designSystem.component.button.CustomButton
import com.kakapo.oakane.presentation.feature.categories.component.CategoryIconView
import com.kakapo.oakane.presentation.viewModel.categories.CategoriesEvent
import com.kakapo.oakane.presentation.viewModel.categories.CategoriesUiState


@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun CreateCategoryContentView(
    uiState: CategoriesUiState,
    onEvent: (CategoriesEvent) -> Unit
) {
    Column(
        modifier = Modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        TitleView(text = "Create Category")
        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Surface(color = MaterialTheme.colorScheme.primary, shape = CircleShape) {
                Icon(
                    modifier = Modifier.padding(12.dp),
                    painter = painterResource(R.drawable.ic_outline_account_balance_wallet),
                    contentDescription = null
                )
            }
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
        TitleView(text = "Category Type")
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
        TitleView(text = "Category Color")
        LazyRow(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            items(uiState.categoriesColor) { colorHex ->
                CategoryIconView(icon = R.drawable.ic_empty, color = Color(colorHex))
            }
        }
        CustomButton(
            modifier = Modifier.fillMaxWidth(),
            text = { Text(text = "Create") },
            onClick = {}
        )
        Spacer(Modifier.size(8.dp))
    }
}

@Composable
private fun TitleView(text: String) {
    Text(text = text, style = MaterialTheme.typography.titleMedium)
}


