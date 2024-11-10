package com.kakapo.oakane.presentation

import com.kakapo.oakane.model.transaction.TransactionModel
import com.kakapo.oakane.presentation.viewModel.home.HomeViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class HomeViewModelAdapter(
    private val viewModel: HomeViewModel,
    private val scope: CoroutineScope
) {

    init {
        viewModel.initializeData()
    }

    fun initViewModel() = viewModel.initializeData()

    fun observeState(onStateChange: (List<TransactionModel>) -> Unit) {
        scope.launch {
            viewModel.uiState.collect { state ->
                onStateChange.invoke(state.transactions)
            }
        }
    }

}