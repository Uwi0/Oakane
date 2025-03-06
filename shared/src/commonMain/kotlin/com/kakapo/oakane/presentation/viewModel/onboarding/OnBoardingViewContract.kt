package com.kakapo.oakane.presentation.viewModel.onboarding

import com.kakapo.model.Currency
import com.kakapo.model.wallet.WalletModel
import com.kakapo.oakane.presentation.model.OnBoardingContent

data class OnBoardingState(
    val onBoardingContent: OnBoardingContent = OnBoardingContent.Account,
    val currency: Currency = Currency.IDR,
) {

    companion object {
        fun default() = OnBoardingState()
    }
}

sealed class OnBoardingEffect {
    data class ShowError(val message: String): OnBoardingEffect()
    data object NavigateToHome : OnBoardingEffect()
    data object RestoreBackup: OnBoardingEffect()
}

sealed class OnBoardingEvent {
    data class NavigateNext(val content: OnBoardingContent): OnBoardingEvent()
    data class OnConfirmCurrency(val currency: Currency): OnBoardingEvent()
    data class ConfirmWallet(val wallet: WalletModel): OnBoardingEvent()
    data class RestoreBackup(val json: String) : OnBoardingEvent()
    data object OnclickRestoredBackup: OnBoardingEvent()
    data object SkippWallet: OnBoardingEvent()
}