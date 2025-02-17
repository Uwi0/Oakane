package com.kakapo.oakane.presentation.viewModel.goal

import com.kakapo.common.asRealCurrencyValue
import com.kakapo.common.daysBetween
import com.kakapo.data.model.GoalSavingParam
import com.kakapo.model.Currency
import com.kakapo.model.goal.GoalModel
import com.kakapo.model.goal.GoalSavingModel
import com.kakapo.model.system.Theme
import com.kakapo.model.toFormatCurrency
import com.kakapo.model.wallet.WalletModel
import kotlinx.datetime.Clock
import kotlin.native.ObjCName

@ObjCName("GoalStateKt")
data class GoalState(
    val goal: GoalModel = GoalModel(),
    val savingAmount: String = "",
    val dialogShown: Boolean = false,
    val dialogContent: GoalDialogContent = GoalDialogContent.UpdateAmount,
    val currency: Currency = Currency.IDR,
    val wallets: List<WalletModel> = emptyList(),
    val selectedWallet: WalletModel = WalletModel(),
    val note: String = "",
    val goalSavings: List<GoalSavingModel> = emptyList(),
    val theme: Theme = Theme.System
) {

    val dayLeft: Long
        get() {
            return goal.endDate.daysBetween(goal.startDate)
        }

    val currentAmount: String
        get() {
            return goal.savedMoney.toFormatCurrency(currency)
        }

    val targetAmount: String
        get() {
            return goal.amount.toFormatCurrency(currency)
        }

    fun goalSavingParam(): GoalSavingParam {
        val currentTime = Clock.System.now().toEpochMilliseconds()
        return GoalSavingParam(
            goalId = goal.id,
            dateCreated = currentTime,
            amount = savingAmount.asRealCurrencyValue(),
            walletId = selectedWallet.id,
            note = note
        )
    }

    fun updateDialog(shown: Boolean, content: GoalDialogContent) = copy(
        dialogShown = shown,
        dialogContent = content
    )
}

enum class GoalDialogContent {
    UpdateAmount, DeleteGoal
}

sealed class GoalEffect {
    data object NavigateBack : GoalEffect()
    data class ShowError(val message: String): GoalEffect()
    data class UpdateGoalBy(val id: Long): GoalEffect()
}

sealed class GoalEvent {
    data object NavigateBack : GoalEvent()
    data class Dialog(
        val shown: Boolean,
        val content: GoalDialogContent = GoalDialogContent.UpdateAmount
    ) : GoalEvent()

    data class Change(val amount: String) : GoalEvent()
    data object AddSaving : GoalEvent()
    data object DeleteGoal : GoalEvent()
    data object UpdateGoal : GoalEvent()
    data class ChangeWallet(val wallet: WalletModel) : GoalEvent()
    data class AddNote(val note: String) : GoalEvent()
}