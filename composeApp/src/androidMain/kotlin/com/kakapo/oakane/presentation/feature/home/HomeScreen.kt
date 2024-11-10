package com.kakapo.oakane.presentation.feature.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.kakapo.oakane.model.dummyGoals
import com.kakapo.oakane.model.transaction.TransactionModel
import com.kakapo.oakane.presentation.designSystem.component.button.CustomIconButton
import com.kakapo.oakane.presentation.designSystem.theme.AppTheme
import com.kakapo.oakane.presentation.feature.home.component.GoalHeaderView
import com.kakapo.oakane.presentation.feature.home.component.GoalItemView
import com.kakapo.oakane.presentation.feature.home.component.MonthlyBudgetView
import com.kakapo.oakane.presentation.feature.home.component.ShowMoreButtonView
import com.kakapo.oakane.presentation.feature.home.component.TotalBalanceView
import com.kakapo.oakane.presentation.ui.component.item.TransactionItemView
import com.kakapo.oakane.presentation.viewModel.home.HomeEffect
import com.kakapo.oakane.presentation.viewModel.home.HomeEvent
import com.kakapo.oakane.presentation.viewModel.home.HomeState
import com.kakapo.oakane.presentation.viewModel.home.HomeViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
internal fun HomeRoute(
    openDrawer: () -> Unit,
    navigateToAddTransaction: () -> Unit,
    navigateToTransactions: () -> Unit
) {
    val viewModel = koinViewModel<HomeViewModel>()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.uiEffect.collect { effect ->
            when (effect) {
                HomeEffect.ToCreateTransaction -> navigateToAddTransaction.invoke()
                HomeEffect.ToTransactions -> navigateToTransactions.invoke()
                HomeEffect.OpenDrawer -> openDrawer.invoke()
            }
        }
    }

    LaunchedEffect(Unit) {
        viewModel.initializeData()
    }

    HomeScreen(uiState = uiState, onEvent = viewModel::handleEvent)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun HomeScreen(uiState: HomeState, onEvent: (HomeEvent) -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("Dashboard")
                },
                navigationIcon = {
                    CustomIconButton(
                        icon = Icons.Default.Menu,
                        onClick = { onEvent.invoke(HomeEvent.OpenDrawer) }
                    )
                }
            )
        },
        content = { paddingValues ->
            HomeContentView(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                transactions = uiState.transactions,
                onEvent = onEvent
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                shape = MaterialTheme.shapes.medium,
                onClick = { onEvent.invoke(HomeEvent.ToCreateTransaction) }
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = null)
            }
        }
    )
}

@Composable
private fun HomeContentView(
    modifier: Modifier,
    transactions: List<TransactionModel>,
    onEvent: (HomeEvent) -> Unit
) {
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
            ShowMoreButtonView(
                isVisible = transactions.size == 3,
                onClick = { onEvent.invoke(HomeEvent.ToTransactions) }
            )
        }
        item {
            GoalHeaderView(isVisible = dummyGoals().size > 3, onAddItem = {})
        }
        items(dummyGoals().take(3)) { goal ->
            GoalItemView(goal, onClicked = {})
        }
        item {
            ShowMoreButtonView(isVisible = dummyGoals().size > 3, onClick = {})
        }

    }
}

@Preview
@Composable
private fun HomeScreenPreview() {
    AppTheme {
        HomeScreen(uiState = HomeState()) {

        }
    }

}