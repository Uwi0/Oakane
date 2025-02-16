package com.kakapo.oakane.presentation.viewModel.termAndService

import com.kakapo.model.system.Theme

data class TermAndServiceState(
    val isTerm1Checked: Boolean = false,
    val isTerm2Checked: Boolean = false,
    val isTerm3Checked: Boolean = false,
    val isTerm4Checked: Boolean = false,
    val theme: Theme = Theme.System
){
    val isButtonEnabled: Boolean get() = isTerm1Checked && isTerm2Checked && isTerm3Checked && isTerm4Checked
}

sealed class TermAndServiceEffect {
    data class ShowError(val message: String): TermAndServiceEffect()
    data object NavigateToOnBoarding: TermAndServiceEffect()
}

sealed class TermAndServiceEvent {
    data class OnTerm1Checked(val isChecked: Boolean): TermAndServiceEvent()
    data class OnTerm2Checked(val isChecked: Boolean): TermAndServiceEvent()
    data class OnTerm3Checked(val isChecked: Boolean): TermAndServiceEvent()
    data class OnTerm4Checked(val isChecked: Boolean): TermAndServiceEvent()
    data object OnAgreementButtonClicked: TermAndServiceEvent()
}