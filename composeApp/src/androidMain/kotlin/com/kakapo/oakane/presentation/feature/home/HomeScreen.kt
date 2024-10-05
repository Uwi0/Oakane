package com.kakapo.oakane.presentation.feature.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kakapo.oakane.model.dummyTransaction
import com.kakapo.oakane.presentation.feature.home.component.MonthlyBudgetView
import com.kakapo.oakane.presentation.feature.home.component.TotalBalanceView
import com.kakapo.oakane.presentation.feature.home.component.TransactionItemView

@Composable
internal fun HomeRoute() {

}

@Composable
private fun HomeScreen() {
    Scaffold(
        content = { paddingValues ->
            HomeContentView(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            )
        }
    )
}

@Composable
private fun HomeContentView(modifier: Modifier) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(vertical = 24.dp, horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            TotalBalanceView()
        }
        item {
            MonthlyBudgetView()
        }
        item {
            Text(
                text = "Recent Transaction",
                style = MaterialTheme.typography.titleMedium
            )
        }
        items(dummyTransaction().take(3)) { item ->
            TransactionItemView(item, onClick = {})
        }
    }
}

@Preview
@Composable
private fun HomeScreenPreview() {
    HomeScreen()
}