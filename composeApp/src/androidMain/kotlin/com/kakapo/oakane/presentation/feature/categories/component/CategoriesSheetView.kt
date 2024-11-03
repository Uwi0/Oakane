package com.kakapo.oakane.presentation.feature.categories.component

import androidx.compose.animation.AnimatedContent
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.runtime.Composable
import com.kakapo.oakane.presentation.feature.categories.component.sheet.CreateCategoryContentView
import com.kakapo.oakane.presentation.feature.categories.component.sheet.SelectCategoryColorView
import com.kakapo.oakane.presentation.feature.categories.component.sheet.SelectCategoryIconView
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
        AnimatedContent(targetState = uiState.sheetContent, label = "CategoriesSheetView") { content ->
            when(content) {
                CategoriesSheetContent.Create -> CreateCategoryContentView(uiState, onEvent)
                CategoriesSheetContent.SelectColor -> SelectCategoryColorView(uiState, onEvent)
                CategoriesSheetContent.SelectIcon -> SelectCategoryIconView(onEvent)
            }
        }
    }
}
