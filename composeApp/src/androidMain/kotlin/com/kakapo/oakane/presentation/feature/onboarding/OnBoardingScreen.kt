package com.kakapo.oakane.presentation.feature.onboarding

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.kakapo.oakane.presentation.feature.onboarding.content.AccountContentView
import com.kakapo.oakane.presentation.feature.onboarding.content.CreateWalletView
import com.kakapo.oakane.presentation.feature.onboarding.content.ImportBackupContentView
import com.kakapo.oakane.presentation.feature.onboarding.content.SelectCurrencyView
import com.kakapo.oakane.presentation.model.OnBoardingContent
import com.kakapo.oakane.presentation.viewModel.onboarding.OnBoardingEvent
import com.kakapo.oakane.presentation.viewModel.onboarding.OnBoardingState
import com.kakapo.oakane.presentation.viewModel.onboarding.OnBoardingViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
internal fun OnBoardingRoute() {
    val viewModel = koinViewModel<OnBoardingViewModel>()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    OnBoardingScreen(state = uiState, onEvent = viewModel::handleEvent)
}

@Composable
private fun OnBoardingScreen(state: OnBoardingState, onEvent: (OnBoardingEvent) -> Unit = {}) {
    when (state.onBoardingContent) {
        OnBoardingContent.Account -> AccountContentView(onEvent = onEvent)
        OnBoardingContent.ImportBackup -> ImportBackupContentView()
        OnBoardingContent.SelectCurrency -> SelectCurrencyView()
        OnBoardingContent.CreateWallet -> CreateWalletView()
    }
}
