package com.kakapo.oakane.presentation.feature.categories.component

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.runtime.Composable
import com.kakapo.oakane.presentation.feature.categories.component.sheet.CreateCategoryContentView
import com.kakapo.oakane.presentation.viewModel.categories.CategoriesEvent
import com.kakapo.oakane.presentation.viewModel.categories.CategoriesUiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoriesSheetView(
    sheetState: SheetState,
    uiState: CategoriesUiState,
    onEvent: (CategoriesEvent) -> Unit
) {
    ModalBottomSheet(
        sheetState = sheetState,
        onDismissRequest = { onEvent.invoke(CategoriesEvent.ShowSheet(visibility = false)) }
    ) {
        CreateCategoryContentView(uiState, onEvent)
    }
}
