package com.kakapo.model.wallet

import com.kakapo.common.getCurrentUnixTime

sealed class FilterWalletLogByDateModel(val id: String) {
    data object All : FilterWalletLogByDateModel(id = "all")
    data object PastWeek : FilterWalletLogByDateModel(id = "past_week")
    data object PastMonth : FilterWalletLogByDateModel(id = "past_month")
    data class Custom(
        val startDate: Long,
        val endDate: Long
    ) : FilterWalletLogByDateModel(id = "custom")

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

    companion object {
        fun filters() = listOf(
            All,
            PastWeek,
            PastMonth,
            Custom(getCurrentUnixTime(), getCurrentUnixTime())
        )
    }
}