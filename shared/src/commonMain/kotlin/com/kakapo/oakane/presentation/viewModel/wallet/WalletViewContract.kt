package com.kakapo.oakane.presentation.viewModel.wallet

import com.kakapo.common.asRealCurrencyValue
import com.kakapo.data.model.WalletTransferParam
import com.kakapo.domain.usecase.filterWalletLogsBy
import com.kakapo.model.system.Theme
import com.kakapo.model.wallet.FilterWalletLogByCategory
import com.kakapo.model.wallet.FilterWalletLogByDateModel
import com.kakapo.model.wallet.WalletItemModel
import com.kakapo.model.wallet.WalletLogItem
import com.kakapo.model.wallet.WalletModel
import kotlinx.datetime.Clock

typealias FilterDate = FilterWalletLogByDateModel
typealias FilterCategory = FilterWalletLogByCategory

data class WalletState(
    val wallet: WalletItemModel = WalletItemModel(),
    val wallets: List<WalletModel> = emptyList(),
    val moveBalanceNote: String = "",
    val dialogVisible: Boolean = false,
    val selectedWalletTo: WalletModel = WalletModel(),
    val movedBalance: String = "",
    val logItems: List<WalletLogItem<*>> = emptyList(),
    val searchQuery: String = "",
    val dialogContent: WalletDialogContent = WalletDialogContent.DeleteWallet,
    val isWalletSheetShown: Boolean = false,
    val isFilterSheetShown: Boolean = false,
    val filterDate: FilterDate = FilterWalletLogByDateModel.All,
    val filterCategory: FilterCategory = FilterWalletLogByCategory.All,
    val theme: Theme = Theme.System
) {
    val selectedWalletId: Long get() = wallet.id

    val filteredLogItems: List<WalletLogItem<*>>
        get()  = logItems.filterWalletLogsBy(
            query = searchQuery,
            date = filterDate,
            category = filterCategory
        )

    val formatedMovedBalance: Int get() = movedBalance.toIntOrNull() ?: 0

    val hasFilter: Boolean get() = filterDate != FilterWalletLogByDateModel.All ||
            filterCategory != FilterWalletLogByCategory.All

    fun asWalletTransfer() = WalletTransferParam(
        fromWalletId = selectedWalletId,
        toWalletId = selectedWalletTo.id,
        amount = movedBalance.asRealCurrencyValue(),
        notes = moveBalanceNote,
        createdAt = Clock.System.now().toEpochMilliseconds()
    )

    fun showDialog(event: WalletEvent.ShowDialog) = copy(
        dialogVisible = event.shown,
        dialogContent = event.content
    )

    fun applyFilter(event: WalletEvent.FilterLog) = copy(
        filterDate = event.dateFilter,
        filterCategory = event.categoryFilter,
        isFilterSheetShown = false
    )

    fun resetFilter() = copy(
        filterDate = FilterWalletLogByDateModel.All,
        filterCategory = FilterWalletLogByCategory.All
    )

    companion object {
        fun default() = WalletState()
    }
}

sealed class WalletEffect {
    data object NavigateBack : WalletEffect()
    data class ShowError(val message: String) : WalletEffect()
    data object DismissWalletSheet: WalletEffect()
    data object DismissFilterSheet: WalletEffect()
}

sealed class WalletEvent {
    data object NavigateBack : WalletEvent()
    data class ShowDialog(
        val content: WalletDialogContent = WalletDialogContent.DeleteWallet,
        val shown: Boolean
    ) : WalletEvent()

    data class AddNote(val note: String) : WalletEvent()
    data class AddBalance(val balance: String) : WalletEvent()
    data class AddSelectedWalletTo(val wallet: WalletModel) : WalletEvent()
    data object MoveBalance : WalletEvent()
    data class SearchLog(val query: String) : WalletEvent()
    data class ShowWalletSheet(val shown: Boolean) : WalletEvent()
    data class ShowFilterSheet(val shown: Boolean) : WalletEvent()
    data object ConfirmDelete : WalletEvent()
    data class UpdateWallet(val wallet: WalletModel) : WalletEvent()
    data class FilterLog(val dateFilter: FilterDate, val categoryFilter: FilterCategory) : WalletEvent()
    data object ResetFilterLog : WalletEvent()
}

enum class WalletDialogContent {
    DeleteWallet,
    MoveBalance
}