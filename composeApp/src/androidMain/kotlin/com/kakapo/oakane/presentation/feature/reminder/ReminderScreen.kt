package com.kakapo.oakane.presentation.feature.reminder

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kakapo.model.reminder.ReminderDay
import com.kakapo.oakane.presentation.designSystem.component.button.CustomButton
import com.kakapo.oakane.presentation.designSystem.component.topAppBar.CustomNavigationTopAppBarView
import com.kakapo.oakane.presentation.designSystem.theme.AppTheme
import com.kakapo.oakane.presentation.designSystem.theme.ColorScheme

@Composable
internal fun ReminderRoute() {
    ReminderScreen()
}

@Composable
private fun ReminderScreen() {
    Scaffold(
        topBar = { CustomNavigationTopAppBarView(title = "Reminder", onNavigateBack = {}) },
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .padding(paddingValues)
                    .padding(vertical = 24.dp, horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                SwitchReminder()
                HorizontalDivider()
                TimeSelector()
                DaySelector()
                HorizontalDivider()
            }
        },
        bottomBar = {
            ButtonSaveReminder()
        }
    )
}

@Composable
private fun SwitchReminder() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text("Switch Reminder", style = MaterialTheme.typography.titleMedium)
        Switch(checked = true, onCheckedChange = {})
    }
}

@Composable
private fun TimeSelector() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text("Select Time", style = MaterialTheme.typography.titleMedium)
        CustomButtonSelectTime()
    }
}

@Composable
private fun CustomButtonSelectTime() {
    Surface(color = ColorScheme.primaryContainer, shape = MaterialTheme.shapes.medium) {
        Text(
            "20:00",
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
            style = MaterialTheme.typography.titleMedium
        )
    }
}

@Composable
private fun DaySelector() {
    LazyRow(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
        items(ReminderDay.entries) { day ->
            ButtonSelector(day)
        }
    }
}

@Composable
private fun ButtonSelector(day: ReminderDay) {
    Surface(color = ColorScheme.secondaryContainer, shape = MaterialTheme.shapes.medium) {
        Text(day.title, modifier = Modifier.padding(vertical = 8.dp, horizontal = 12.dp))
    }
}

@Composable
private fun ButtonSaveReminder() {
    CustomButton(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp, bottom = 24.dp),
        contentPadding = PaddingValues(16.dp),
        onClick = {},
        content = { Text("Save Reminder") },
    )
}

@Preview
@Composable
private fun ReminderScreenPReview() {
    AppTheme {
        ReminderScreen()
    }
}