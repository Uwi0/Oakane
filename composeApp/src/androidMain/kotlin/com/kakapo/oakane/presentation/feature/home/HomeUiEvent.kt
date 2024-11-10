package com.kakapo.oakane.presentation.feature.home

sealed interface HomeUiEvent
data object OnNavigateToAddTransaction : HomeUiEvent
data object OnNavigateToTransactions : HomeUiEvent