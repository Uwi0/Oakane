package com.kakapo.model.wallet

import com.kakapo.common.getCurrentUnixTime

sealed class FilterWalletLogByDateModel {
    data object All : FilterWalletLogByDateModel()
    data object PastWeek : FilterWalletLogByDateModel()
    data object PastMonth : FilterWalletLogByDateModel()
    data class Custom(val startDate: Long, val endDate: Long) : FilterWalletLogByDateModel()

    fun asStartDate(): Long {
        return when(this) {
            is Custom -> startDate
            else -> getCurrentUnixTime()
        }
    }

    fun asEndDate(): Long {
        return when(this) {
            is Custom -> endDate
            else -> getCurrentUnixTime()
        }
    }
}