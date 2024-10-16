package com.kakapo.oakane.presentation

import com.kakapo.oakane.data.repository.base.TransactionRepository
import com.kakapo.oakane.model.transaction.TransactionModel
import com.kakapo.oakane.presentation.viewModel.home.HomeViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class IOSHomeViewModel : KoinComponent {
    private val transactionRepository: TransactionRepository by inject()
    private val viewModel: HomeViewModel = HomeViewModel(transactionRepository)
    private val scope: CoroutineScope = MainScope()

    val state: List<TransactionModel> = viewModel.transactions.value

    fun initViewModel() = viewModel.initializeData()

    fun observeState(onStateChange: (List<TransactionModel>) -> Unit) {
        scope.launch {
            viewModel.transactions.collect { transactions ->
                onStateChange.invoke(transactions)
            }
        }
    }

}