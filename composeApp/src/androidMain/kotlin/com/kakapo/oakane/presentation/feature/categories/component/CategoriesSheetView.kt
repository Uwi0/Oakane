package com.kakapo.oakane.presentation.feature.categories.component

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.kakapo.oakane.presentation.feature.categories.component.sheet.CreateCategoryContentView
import com.kakapo.oakane.presentation.model.CategoriesSheetContent
import com.kakapo.oakane.presentation.viewModel.categories.CategoriesEvent
import com.kakapo.oakane.presentation.viewModel.categories.CategoriesState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoriesSheetView(
    sheetState: SheetState,
    uiState: CategoriesState,
    onEvent: (CategoriesEvent) -> Unit
) {
    ModalBottomSheet(
        sheetState = sheetState,
        onDismissRequest = { onEvent.invoke(CategoriesEvent.ShowSheet(visibility = false)) }
    ) {
        when(uiState.sheetContent) {
            CategoriesSheetContent.Create -> CreateCategoryContentView(uiState, onEvent)
            CategoriesSheetContent.SelectColor -> Text("Todo Need Some implement")
        }
    }
}
