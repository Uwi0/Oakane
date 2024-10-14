package com.kakapo.oakane.presentation.feature.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.kakapo.oakane.model.dummyGoals
import com.kakapo.oakane.model.transaction.TransactionModel
import com.kakapo.oakane.presentation.feature.home.component.GoalHeaderView
import com.kakapo.oakane.presentation.feature.home.component.GoalItemView
import com.kakapo.oakane.presentation.feature.home.component.MonthlyBudgetView
import com.kakapo.oakane.presentation.feature.home.component.ShowMoreButtonView
import com.kakapo.oakane.presentation.feature.home.component.TotalBalanceView
import com.kakapo.oakane.presentation.feature.home.component.TransactionItemView
import com.kakapo.oakane.presentation.viewModel.home.HomeViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
internal fun HomeRoute(navigateToAddTransaction: () -> Unit) {
    val viewModel = koinViewModel<HomeViewModel>()

    val onEvent: (HomeUiEvent) -> Unit = {
        when(it) {
            OnNavigateToAddTransaction -> navigateToAddTransaction.invoke()
        }
    }

    LaunchedEffect(Unit) {
        viewModel.initializeData()
    }

    val transactions by viewModel.transactions.collectAsStateWithLifecycle()
    HomeScreen(transactions = transactions,onEvent = onEvent)
}

@Composable
private fun HomeScreen(transactions: List<TransactionModel>, onEvent: (HomeUiEvent) -> Unit) {
    Scaffold(
        content = { paddingValues ->
            HomeContentView(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                transactions = transactions
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                shape = MaterialTheme.shapes.medium,
                onClick = { onEvent.invoke(OnNavigateToAddTransaction) }
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = null)
            }
        }
    )
}

@Composable
private fun HomeContentView(modifier: Modifier, transactions: List<TransactionModel>) {
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
        items(transactions) { item ->
            TransactionItemView(item, onClick = {})
        }
        item {
            ShowMoreButtonView(isVisible = transactions.size == 3, onClick = {})
        }
        item {
            GoalHeaderView(isVisible = true, onAddItem = {})
        }
        items(dummyGoals().take(3)){ goal ->
            GoalItemView(goal, onClicked = {})
        }
        item {
            ShowMoreButtonView(isVisible = true, onClick = {})
        }

    }
}

@Preview
@Composable
private fun HomeScreenPreview() {
    HomeScreen(transactions = emptyList()){

    }
}