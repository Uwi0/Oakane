package com.kakapo.oakane.presentation.feature.settings

import android.content.Intent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.sharp.ArrowForwardIos
import androidx.compose.material.icons.filled.Brightness6
import androidx.compose.material.icons.outlined.Backup
import androidx.compose.material.icons.outlined.ImportExport
import androidx.compose.material.icons.outlined.Payments
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import co.touchlab.kermit.Logger
import com.kakapo.common.getCurrentDateWith
import com.kakapo.common.showToast
import com.kakapo.model.system.Theme
import com.kakapo.oakane.presentation.designSystem.component.button.CustomOutlinedButton
import com.kakapo.oakane.presentation.designSystem.component.button.ToggleSwitchComponentView
import com.kakapo.oakane.presentation.designSystem.component.topAppBar.CustomNavigationMenuTopAppBarView
import com.kakapo.oakane.presentation.designSystem.theme.AppTheme
import com.kakapo.oakane.presentation.feature.settings.component.AlarmComponentView
import com.kakapo.oakane.presentation.feature.settings.component.ButtonSettingsView
import com.kakapo.oakane.presentation.feature.settings.component.SelectCurrencySheet
import com.kakapo.oakane.presentation.feature.settings.component.dialog.DialogSettingsView
import com.kakapo.oakane.presentation.feature.settings.component.dialog.asString
import com.kakapo.oakane.presentation.viewModel.settings.SettingsDialogContent
import com.kakapo.oakane.presentation.viewModel.settings.SettingsEffect
import com.kakapo.oakane.presentation.viewModel.settings.SettingsEvent
import com.kakapo.oakane.presentation.viewModel.settings.SettingsState
import com.kakapo.oakane.presentation.viewModel.settings.SettingsViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.androidx.compose.koinViewModel

private const val DATE_FORMAT = "dd-MM-yyyy-HH-mm-ss"

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun SettingsRoute(
    showDrawer: Boolean,
    openDrawer: () -> Unit,
    navigateBack: () -> Unit,
    onSelectedTheme: (Theme) -> Unit
) {
    val viewModel = koinViewModel<SettingsViewModel>()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    var json by remember { mutableStateOf("test") }

    val createJsonLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        val data = result.data
        val uri = data?.data
        uri?.let { safeUri ->
            context.contentResolver.openOutputStream(safeUri)?.let { outputStream ->
                scope.launch(Dispatchers.IO) {
                    try {
                        outputStream.use { it.write(json.toByteArray()) }
                        context.showToast("Success create back up")
                    } catch (e: Exception) {
                        context.showToast("Error create back up")
                    }
                }
            }
        }
    }

    val retrieveJsonLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        val data = result.data
        val uri = data?.data
        uri?.let { fileUri ->
            context.contentResolver.openInputStream(fileUri)?.let { inputStream ->
                scope.launch {
                    withContext(Dispatchers.IO) {
                        val retrievedJson = inputStream.bufferedReader().use { it.readText() }
                        inputStream.close()
                        viewModel.handleEvent(SettingsEvent.RetrieveBackupFile(retrievedJson))
                    }
                }
            }
        }
    }

    LaunchedEffect(Unit) {
        viewModel.initData(showDrawer)
    }

    LaunchedEffect(Unit) {
        viewModel.uiEffect.collect { effect ->
            when (effect) {
                SettingsEffect.NavigateBack -> navigateBack.invoke()
                SettingsEffect.RestoreBackupFile -> retrieveJsonLauncher.launch(openDocumentIntent())
                is SettingsEffect.ShowError -> context.showToast(effect.message)
                is SettingsEffect.Confirm -> onSelectedTheme.invoke(effect.theme)
                SettingsEffect.SuccessChangeCurrency -> sheetState.hide()
                SettingsEffect.OpenDrawer -> openDrawer.invoke()
                is SettingsEffect.GenerateBackupFile -> {
                    json = effect.json
                    Logger.d("json: $json")
                    createJsonLauncher.launch(createNewDocumentIntent())
                }

            }
        }
    }

    SettingsScreen(state = uiState, onEvent = viewModel::handleEvent)

    if (uiState.isSheetShown) {
        SelectCurrencySheet(
            currency = uiState.currency,
            theme = uiState.theme,
            sheetState = sheetState,
            onEvent = viewModel::handleEvent,
        )
    }

    if (uiState.dialogShown) {
        DialogSettingsView(
            state = uiState,
            onEvent = viewModel::handleEvent
        )
    }
}

fun createNewDocumentIntent(): Intent {
    val fileName = "backup-oakane-${getCurrentDateWith(DATE_FORMAT)}.json"
    val intent = Intent(Intent.ACTION_CREATE_DOCUMENT).apply {
        addCategory(Intent.CATEGORY_OPENABLE)
        type = "application/json"
        putExtra(Intent.EXTRA_TITLE, fileName)
    }
    intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
    intent.setFlags(Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION)
    intent.setFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
    return intent
}

fun openDocumentIntent(): Intent {
    val intent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
        addCategory(Intent.CATEGORY_OPENABLE)
        type = "application/json"
    }
    intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
    intent.setFlags(Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION)
    intent.setFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
    return intent
}

@Composable
private fun SettingsScreen(state: SettingsState, onEvent: (SettingsEvent) -> Unit) {
    Scaffold(
        topBar = {
            CustomNavigationMenuTopAppBarView(
                title = "Settings",
                showDrawer = state.showDrawer,
                onNavigateBack = { onEvent.invoke(SettingsEvent.NavigateBack) },
                openMenu = { onEvent.invoke(SettingsEvent.OpenDrawer) }
            )
        },
        content = { paddingValues ->
            SettingContentView(
                modifier = Modifier
                    .padding(paddingValues)
                    .padding(vertical = 24.dp, horizontal = 16.dp),
                uiState = state,
                onEvent = onEvent
            )
        }
    )
}

@Composable
private fun SettingContentView(
    modifier: Modifier = Modifier,
    uiState: SettingsState,
    onEvent: (SettingsEvent) -> Unit
) {
    val dialogContent = SettingsDialogContent.Theme
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        ChangeCurrencyButtonView(onEvent, uiState)
        ThemeButtonView(
            theme = uiState.theme,
            onClick = { onEvent.invoke(SettingsEvent.ShowDialog(content = dialogContent, shown = true)) }
        )
        ToggleSwitchComponentView(
            title = "Recurring Monthly Budget",
            checked = uiState.isRecurringBudget,
            onCheckedChange = { onEvent.invoke(SettingsEvent.ToggleRecurringBudget(it)) }
        )
        ToggleSwitchComponentView(
            title = "Recurring Category Limit",
            checked = uiState.isRecurringCategoryLimit,
            enable = uiState.isRecurringBudget,
            onCheckedChange = { onEvent.invoke(SettingsEvent.ToggleRecurringCategoryLimit(it)) }
        )
        HorizontalDivider()
        ButtonSettingsView(
            title = "Back Up Data",
            icon = Icons.Outlined.Backup,
            theme = uiState.theme,
            onClick = { onEvent.invoke(SettingsEvent.GenerateBackupFile) }
        )
        ButtonSettingsView(
            title = "Import Data",
            theme = uiState.theme,
            icon = Icons.Outlined.ImportExport,
            onClick = { onEvent.invoke(SettingsEvent.RestoreBackupFile) }
        )
        HorizontalDivider()
        AlarmComponentView(state = uiState, onEvent = onEvent)
    }
}

@Composable
private fun ChangeCurrencyButtonView(
    onEvent: (SettingsEvent) -> Unit,
    uiState: SettingsState
) {
    CustomOutlinedButton(
        modifier = Modifier.fillMaxWidth(),
        contentPadding = PaddingValues(16.dp),
        onClick = { onEvent.invoke(SettingsEvent.OnSheet(shown = true)) }
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(Icons.Outlined.Payments, contentDescription = null)
            Text("Set Currency")
            Spacer(modifier = Modifier.weight(1f))
            Text(uiState.currency.name)
            Icon(Icons.AutoMirrored.Sharp.ArrowForwardIos, contentDescription = null)
        }
    }
}

@Composable
private fun ThemeButtonView(theme: Theme, onClick: () -> Unit) {
    Surface(onClick = onClick) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.Brightness6,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary
            )
            Column {
                Text(
                    text = "Theme",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.primary
                )
                Text(
                    text = theme.asString(),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.outline
                )
            }
        }
    }
}

@Preview
@Composable
private fun SettingScreenPreview() {
    AppTheme {
        SettingsScreen(state = SettingsState()) { }
    }
}