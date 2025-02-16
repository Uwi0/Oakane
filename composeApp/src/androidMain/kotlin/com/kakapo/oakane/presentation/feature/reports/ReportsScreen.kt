package com.kakapo.oakane.presentation.feature.reports

import android.content.ContentValues
import android.content.Context
import android.os.Environment
import android.provider.MediaStore
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.FileDownload
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.kakapo.common.getCurrentDateWith
import com.kakapo.common.showToast
import com.kakapo.oakane.domain.usecase.toCsvUseCase
import com.kakapo.oakane.presentation.designSystem.component.button.CustomIconButton
import com.kakapo.oakane.presentation.designSystem.component.topAppBar.CustomNavigationTopAppBarView
import com.kakapo.oakane.presentation.feature.reports.component.BudgetContentView
import com.kakapo.oakane.presentation.feature.reports.component.ButtonFilterView
import com.kakapo.oakane.presentation.feature.reports.component.DonutChartComponentView
import com.kakapo.oakane.presentation.feature.reports.component.ReportsItemView
import com.kakapo.oakane.presentation.viewModel.reports.ReportsEffect
import com.kakapo.oakane.presentation.viewModel.reports.ReportsEvent
import com.kakapo.oakane.presentation.viewModel.reports.ReportsState
import com.kakapo.oakane.presentation.viewModel.reports.ReportsViewModel
import org.koin.androidx.compose.koinViewModel
import java.io.File

@Composable
internal fun ReportsRoute(
    navigateBack: () -> Unit
) {
    val context = LocalContext.current
    val viewModel = koinViewModel<ReportsViewModel>()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.initializeData()
    }

    LaunchedEffect(Unit) {
        viewModel.uiEffect.collect { effect ->
            when (effect) {
                ReportsEffect.NavigateBack -> navigateBack.invoke()
                is ReportsEffect.ShowError -> context.showToast(message = effect.message)
                is ReportsEffect.GenerateReport -> {
                    val fileName = uiState.toReportName()
                    val fileReport = effect.reports.toCsvUseCase(context, fileName).await()
                    fileReport?.let { saveToDownloads(context, it) }
                }
            }
        }
    }

    ReportsScreen(uiState = uiState, onEvent = viewModel::handleEVent)
}

private fun ReportsState.toReportName(): String {
    val format = "dd-MM-yyyy-HH-mm-ss"
    return "report-${selectedMonth.name}-${getCurrentDateWith(format)}.csv"
}

fun saveToDownloads(context: Context, file: File) {
    val resolver = context.contentResolver
    val contentValues = ContentValues().apply {
        put(MediaStore.MediaColumns.DISPLAY_NAME, file.name)
        put(MediaStore.MediaColumns.MIME_TYPE, "text/csv")
        put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_DOWNLOADS)
        put(MediaStore.MediaColumns.RELATIVE_PATH, "Download/oakane/report")
    }

    val uri = resolver.insert(MediaStore.Files.getContentUri("external"), contentValues)

    uri?.let {
        resolver.openOutputStream(it)?.use { outputStream ->
            file.inputStream().use { inputStream ->
                inputStream.copyTo(outputStream)
            }
        }
        context.showToast("Report downloaded")
    }
}

@Composable
private fun ReportsScreen(uiState: ReportsState, onEvent: (ReportsEvent) -> Unit) {
    Scaffold(
        topBar = {
            CustomNavigationTopAppBarView(
                title = "Reports",
                actions = {
                    CustomIconButton(
                        icon = Icons.Outlined.FileDownload,
                        onClick = { onEvent.invoke(ReportsEvent.GenerateReport) }
                    )
                },
                onNavigateBack = { onEvent.invoke(ReportsEvent.NavigateBack) }
            )
        },
        content = { paddingValues ->
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentPadding = PaddingValues(vertical = 24.dp, horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                item {
                    ButtonFilterView(uiState = uiState, onEvent = onEvent)
                }
                item {
                    DonutChartComponentView(
                        amount = uiState.totalBalance,
                        proportions = uiState.proportions,
                        colors = uiState.colors,
                        categoriesName = uiState.names,
                        currency = uiState.currency,
                        theme = uiState.theme
                    )
                }
                item {
                    BudgetContentView(uiState)
                }
                items(uiState.reports, key = { it.id }) { report ->
                    ReportsItemView(report)
                }
            }
        }
    )
}