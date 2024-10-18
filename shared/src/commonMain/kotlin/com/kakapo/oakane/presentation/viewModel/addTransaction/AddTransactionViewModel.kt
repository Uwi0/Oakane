package com.kakapo.oakane.presentation.viewModel.addTransaction

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.touchlab.kermit.Logger
import com.kakapo.oakane.common.toDateWith
import com.kakapo.oakane.data.model.TransactionParam
import com.kakapo.oakane.data.repository.base.TransactionRepository
import com.kakapo.oakane.model.transaction.TransactionModel
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent

class AddTransactionViewModel(
    private val repository: TransactionRepository
): ViewModel(), KoinComponent {

    fun save(transaction: TransactionParam) = viewModelScope.launch {
        repository.save(transaction)
    }
}