package com.kakapo.model.wallet

import com.kakapo.common.getCurrentUnixTime

sealed class FilterWalletLogByDateModel(val id: String, val title: String = id) {
    data object All : FilterWalletLogByDateModel(id = "all", title = "All")
    data object PastWeek : FilterWalletLogByDateModel(id = "past_week", title = "Past Week")
    data object PastMonth : FilterWalletLogByDateModel(id = "past_month", title = "Past Month")
    data class Custom(
        val startDate: Long,
        val endDate: Long
    ) : FilterWalletLogByDateModel(id = "custom", title = "Custom")

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