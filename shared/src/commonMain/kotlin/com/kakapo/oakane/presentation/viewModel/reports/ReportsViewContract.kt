package com.kakapo.oakane.presentation.viewModel.reports

import com.kakapo.oakane.model.ReportModel
import com.kakapo.oakane.model.monthlyBudget.MonthlyBudgetOverViewModel
import com.kakapo.oakane.model.wallet.WalletItemModel
import kotlin.native.ObjCName

@ObjCName("ReportsState")
data class ReportsState(
    val reports: List<ReportModel> = emptyList(),
    val monthlyOverView: MonthlyBudgetOverViewModel = MonthlyBudgetOverViewModel(),
    val totalBalance: Double = 0.0,
    val displayedTotalBalance: Double = 0.0,
    val wallets: List<WalletItemModel> = emptyList(),
    val selectedWallet: String = "All Wallet"
){
    val proportions: List<Float> get(){
        val total = reports.sumOf { it.amount }
        return reports.map { (it.amount / total).toFloat() }
    }

    val colors: List<Int> get() = reports.map { it.formattedColor }

    val names: List<String> get() = reports.map { it.name }

    fun updateBalance(balance: Double) = copy(
        totalBalance = balance,
        displayedTotalBalance = balance
    )

    fun updateAllWallet() = copy(
        selectedWallet = "All Wallet",
        displayedTotalBalance = totalBalance
    )

    fun updateSelected(wallet: WalletItemModel) = copy(
        selectedWallet = wallet.name,
        displayedTotalBalance = wallet.balance
    )
}

sealed class ReportsEvent {
    data object NavigateBack: ReportsEvent()
    data object SelectedAllWallet: ReportsEvent()
    data class Selected(val wallet: WalletItemModel): ReportsEvent()
}