package com.kakapo.oakane.presentation.feature.home.component

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.kakapo.oakane.presentation.ui.component.ColumnWrapper

@Composable
internal fun TotalBalanceView() {
    ColumnWrapper(modifier = Modifier.padding(12.dp), horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            text = "You're Balance"
        )
        Text(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            text = "Rp. 0",
            style = MaterialTheme.typography.titleMedium
        )
    }
}