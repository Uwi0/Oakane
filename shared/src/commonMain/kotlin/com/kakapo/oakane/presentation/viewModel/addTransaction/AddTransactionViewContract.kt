package com.kakapo.oakane.presentation.viewModel.addTransaction




sealed class AddTransactionEvent {
    data class ChangedTitle(val value: String): AddTransactionEvent()
    data class ChangedAmount(val value: String): AddTransactionEvent()
    data class ChangeNote(val value: String): AddTransactionEvent()
}