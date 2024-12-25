package com.kakapo.oakane.presentation.viewModel.reports

import com.kakapo.oakane.common.toStartDateOfMonthAndEndDateOfMonth
import com.kakapo.oakane.model.ReportModel
import com.kakapo.oakane.model.monthlyBudget.MonthlyBudgetOverViewModel
import com.kakapo.oakane.model.wallet.WalletItemModel
import com.kakapo.oakane.presentation.viewModel.reports.model.MonthReport
import com.kakapo.oakane.presentation.viewModel.reports.model.currentMonth
import kotlin.native.ObjCName

@ObjCName("ReportsState")
data class ReportsState(
    val reports: List<ReportModel> = emptyList(),
    val displayedReports: List<ReportModel> = emptyList(),
    val monthlyOverView: MonthlyBudgetOverViewModel = MonthlyBudgetOverViewModel(),
    val totalBalance: Double = 0.0,
    val displayedTotalBalance: Double = 0.0,
    val wallets: List<WalletItemModel> = emptyList(),
    val selectedWalletName: String = "All Wallet",
    val selectedMonth: MonthReport = currentMonth(),
    val selectedWallet: WalletItemModel? = null
){
    val proportions: List<Float> get(){
        val total = displayedReports.sumOf { it.amount }
        return displayedReports.map { (it.amount / total).toFloat() }
    }

    val colors: List<Int> get() = displayedReports.map { it.formattedColor }

    val names: List<String> get() = displayedReports.map { it.name }

    val monthNumber get() = selectedMonth.monthNumber.toStartDateOfMonthAndEndDateOfMonth()

    fun updateBalance(balance: Double) = copy(
        totalBalance = balance,
        displayedTotalBalance = balance
    )

    fun updateAllWallet() = copy(
        selectedWalletName = "All Wallet",
        displayedTotalBalance = totalBalance,
        displayedReports = reports,
        selectedWallet = null
    )

    fun updateSelected(wallet: WalletItemModel, reports: List<ReportModel>) = copy(
        selectedWalletName = wallet.name,
        displayedTotalBalance = wallet.balance,
        displayedReports = reports,
        selectedWallet = wallet
    )
}

sealed class ReportsEffect {
    data object NavigateBack: ReportsEffect()
    data class ShowError(val message: String): ReportsEffect()
}

sealed class ReportsEvent {
    data object NavigateBack: ReportsEvent()
    data object SelectedAllWallet: ReportsEvent()
    data class Selected(val wallet: WalletItemModel): ReportsEvent()
    data class FilterBy(val month: MonthReport): ReportsEvent()
}