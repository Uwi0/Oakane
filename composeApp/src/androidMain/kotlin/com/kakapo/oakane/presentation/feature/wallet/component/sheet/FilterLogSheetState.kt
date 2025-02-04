package com.kakapo.oakane.presentation.feature.wallet.component.sheet

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.kakapo.common.formatDateWith
import com.kakapo.common.getCurrentUnixTime
import com.kakapo.model.wallet.FilterDateDialogContent
import com.kakapo.model.wallet.FilterWalletLogByCategory
import com.kakapo.model.wallet.FilterWalletLogByDateModel

@Composable
internal fun rememberFilterLogSheetState(
    dateFilter: FilterWalletLogByDateModel = FilterWalletLogByDateModel.All,
    categoryFilter: FilterWalletLogByCategory = FilterWalletLogByCategory.All,
    onApplyFilter: (FilterWalletLogByDateModel, FilterWalletLogByCategory) -> Unit = { _, _ -> },
    onResetFilter: () -> Unit = {}
) = remember(dateFilter, categoryFilter) {
    FilterLogSheetState(dateFilter, categoryFilter, onApplyFilter)
}

class FilterLogSheetState(
    dateFilter: FilterWalletLogByDateModel = FilterWalletLogByDateModel.All,
    categoryFilter: FilterWalletLogByCategory = FilterWalletLogByCategory.All,
    private val onApplyFilter: (FilterWalletLogByDateModel, FilterWalletLogByCategory) -> Unit,
    private val onResetFilter: () -> Unit = {}
) {

    var isDialogShown by mutableStateOf(false)
    private var selectedDateFilter by mutableStateOf(dateFilter)
    private var selectedCategoryFilter by mutableStateOf(categoryFilter)
    private var selectedStartDate by mutableLongStateOf(dateFilter.asStartDate())
    private var selectedEndDate by mutableLongStateOf(dateFilter.asEndDate())
    private var dialogContent by mutableStateOf(FilterDateDialogContent.StartDate)

    val isResetShown: Boolean
        get() {
            return selectedDateFilter != FilterWalletLogByDateModel.All
                    || selectedCategoryFilter != FilterWalletLogByCategory.All
        }

    val defaultValue: Long
        get() {
            return if (dialogContent == FilterDateDialogContent.StartDate) selectedStartDate
            else selectedEndDate
        }

    val isLastWeekSelected: Boolean
        get() {
            return selectedDateFilter == FilterWalletLogByDateModel.PastWeek
        }

    val isLastMonthSelected: Boolean
        get() {
            return selectedDateFilter == FilterWalletLogByDateModel.PastMonth
        }

    val isCustomSelected: Boolean
        get() {
            return selectedDateFilter is FilterWalletLogByDateModel.Custom
        }

    val isTransactionSelected: Boolean
        get() {
            return selectedCategoryFilter == FilterWalletLogByCategory.Transaction
        }

    val isGoalSavingSelected: Boolean
        get() {
            return selectedCategoryFilter == FilterWalletLogByCategory.GoalSavings
        }

    val isTransferSelected: Boolean
        get() {
            return selectedCategoryFilter == FilterWalletLogByCategory.Transfer
        }

    val displayedStartDate: String
        get() {
            return selectedStartDate.formatDateWith(DATE_FORMAT)
        }

    val displayedEndDate: String
        get() {
            return selectedEndDate.formatDateWith(DATE_FORMAT)
        }

    fun onSelectedWeek() {
        selectedDateFilter = FilterWalletLogByDateModel.PastWeek
    }

    fun onSelectedMonth() {
        selectedDateFilter = FilterWalletLogByDateModel.PastMonth
    }

    fun onSelectedCustom() {
        val currentTime = getCurrentUnixTime()
        selectedDateFilter = FilterWalletLogByDateModel.Custom(currentTime, currentTime)
    }

    fun onSelectedTransaction() {
        selectedCategoryFilter = FilterWalletLogByCategory.Transaction
    }

    fun onSelectedGoalSaving() {
        selectedCategoryFilter = FilterWalletLogByCategory.GoalSavings
    }

    fun onSelectedTransfer() {
        selectedCategoryFilter = FilterWalletLogByCategory.Transfer
    }

    fun onShowDialogStartDate() {
        dialogContent = FilterDateDialogContent.StartDate
        isDialogShown = true
    }

    fun onShowDialogEndDate() {
        dialogContent = FilterDateDialogContent.EndDate
        isDialogShown = true
    }

    fun setFilterDate(date: Long) {
        if (dialogContent == FilterDateDialogContent.StartDate) {
            selectedStartDate = date
        } else {
            selectedEndDate = date
        }
        isDialogShown = false
    }

    fun resetFilter() {
        selectedDateFilter = FilterWalletLogByDateModel.All
        selectedCategoryFilter = FilterWalletLogByCategory.All
        onResetFilter.invoke()
    }

    fun applyFilter() {
        val dateFilter = validateDateFilter()
        onApplyFilter(dateFilter, selectedCategoryFilter)
    }

    private fun validateDateFilter(): FilterWalletLogByDateModel {
        if (selectedDateFilter is FilterWalletLogByDateModel.Custom) {
            if (selectedStartDate > selectedEndDate) {
                val dateTemp = selectedStartDate
                selectedStartDate = selectedEndDate
                selectedEndDate = dateTemp
                return FilterWalletLogByDateModel.Custom(selectedStartDate, selectedEndDate)
            }
            return FilterWalletLogByDateModel.Custom(selectedStartDate, selectedEndDate)
        } else {
            return selectedDateFilter
        }
    }

    companion object {
        private const val DATE_FORMAT = "dd MMM yyyy"
    }
}