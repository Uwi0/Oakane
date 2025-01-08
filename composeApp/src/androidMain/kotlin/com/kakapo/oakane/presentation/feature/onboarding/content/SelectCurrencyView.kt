package com.kakapo.oakane.presentation.feature.onboarding.content

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
internal fun SelectCurrencyView() {
    Column(modifier = Modifier.fillMaxSize().padding(vertical = 24.dp, horizontal = 16.dp)) {
        Text("Select Currency")
    }
}