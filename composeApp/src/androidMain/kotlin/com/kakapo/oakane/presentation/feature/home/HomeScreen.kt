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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.kakapo.common.showToast
import com.kakapo.oakane.presentation.designSystem.component.button.CustomIconButton
import com.kakapo.oakane.presentation.designSystem.theme.AppTheme
import com.kakapo.oakane.presentation.feature.home.component.GoalHeaderView
import com.kakapo.oakane.presentation.feature.home.component.MonthlyBudgetView
import com.kakapo.oakane.presentation.feature.home.component.PrimaryWalletView
import com.kakapo.oakane.presentation.feature.home.component.ShowMoreButtonView
import com.kakapo.oakane.presentation.ui.component.item.GoalItemView
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
    navigateToTransactions: () -> Unit,
    navigateToTransaction: (Long) -> Unit,
    navigateToAddGoal: () -> Unit,
    navigateToGoals: () -> Unit,
    navigateToGoal: (Long) -> Unit,
    navigateToMonthlyBudget: () -> Unit,
    navigateToWallets: () -> Unit
) {
    val context = LocalContext.current
    val viewModel = koinViewModel<HomeViewModel>()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.uiEffect.collect { effect ->
            when (effect) {
                is HomeEffect.ToTransaction -> navigateToTransaction.invoke(effect.id)
                is HomeEffect.ToGoalWith -> navigateToGoal.invoke(effect.id)
                is HomeEffect.ShowError -> context.showToast(effect.message)
                HomeEffect.ToCreateTransaction -> navigateToAddTransaction.invoke()
                HomeEffect.ToTransactions -> navigateToTransactions.invoke()
                HomeEffect.OpenDrawer -> openDrawer.invoke()
                HomeEffect.ToCreateGoal -> navigateToAddGoal.invoke()
                HomeEffect.ToMonthlyBudget -> navigateToMonthlyBudget.invoke()
                HomeEffect.ToGoals -> navigateToGoals.invoke()
                HomeEffect.ToWallets -> navigateToWallets.invoke()
            }
        }
    }

    LaunchedEffect(Unit) {
        viewModel.initializeData()
    }

    HomeScreen(uiState = uiState, onEvent = viewModel::handleEvent)
}

@Composable
private fun HomeScreen(uiState: HomeState, onEvent: (HomeEvent) -> Unit) {
    Scaffold(
        topBar = {
            HomeTopAppBar(onEvent)
        },
        content = { paddingValues ->
            HomeContentView(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                uiState = uiState,
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun HomeTopAppBar(onEvent: (HomeEvent) -> Unit) {
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
}

@Composable
private fun HomeContentView(
    modifier: Modifier,
    uiState: HomeState,
    onEvent: (HomeEvent) -> Unit
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(vertical = 24.dp, horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            PrimaryWalletView(uiState, onEvent)
        }
        item {
            MonthlyBudgetView(uiState = uiState, onEvent = onEvent)
        }
        item {
            Text(
                text = "Recent Transaction",
                style = MaterialTheme.typography.titleMedium
            )
        }
        items(uiState.transactions, key = { "transaction_${it.id}" }) { transaction ->
            TransactionItemView(
                transaction = transaction,
                theme = uiState.theme,
                onClick = { onEvent.invoke(HomeEvent.ToTransactionWith(transaction.id)) }
            )
        }
        item {
            ShowMoreButtonView(
                isVisible = uiState.transactions.size == 3,
                onClick = { onEvent.invoke(HomeEvent.ToTransactions) }
            )
        }
        item {
            GoalHeaderView(
                isVisible = uiState.goals.isEmpty(),
                theme = uiState.theme,
                onAddItem = { onEvent.invoke(HomeEvent.ToCreateGoal) }
            )
        }
        items(uiState.goals, key = { "goal_${it.id}" }) { goal ->
            GoalItemView(goal, uiState.theme,onClicked = { onEvent.invoke(HomeEvent.ToGoalWith(id = goal.id)) })
        }
        item {
            ShowMoreButtonView(
                isVisible = uiState.goals.size > 3,
                onClick = { onEvent.invoke(HomeEvent.ToGoals) }
            )
        }
    }
}

@Preview
@Composable
private fun HomeScreenPreview() {
    AppTheme {
        HomeScreen(uiState = HomeState(), onEvent = {})
    }

}