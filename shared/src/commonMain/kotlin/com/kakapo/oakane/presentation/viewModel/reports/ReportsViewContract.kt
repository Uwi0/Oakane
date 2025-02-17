package com.kakapo.oakane.presentation.viewModel.reports

import com.kakapo.common.startDateAndEndDateOfMonth
import com.kakapo.model.Currency
import com.kakapo.model.monthlyBudget.MonthlyBudgetOverView
import com.kakapo.model.report.ReportCsvModel
import com.kakapo.model.report.ReportModel
import com.kakapo.model.system.Theme
import com.kakapo.model.toFormatCurrency
import com.kakapo.model.wallet.WalletItemModel
import com.kakapo.oakane.presentation.viewModel.reports.model.MonthReport
import com.kakapo.oakane.presentation.viewModel.reports.model.currentMonth
import kotlin.native.ObjCName

@ObjCName("ReportsStateKt")
data class ReportsState(
    val reports: List<ReportModel> = emptyList(),
    val monthlyOverView: MonthlyBudgetOverView = MonthlyBudgetOverView(),
    val totalBalance: Double = 0.0,
    val wallets: List<WalletItemModel> = emptyList(),
    val selectedWalletName: String = "All Wallet",
    val selectedMonth: MonthReport = currentMonth(),
    val selectedWallet: WalletItemModel? = null,
    val currency: Currency = Currency.IDR,
    val theme: Theme = Theme.System,
    val showDrawer: Boolean = false
){
    val proportions: List<Float> get(){
        val total = reports.sumOf { it.amount }
        return reports.map { (it.amount / total).toFloat() }
    }

    val colors: List<Long> get() = reports.map { it.formattedColor }

    val names: List<String> get() = reports.map { it.name }

    val monthNumber: Pair<Long, Long> get() = startDateAndEndDateOfMonth(selectedMonth.monthNumber)

    val limit: String get() = monthlyOverView.limit.toFormatCurrency(monthlyOverView.currency)
    
    val spent: String get() = monthlyOverView.spent.toFormatCurrency(monthlyOverView.currency)

    val displayedWallets: List<WalletItemModel> get() {
        val allWallet = WalletItemModel()
        return listOf(allWallet) + wallets
    }

    fun updateBalance(balance: Double) = copy(
        totalBalance = balance,
    )

    fun updateSelected(wallet: WalletItemModel, reports: List<ReportModel>) = copy(
        selectedWalletName = wallet.name,
        totalBalance = wallet.balance,
        reports = reports,
        selectedWallet = wallet
    )
}

sealed class ReportsEffect {
    data object NavigateBack: ReportsEffect()
    data class ShowError(val message: String): ReportsEffect()
    data class GenerateReport(val reports: List<ReportCsvModel>): ReportsEffect()
    data object OpenDrawer: ReportsEffect()
}

sealed class ReportsEvent {
    data object NavigateBack: ReportsEvent()
    data class Selected(val wallet: WalletItemModel): ReportsEvent()
    data class FilterBy(val month: MonthReport): ReportsEvent()
    data object GenerateReport: ReportsEvent()
    data object OpenDrawer: ReportsEvent()
}