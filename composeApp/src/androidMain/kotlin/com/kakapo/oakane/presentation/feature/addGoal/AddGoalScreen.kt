package com.kakapo.oakane.presentation.feature.addGoal

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CalendarToday
import androidx.compose.material.icons.outlined.Event
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.kakapo.oakane.presentation.designSystem.component.button.CustomButton
import com.kakapo.oakane.presentation.designSystem.component.textField.CustomOutlinedTextField
import com.kakapo.oakane.presentation.designSystem.component.textField.OutlinedCurrencyTextField
import com.kakapo.oakane.presentation.designSystem.component.topAppBar.CustomNavigationTopAppBarView
import com.kakapo.oakane.presentation.feature.addGoal.component.DateSelectorView
import com.kakapo.oakane.presentation.feature.addGoal.component.ImageGoalPicker

@Composable
fun AddGoalRoute() {
    AddGoalScreen()
}

@Composable
private fun AddGoalScreen() {
    Scaffold(
        topBar = {
            CustomNavigationTopAppBarView(
                title = "Add Goals",
                onNavigateBack = {}
            )
        },
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(vertical = 24.dp, horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                ImageGoalPicker(onSelectedImage = {})
                Spacer(modifier = Modifier.size(4.dp))
                OutlinedCurrencyTextField(
                    modifier = Modifier.fillMaxWidth(),
                    label = "Target",
                    placeHolder = "0",
                    value = "",
                    prefix = "Rp ",
                    onValueChange = {}
                )
                CustomOutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    label = "Goal Name",
                    placeHolder = "My Goal",
                    value = "",
                    onValueChange = {}
                )
                CustomOutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    label = "Note",
                    placeHolder = "Some Note",
                    value = "",
                    onValueChange = {}
                )
                DateSelectorView(
                    defaultDate = System.currentTimeMillis(),
                    icon = Icons.Outlined.CalendarToday,
                    title = "Starting Date",
                    onClick = {}
                )
                DateSelectorView(
                    defaultDate = System.currentTimeMillis(),
                    icon = Icons.Outlined.Event,
                    title = "End Date",
                    onClick = {}
                )
            }
        },
        bottomBar = {
            CustomButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 24.dp),
                onClick = {},
                contentPadding = PaddingValues(16.dp),
                content = {
                    Text(text = "Save Goal")
                }
            )
        }
    )
}