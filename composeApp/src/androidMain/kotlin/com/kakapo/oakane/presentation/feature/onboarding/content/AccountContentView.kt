package com.kakapo.oakane.presentation.feature.onboarding.content

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kakapo.oakane.presentation.designSystem.theme.AppTheme
import com.kakapo.oakane.presentation.model.OnBoardingContent
import com.kakapo.oakane.presentation.viewModel.onboarding.OnBoardingEvent

@Composable
internal fun AccountContentView(onEvent: (OnBoardingEvent) -> Unit) {
    Surface {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(vertical = 24.dp, horizontal = 16.dp)
        ) {
            Text(text = "Oakane", style = MaterialTheme.typography.displayMedium)
            Spacer(Modifier.size(8.dp))
            Text(
                text = "Your personal finance Manager",
                style = MaterialTheme.typography.labelLarge
            )
            Text(text = "#Open source", style = MaterialTheme.typography.labelSmall)
            Spacer(modifier = Modifier.height(height = 164.dp))
            Text(text = "Enter with offline Account", style = MaterialTheme.typography.titleMedium)
            Spacer(Modifier.height(height = 12.dp))
            Text(
                text = "Your data will be stored locally on your device. if you uninstall the app or switch devices. you may lose your data. To prevent this, we recommend that you regularly export your backups.",
                style = MaterialTheme.typography.bodySmall
            )
            Spacer(Modifier.height(16.dp))
            LoginButton(onClick = { onEvent.invoke(OnBoardingEvent.NavigateNext(content = OnBoardingContent.ImportBackup)) })
            Spacer(modifier = Modifier.weight(1f))
            Text(
                modifier = Modifier.width(224.dp).align(Alignment.CenterHorizontally),
                text = "By signing in. you accept our Terms & Conditions and privacy policy",
                style = MaterialTheme.typography.bodySmall,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
private fun LoginButton(onClick: () -> Unit) {
    Surface(
        color = MaterialTheme.colorScheme.primary,
        shape = MaterialTheme.shapes.medium,
        onClick = onClick
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp, horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Icon(
                imageVector = Icons.Outlined.AccountCircle,
                contentDescription = "Login",
                modifier = Modifier.size(24.dp)
            )
            Text("Offline Account", style = MaterialTheme.typography.titleMedium)
        }
    }
}

@Composable
@Preview
private fun AccountContentPreview() {
    AppTheme {
        AccountContentView(onEvent = {})
    }
}