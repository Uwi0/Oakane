package com.kakapo.oakane.presentation.feature.termAndService

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.kakapo.common.showToast
import com.kakapo.model.system.Theme
import com.kakapo.oakane.R
import com.kakapo.oakane.presentation.designSystem.component.button.CustomButton
import com.kakapo.oakane.presentation.designSystem.theme.AppTheme
import com.kakapo.oakane.presentation.designSystem.theme.ColorScheme
import com.kakapo.oakane.presentation.designSystem.theme.Typography
import com.kakapo.oakane.presentation.model.TermAndServiceMessage.TERM_1
import com.kakapo.oakane.presentation.model.TermAndServiceMessage.TERM_2
import com.kakapo.oakane.presentation.model.TermAndServiceMessage.TERM_3
import com.kakapo.oakane.presentation.model.TermAndServiceMessage.TERM_4
import com.kakapo.oakane.presentation.model.TermAndServiceMessage.TERM_TITLE
import com.kakapo.oakane.presentation.ui.component.RowWrapper
import com.kakapo.oakane.presentation.viewModel.termAndService.TermAndServiceEffect
import com.kakapo.oakane.presentation.viewModel.termAndService.TermAndServiceEvent
import com.kakapo.oakane.presentation.viewModel.termAndService.TermAndServiceState
import com.kakapo.oakane.presentation.viewModel.termAndService.TermAndServiceViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
internal fun TermAndServiceRoute(
    navigateToOnBoarding: () -> Unit
) {
    val viewModel = koinViewModel<TermAndServiceViewModel>()
    val context = LocalContext.current
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.uiEffect.collect { effect ->
            when(effect) {
                TermAndServiceEffect.NavigateToOnBoarding -> navigateToOnBoarding.invoke()
                is TermAndServiceEffect.ShowError -> context.showToast(effect.message)
            }
        }
    }

    TermAndServiceScreen(uiState = uiState, onEvent = viewModel::handleEvent)
}

@Composable
private fun TermAndServiceScreen(uiState: TermAndServiceState, onEvent: (TermAndServiceEvent) -> Unit) {
    Scaffold(
        topBar = { TopAppBarView() },
        content = { paddingValues ->
            TermAndServiceContent(
                modifier = Modifier
                    .padding(paddingValues)
                    .padding(vertical = 24.dp, horizontal = 16.dp),
                uiState = uiState,
                onEvent = onEvent
            )
        },
        bottomBar = {
            TermAndServiceButton(
                enable = uiState.isButtonEnabled,
                onClick = { onEvent.invoke(TermAndServiceEvent.OnAgreementButtonClicked) }
            )
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TopAppBarView() {
    TopAppBar(title = { Text("Important User Agreement") })
}

@Composable
private fun TermAndServiceContent(
    modifier: Modifier,
    uiState: TermAndServiceState,
    onEvent: (TermAndServiceEvent) -> Unit
) {
    Column(
        modifier = modifier.verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        GithubButton()
        Text(TERM_TITLE, style = Typography.titleMedium)
        TermAndServiceCheckbox(
            TERM_1,
            isChecked = uiState.isTerm1Checked,
            onChecked = { onEvent.invoke(TermAndServiceEvent.OnTerm1Checked(it)) }
        )
        TermAndServiceCheckbox(
            TERM_2,
            isChecked = uiState.isTerm2Checked,
            onChecked = { onEvent.invoke(TermAndServiceEvent.OnTerm2Checked(it)) }
        )
        TermAndServiceCheckbox(
            TERM_3,
            isChecked = uiState.isTerm3Checked,
            onChecked = { onEvent.invoke(TermAndServiceEvent.OnTerm3Checked(it)) }
        )
        TermAndServiceCheckbox(
            TERM_4,
            isChecked = uiState.isTerm4Checked,
            onChecked = { onEvent.invoke(TermAndServiceEvent.OnTerm4Checked(it)) }
        )
    }
}

@Composable
private fun GithubButton() {
    val uriHandler = LocalUriHandler.current
    RowWrapper(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp, horizontal = 16.dp),
        theme = Theme.System,
        onClick = {
            uriHandler.openUri("https://github.com/Uwi0/Oakane")
        }
    ) {
        GithubIcon()
        Column(modifier = Modifier.weight(1f)) {
            Text("Oakane is open source")
            Text("https://github.com/Uwi0/Oakane", color = ColorScheme.tertiary)
        }
    }
}

@Composable
private fun GithubIcon() {
    Icon(
        modifier = Modifier
            .size(48.dp)
            .clip(CircleShape),
        painter = painterResource(R.drawable.ic_github),
        contentDescription = "Github Icon",
    )
}

@Composable
private fun TermAndServiceCheckbox(
    title: String,
    isChecked: Boolean,
    onChecked: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(
            checked = isChecked,
            onCheckedChange = onChecked
        )
        Text(title, style = Typography.bodyMedium)
    }
}

@Composable
private fun TermAndServiceButton(enable: Boolean, onClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp, bottom = 24.dp)
    ) {
        CustomButton(
            modifier = Modifier.fillMaxWidth(),
            enabled = enable,
            onClick = onClick,
            contentPadding = PaddingValues(16.dp),
            content = { Text("I Accept and Agree") }
        )
    }
}

@Composable
@Preview
private fun GithubIconPrev() {
    AppTheme {
        TermAndServiceScreen(uiState = TermAndServiceState(isTerm1Checked = true), onEvent = {})
    }
}