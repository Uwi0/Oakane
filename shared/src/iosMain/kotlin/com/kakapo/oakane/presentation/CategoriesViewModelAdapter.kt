package com.kakapo.oakane.presentation

import com.kakapo.oakane.model.category.CategoryModel
import com.kakapo.oakane.presentation.viewModel.categories.CategoriesViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class CategoriesViewModelAdapter (
    private val viewModel: CategoriesViewModel,
    private val scope: CoroutineScope
){

    init {
        viewModel.initializeData()
    }

    fun observeData(onStateChange: (List<CategoryModel>) -> Unit) {
        scope.launch {
            viewModel.categories.collect { categories ->
                onStateChange.invoke(categories)
            }
        }
    }
}