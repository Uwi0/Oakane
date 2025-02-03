package com.kakapo.model.wallet

sealed class FilterWalletLogByDateModel {
    data object PastWeek : FilterWalletLogByDateModel()
    data object PastMonth : FilterWalletLogByDateModel()
    data class Custom(val startDate: Long, val endDate: Long) : FilterWalletLogByDateModel()
}