package com.kakapo.oakane.presentation.feature.onboarding.content

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
internal fun AccountContentView() {
    Column(modifier = Modifier.fillMaxSize()) {
        Text(text = "Oakane")
        Text(text = "Your personal money manager")
        Spacer(modifier = Modifier.size(128.dp))
    }
}