package com.kakapo.oakane.presentation.feature.onboarding

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.kakapo.common.showToast
import com.kakapo.oakane.presentation.feature.onboarding.content.AccountContentView
import com.kakapo.oakane.presentation.feature.onboarding.content.ImportBackupContentView
import com.kakapo.oakane.presentation.feature.onboarding.content.SelectCurrencyView
import com.kakapo.oakane.presentation.feature.onboarding.content.createWallet.CreateWalletView
import com.kakapo.oakane.presentation.model.OnBoardingContent
import com.kakapo.oakane.presentation.viewModel.onboarding.OnBoardingEffect
import com.kakapo.oakane.presentation.viewModel.onboarding.OnBoardingEvent
import com.kakapo.oakane.presentation.viewModel.onboarding.OnBoardingState
import com.kakapo.oakane.presentation.viewModel.onboarding.OnBoardingViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
internal fun OnBoardingRoute(navigateToHome: () -> Unit) {
    val context = LocalContext.current
    val viewModel = koinViewModel<OnBoardingViewModel>()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.uiEffect.collect { effect ->
            when(effect) {
                OnBoardingEffect.NavigateToHome -> navigateToHome.invoke()
                is OnBoardingEffect.ShowError -> context.showToast(effect.message)
            }
        }
    }

    OnBoardingScreen(state = uiState, onEvent = viewModel::handleEvent)
}

@Composable
private fun OnBoardingScreen(state: OnBoardingState, onEvent: (OnBoardingEvent) -> Unit = {}) {
    when (state.onBoardingContent) {
        OnBoardingContent.Account -> AccountContentView(onEvent = onEvent)
        OnBoardingContent.ImportBackup -> ImportBackupContentView(onEvent = onEvent)
        OnBoardingContent.SelectCurrency -> SelectCurrencyView(onConfirm = { onEvent.invoke(OnBoardingEvent.OnConfirmCurrency(it)) })
        OnBoardingContent.CreateWallet -> CreateWalletView(onEvent = onEvent)
    }
}
