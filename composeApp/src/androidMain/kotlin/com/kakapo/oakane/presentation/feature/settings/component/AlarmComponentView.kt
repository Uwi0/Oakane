package com.kakapo.oakane.presentation.feature.settings.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForwardIos
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kakapo.oakane.presentation.designSystem.component.button.ToggleSwitchComponentView
import com.kakapo.oakane.presentation.designSystem.theme.AppTheme
import com.kakapo.oakane.presentation.designSystem.theme.ColorScheme
import com.kakapo.oakane.presentation.designSystem.theme.Typography
import com.kakapo.oakane.presentation.model.ReminderDay
import com.kakapo.oakane.presentation.viewModel.settings.SettingsEvent
import com.kakapo.oakane.presentation.viewModel.settings.SettingsState

@Composable
internal fun AlarmComponentView(state: SettingsState, onEvent: (SettingsEvent) -> Unit) {
    Column {
        ToggleSwitchComponentView(
            title = "Set Reminder",
            checked = state.alarmEnabled,
            onCheckedChange = { onEvent.invoke(SettingsEvent.ToggleAlarm(it)) }
        )
        AnimatedVisibility(state.alarmEnabled) {
            AlarmContentView(state = state, onEvent = onEvent)
        }
    }
}

@Composable
private fun AlarmContentView(
    state: SettingsState,
    onEvent: (SettingsEvent) -> Unit
) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        TitleContent(state)
        RepeatedDayView(state = state)
        DaySelectorView(onEvent = onEvent)
    }
}

@Composable
private fun TitleContent(state: SettingsState) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text("Time Input", style = Typography.labelLarge)
        Text(state.alarmValue, style = Typography.titleLarge)
        CustomButtonEdit()
    }
}

@Composable
private fun CustomButtonEdit() {
    Surface(shape = MaterialTheme.shapes.small, color = ColorScheme.secondary) {
        Row(
            modifier = Modifier.padding(start = 16.dp, top = 8.dp, bottom = 8.dp, end = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Edit")
            Icon(imageVector = Icons.AutoMirrored.Filled.ArrowForwardIos, contentDescription = null)
        }
    }
}

@Composable
private fun RepeatedDayView(state: SettingsState) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text("Repeat on:", style = Typography.labelLarge)
        state.selectedDays.forEach { day ->
            Text(day.title, style = Typography.titleMedium)
        }
    }
}

@Composable
private fun DaySelectorView(onEvent: (SettingsEvent) -> Unit) {
    LazyRow(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(ReminderDay.entries) { day ->
            DayReminderItem(day, onClick = { onEvent.invoke(SettingsEvent.UpdateDay(day)) })
        }
    }
}

@Composable
private fun DayReminderItem(
    day: ReminderDay,
    isSelected: Boolean = false,
    onClick: () -> Unit = {}
) {
    val (containerColor, borderColor) = isSelected.asChipDateSelectedColor()
    Surface(
        onClick = onClick,
        shape = MaterialTheme.shapes.small,
        color = containerColor,
        border = BorderStroke(color = borderColor, width = 1.dp)
    ) {
        Text(
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
            text = day.title,
            style = Typography.titleSmall
        )
    }
}

@Composable
private fun Boolean.asChipDateSelectedColor(): Pair<Color, Color> {
    return ColorScheme.secondaryContainer to ColorScheme.secondary
}

@Preview
@Composable
private fun AlarmComponentPrev() {
    AppTheme {
        Surface {
            AlarmComponentView(state = SettingsState(alarmEnabled = true)) {

            }
        }
    }
}