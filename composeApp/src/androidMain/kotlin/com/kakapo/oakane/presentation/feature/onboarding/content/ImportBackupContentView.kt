package com.kakapo.oakane.presentation.feature.onboarding.content

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowForwardIos
import androidx.compose.material.icons.outlined.FileDownload
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kakapo.oakane.presentation.designSystem.component.button.CustomButton
import com.kakapo.oakane.presentation.designSystem.component.button.CustomOutlinedButton
import com.kakapo.oakane.presentation.designSystem.theme.AppTheme
import com.kakapo.oakane.presentation.model.OnBoardingContent
import com.kakapo.oakane.presentation.viewModel.onboarding.OnBoardingEvent

@Composable
internal fun ImportBackupContentView(onEvent: (OnBoardingEvent) -> Unit) {
    Surface {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(vertical = 24.dp, horizontal = 16.dp)
        ) {
            Text(text = "Import Your Back Up", style = MaterialTheme.typography.displaySmall)
            Spacer(Modifier.weight(1f))
            Text(
                text = "You can import your back up to restore your data,  if you have one from previous back up or previous device.",
                style = MaterialTheme.typography.bodyMedium
            )
            Spacer(Modifier.weight(1f))
            CustomOutlinedButton(
                onClick = { onEvent.invoke(OnBoardingEvent.OnclickRestoredBackup)},
                contentPadding = PaddingValues(vertical = 12.dp, horizontal = 16.dp),
                content = {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.FileDownload,
                            contentDescription = "Import Back Up"
                        )
                        Text(text = "Import Back Up", style = MaterialTheme.typography.titleMedium)
                        Spacer(Modifier.size(24.dp))
                    }
                }
            )
            Spacer(Modifier.size(16.dp))
            CustomButton(
                onClick = { onEvent.invoke(OnBoardingEvent.NavigateNext(content = OnBoardingContent.SelectCurrency)) },
                contentPadding = PaddingValues(vertical = 12.dp, horizontal = 16.dp),
                content = {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Spacer(Modifier.size(24.dp))
                        Text(text = "Start Fresh", style = MaterialTheme.typography.titleMedium)
                        Icon(
                            imageVector = Icons.AutoMirrored.Outlined.ArrowForwardIos,
                            contentDescription = "Start Fresh"
                        )
                    }
                }
            )
        }
    }
}

@Composable
@Preview
private fun ImportBackupContentPreview() {
    AppTheme {
        ImportBackupContentView(onEvent = {})
    }
}