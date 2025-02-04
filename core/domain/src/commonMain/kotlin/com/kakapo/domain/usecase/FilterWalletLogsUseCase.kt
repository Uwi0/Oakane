package com.kakapo.domain.usecase

import com.kakapo.model.wallet.FilterWalletLogByCategory
import com.kakapo.model.wallet.FilterWalletLogByCategory.All
import com.kakapo.model.wallet.FilterWalletLogByCategory.GoalSavings
import com.kakapo.model.wallet.FilterWalletLogByCategory.Transaction
import com.kakapo.model.wallet.FilterWalletLogByCategory.Transfer
import com.kakapo.model.wallet.FilterWalletLogByDateModel
import com.kakapo.model.wallet.WalletLogItem
import kotlinx.datetime.Clock
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.TimeZone
import kotlinx.datetime.minus

fun List<WalletLogItem<*>>.filterWalletLogsBy(
    query: String,
    date: FilterWalletLogByDateModel,
    category: FilterWalletLogByCategory,
    clock: Clock = Clock.System // Default to system clock
): List<WalletLogItem<*>> {
    val filteredByQuery = filter { it.name.contains(query, ignoreCase = true) }
    val filteredByDate = filteredByQuery.filterBy(date, clock)
    val filteredByCategory = filteredByDate.filterBy(category)
    return filteredByCategory
}

private fun List<WalletLogItem<*>>.filterBy(
    date: FilterWalletLogByDateModel,
    clock: Clock
): List<WalletLogItem<*>> {
    return filter { item ->
        when (date) {
            is FilterWalletLogByDateModel.Custom -> item.dateCreated in date.startDate..date.endDate
            FilterWalletLogByDateModel.All -> true
            FilterWalletLogByDateModel.PastMonth -> item.dateCreated.inPastMonth(clock)
            FilterWalletLogByDateModel.PastWeek -> item.dateCreated.inPastWeek(clock)
        }
    }
}

private fun Long.inPastWeek(clock: Clock): Boolean {
    val now = clock.now().toEpochMilliseconds()
    val pastWeek = now - (7 * 24 * 60 * 60 * 1000)
    return this in pastWeek..now
}

private fun Long.inPastMonth(clock: Clock): Boolean {
    val now = clock.now()
    val currentTime = now.toEpochMilliseconds()

    val lastMonth = now.minus(
        1,
        DateTimeUnit.MONTH,
        TimeZone.currentSystemDefault()
    ).toEpochMilliseconds()
    val comparedDate = this
    return comparedDate in lastMonth..currentTime
}

private fun List<WalletLogItem<*>>.filterBy(category: FilterWalletLogByCategory): List<WalletLogItem<*>> {
    return filter { item ->
        when (category) {
            Transaction -> item is WalletLogItem.TransactionLogItem
            GoalSavings -> item is WalletLogItem.GoalSavingLogItem
            Transfer -> item is WalletLogItem.WalletTransferLogItem
            All -> true
        }
    }
}