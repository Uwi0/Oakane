package com.kakapo.oakane.presentation.feature.settings.component.dialog

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TimePicker
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kakapo.oakane.presentation.designSystem.component.button.CustomButton
import com.kakapo.oakane.presentation.designSystem.component.button.CustomTextButton
import com.kakapo.oakane.presentation.designSystem.theme.AppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun SetAlarmReminderDialogContent() {
    val timePickerState = rememberTimePickerState(
        is24Hour = true
    )
    Column(
        modifier = Modifier.padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TimePicker(state = timePickerState)
        BottomContentView()
    }
}

@Composable
private fun BottomContentView() {
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
        CustomTextButton(
            modifier = Modifier.weight(1f),
            onClick = {},
            text = { Text("Cancel") }
        )
        CustomButton(modifier = Modifier.weight(1f), onClick = {}, text = { Text("Save") })
    }
}

@Composable
@Preview
private fun SetAlarmTimeDialogContentPreview() {
    AppTheme {
        Surface {
            SetAlarmReminderDialogContent()
        }
    }
}