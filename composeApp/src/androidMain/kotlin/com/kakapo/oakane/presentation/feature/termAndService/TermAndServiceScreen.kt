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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kakapo.oakane.R
import com.kakapo.oakane.presentation.designSystem.component.button.CustomButton
import com.kakapo.oakane.presentation.designSystem.theme.AppTheme
import com.kakapo.oakane.presentation.designSystem.theme.ColorScheme
import com.kakapo.oakane.presentation.designSystem.theme.Typography
import com.kakapo.oakane.presentation.ui.component.RowWrapper

const val TERM_TITLE = "Please read and agree to the following terms before using the app:"
const val TERM_1 =
    "I recognize this app is open-source and provided 'as-is' with no warranties, explicit or implied. I fully accept all risks of errors, defects, or failures, using the app solely at my own risk."
const val TERM_2 =
    "I understand there is no warranty for the accuracy, reliability, or completeness of my data. Manual data backup is my responsibility, and I agree to not hold the app liable for any data loss."
const val TERM_3 =
    "I hereby release the app developers, contributors, and distributing company from any liability for claims, damages, legal fees, or losses, including those resulting from security breaches or data inaccuracies."
const val TERM_4 =
    "I am aware and accept that the app may display misleading information or contain inaccuracies. I assume full responsibility for verifying the integrity of financial data and calculations before making any decisions based on app data"

@Composable
internal fun TermAndServiceRoute() {
    TermAndServiceScreen()
}

@Composable
private fun TermAndServiceScreen() {
    Scaffold(
        topBar = { TopAppBarView() },
        content = { paddingValues ->
            TermAndServiceContent(
                modifier = Modifier
                    .padding(paddingValues)
                    .padding(vertical = 24.dp, horizontal = 16.dp)
            )
        },
        bottomBar = {
            TermAndServiceButton(enable = true, onClick = {})
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TopAppBarView() {
    TopAppBar(title = { Text("Important User Agreement") })
}

@Composable
private fun TermAndServiceContent(modifier: Modifier) {
    Column(
        modifier = modifier.verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        GithubButton()
        Text(TERM_TITLE, style = Typography.titleMedium)
        TermAndServiceCheckbox(TERM_1, isChecked = true, onChecked = {})
        TermAndServiceCheckbox(TERM_2, isChecked = false, onChecked = {})
        TermAndServiceCheckbox(TERM_3, isChecked = false, onChecked = {})
        TermAndServiceCheckbox(TERM_4, isChecked = false, onChecked = {})
    }
}

@Composable
private fun GithubButton() {
    val uriHandler = LocalUriHandler.current
    RowWrapper(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp, horizontal = 16.dp),
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
    Column(modifier = Modifier.fillMaxWidth().padding(start = 16.dp, end = 16.dp, bottom = 24.dp)) {
        CustomButton(
            modifier = Modifier.fillMaxWidth(),
            enabled = enable,
            onClick = {},
            contentPadding = PaddingValues(16.dp),
            content = { Text("I Accept and Agree")}
        )
    }
}

@Composable
@Preview
private fun GithubIconPrev() {
    AppTheme {
        TermAndServiceScreen()
    }
}