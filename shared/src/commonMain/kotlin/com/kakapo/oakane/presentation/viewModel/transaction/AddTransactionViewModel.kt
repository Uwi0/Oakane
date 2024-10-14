package com.kakapo.oakane.presentation.viewModel.transaction

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kakapo.oakane.data.model.TransactionParam
import com.kakapo.oakane.data.repository.base.TransactionRepository
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent

class AddTransactionViewModel(
    private val repository: TransactionRepository
): ViewModel(), KoinComponent {

    fun save(transaction: TransactionParam) = viewModelScope.launch {
        repository.save(transaction)
    }
}