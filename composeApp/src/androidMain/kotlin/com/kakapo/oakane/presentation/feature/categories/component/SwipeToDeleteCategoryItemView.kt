package com.kakapo.oakane.presentation.feature.categories.component

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import com.kakapo.oakane.model.category.CategoryModel
import com.kakapo.oakane.presentation.ui.component.SwipeToDeleteBackgroundView
import com.kakapo.oakane.presentation.ui.component.item.category.CategoryItemView
import com.kakapo.oakane.presentation.viewModel.categories.CategoriesEvent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun SwipeToDeleteCategoryView(
    item: CategoryModel,
    onEvent: (CategoriesEvent) -> Unit
) {
    val handleSweep = { onEvent.invoke(CategoriesEvent.SwipeToDeleteBy(item.id)) }
    val dismissState = rememberSwipeToDismissBoxState(
        confirmValueChange = {
            when (it) {
                SwipeToDismissBoxValue.StartToEnd -> handleSweep()
                SwipeToDismissBoxValue.EndToStart -> handleSweep()
                SwipeToDismissBoxValue.Settled -> return@rememberSwipeToDismissBoxState false
            }
            return@rememberSwipeToDismissBoxState true
        },
        positionalThreshold = { it * .25f }
    )

    SwipeToDismissBox(
        state = dismissState,
        backgroundContent = { SwipeToDeleteBackgroundView(dismissState) },
        content = {
            CategoryItemView(item, onEvent = { onEvent.invoke(CategoriesEvent.OnTapped(item)) })
        }
    )
}