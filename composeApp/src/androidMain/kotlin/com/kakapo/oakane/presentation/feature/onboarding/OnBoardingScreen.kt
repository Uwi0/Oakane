package com.kakapo.oakane.presentation.feature.onboarding

import android.content.Intent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.kakapo.common.showToast
import com.kakapo.oakane.presentation.designSystem.theme.AppTheme
import com.kakapo.oakane.presentation.feature.onboarding.content.AccountContentView
import com.kakapo.oakane.presentation.feature.onboarding.content.ImportBackupContentView
import com.kakapo.oakane.presentation.feature.onboarding.content.createWallet.CreateWalletView
import com.kakapo.oakane.presentation.model.OnBoardingContent
import com.kakapo.oakane.presentation.ui.component.sheet.SelectCurrencyView
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

    val retrieveJsonLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        val data = result.data?.data
        data?.let { uri ->
            context.contentResolver.openInputStream(uri)?.let { inputStream ->
                val json = inputStream.bufferedReader().use { it.readText() }
                viewModel.handleEvent(OnBoardingEvent.RestoreBackup(json))
            }
        }
    }

    LaunchedEffect(Unit) {
        viewModel.uiEffect.collect { effect ->
            when (effect) {
                OnBoardingEffect.NavigateToHome -> navigateToHome.invoke()
                is OnBoardingEffect.ShowError -> context.showToast(effect.message)
                OnBoardingEffect.RestoreBackup -> retrieveJsonLauncher.launch(openDocumentIntent())
            }
        }
    }

    OnBoardingScreen(state = uiState, onEvent = viewModel::handleEvent)
}

private fun openDocumentIntent(): Intent {
    val intent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
        addCategory(Intent.CATEGORY_OPENABLE)
        type = "application/json"
    }
    intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
    intent.setFlags(Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION)
    intent.setFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
    return intent
}

@Composable
private fun OnBoardingScreen(state: OnBoardingState, onEvent: (OnBoardingEvent) -> Unit = {}) {
    AnimatedContent(state.onBoardingContent) { content ->
        when (content) {
            OnBoardingContent.Account -> AccountContentView(onEvent = onEvent)
            OnBoardingContent.ImportBackup -> ImportBackupContentView(onEvent = onEvent)
            OnBoardingContent.SelectCurrency -> SelectCurrencyView(
                currency = state.currency,
                onConfirm = { onEvent.invoke(OnBoardingEvent.OnConfirmCurrency(it)) }
            )
            OnBoardingContent.CreateWallet -> CreateWalletView(uiState = state,onEvent = onEvent)
        }
    }
}

@Composable
@Preview
private fun OnBoardingScreenPreview() {
    AppTheme {
        OnBoardingScreen(state = OnBoardingState())
    }
}
