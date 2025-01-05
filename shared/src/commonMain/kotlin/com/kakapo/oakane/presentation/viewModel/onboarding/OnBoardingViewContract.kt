package com.kakapo.oakane.presentation.viewModel.onboarding

import com.kakapo.oakane.presentation.model.OnBoardingContent

data class OnBoardingState(
    val onBoardingContent: OnBoardingContent = OnBoardingContent.Account,
)

sealed class OnBoardingEvent {
    data class NavigateNext(val content: OnBoardingContent): OnBoardingEvent()
}