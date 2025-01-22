package com.kakapo.oakane.presentation.feature.addTransaction

import android.Manifest
import android.content.ContentValues
import android.content.Context
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Today
import androidx.compose.material.icons.outlined.Category
import androidx.compose.material.icons.outlined.Image
import androidx.compose.material.icons.outlined.PhotoCamera
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import co.touchlab.kermit.Logger
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.kakapo.common.saveImageUriToPublicDirectory
import com.kakapo.common.showToast
import com.kakapo.common.toDateWith
import com.kakapo.model.Currency
import com.kakapo.model.transaction.TransactionType
import com.kakapo.model.transaction.asTransactionType
import com.kakapo.oakane.presentation.designSystem.component.button.CustomButton
import com.kakapo.oakane.presentation.designSystem.component.button.CustomOutlinedButton
import com.kakapo.oakane.presentation.designSystem.component.menu.CustomDropdownMenu
import com.kakapo.oakane.presentation.designSystem.component.textField.CustomClickableOutlinedTextField
import com.kakapo.oakane.presentation.designSystem.component.textField.CustomOutlinedTextField
import com.kakapo.oakane.presentation.designSystem.component.textField.currency.CurrencyTextFieldConfig
import com.kakapo.oakane.presentation.designSystem.component.textField.currency.OutlinedCurrencyTextFieldView
import com.kakapo.oakane.presentation.designSystem.component.textField.currency.rememberCurrencyTextFieldState
import com.kakapo.oakane.presentation.designSystem.component.topAppBar.CustomNavigationTopAppBarView
import com.kakapo.oakane.presentation.feature.addTransaction.component.ImageReceiptView
import com.kakapo.oakane.presentation.feature.addTransaction.component.SelectCategorySheet
import com.kakapo.oakane.presentation.ui.component.dialog.CustomDatePickerDialog
import com.kakapo.oakane.presentation.viewModel.addTransaction.AddTransactionEffect
import com.kakapo.oakane.presentation.viewModel.addTransaction.AddTransactionEvent
import com.kakapo.oakane.presentation.viewModel.addTransaction.AddTransactionState
import com.kakapo.oakane.presentation.viewModel.addTransaction.AddTransactionViewModel
import org.koin.androidx.compose.koinViewModel
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class, ExperimentalPermissionsApi::class)
@Composable
internal fun AddTransactionRoute(transactionId: Long, navigateBack: () -> Unit) {
    val context = LocalContext.current
    val viewModel = koinViewModel<AddTransactionViewModel>()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val permissionsState = rememberMultiplePermissionsState(
        permissions = listOf(
            Manifest.permission.CAMERA,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        )
    )
    var imageUri by remember { mutableStateOf<Uri?>(null) }
    var imageFileName by remember { mutableStateOf("") }

    val photoLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture(),
        onResult = { success ->
            if (success) {
                context.showToast("Photo saved successfully at: $imageUri")
                viewModel.handleEvent(AddTransactionEvent.SaveImageFile(imageFileName))
            } else {
                context.showToast("Failed to capture photo")
            }
        }
    )

    val imageLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { uri ->
            uri?.let {
                imageUri = uri
                context.saveImageUriToPublicDirectory(uri).fold(
                    onSuccess = { file ->
                        context.showToast("Image saved successfully at: $file")
                        viewModel.handleEvent(AddTransactionEvent.SaveImageFile(file))
                    },
                    onFailure = {
                        context.showToast("Failed to save image")
                    }
                )
            }
        }
    )

    val launchPhotoLauncher = {
        val uri = createImageUri(context)
        if (uri.first != null) {
            imageUri = uri.first
            imageFileName = uri.second
            photoLauncher.launch(uri.first!!)
        } else {
            Logger.e { "Failed to create file for photo" }
        }
    }

    val savePhoto = {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            if (permissionsState.permissions[0].status.isGranted) {
                launchPhotoLauncher.invoke()
            } else {
                permissionsState.launchMultiplePermissionRequest()
            }
        } else {
            if (permissionsState.allPermissionsGranted) {
                launchPhotoLauncher.invoke()
            } else {
                permissionsState.launchMultiplePermissionRequest()
            }
        }
    }

    val mediaType = ActivityResultContracts.PickVisualMedia.ImageOnly
    val mediaRequest = PickVisualMediaRequest(mediaType)

    LaunchedEffect(Unit) {
        viewModel.uiSideEffect.collect { effect ->
            when (effect) {
                AddTransactionEffect.NavigateBack -> navigateBack.invoke()
                is AddTransactionEffect.ShowError -> context.showToast(effect.message)
                AddTransactionEffect.TakePhoto -> savePhoto.invoke()
                AddTransactionEffect.PickImage -> imageLauncher.launch(mediaRequest)
            }
        }
    }

    LaunchedEffect(Unit) {
        viewModel.initializeData(transactionId)
    }

    AddTransactionScreen(
        uiState = uiState,
        onEvent = viewModel::handleEvent,
    )

    if (uiState.isShowDialog) {
        CustomDatePickerDialog(
            initialValue = uiState.date,
            onDismiss = { viewModel.handleEvent(AddTransactionEvent.Dialog(shown = false)) },
            onConfirm = { viewModel.handleEvent(AddTransactionEvent.ChangeDate(it)) }
        )
    }

    if (uiState.sheetShown) {
        SelectCategorySheet(
            sheetState = sheetState,
            uiState = uiState,
            onEvent = viewModel::handleEvent
        )
    }
}

private fun createImageUri(context: Context): Pair<Uri?, String> {
    val fileName = generateFilename()
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
        val contentValues = ContentValues().apply {
            put(MediaStore.MediaColumns.DISPLAY_NAME, fileName)
            put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg")
            put(MediaStore.MediaColumns.RELATIVE_PATH, "Pictures/Oakane")
        }
        val uri = context.contentResolver.insert(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            contentValues
        )
        uri to "${fileName}.jpg"
    } else {
        val picturesDir = File(
            context.getExternalFilesDir(Environment.DIRECTORY_PICTURES),
            "Oakane"
        )
        if (!picturesDir.exists()) {
            picturesDir.mkdirs()
        }
        val file = File(picturesDir, "${fileName}.jpg")
        Uri.fromFile(file) to fileName
    }
}

private fun generateFilename(): String {
    val timestamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
    return "IMG_OAKANE_$timestamp"
}

@Composable
private fun AddTransactionScreen(
    uiState: AddTransactionState,
    onEvent: (AddTransactionEvent) -> Unit,
) {
    Scaffold(
        topBar = {
            val screenTitle = if (uiState.isEditMode) "Edit Transaction" else "Add Transaction"
            CustomNavigationTopAppBarView(
                title = screenTitle,
                onNavigateBack = { onEvent.invoke(AddTransactionEvent.NavigateBack) }
            )
        },
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(vertical = 24.dp, horizontal = 16.dp)
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                CustomOutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    placeHolder = "Title",
                    value = uiState.title,
                    onValueChange = { onEvent.invoke(AddTransactionEvent.ChangedTitle(it)) }
                )
                AddTransactionCurrencyTextField(currency = uiState.currency, onEvent = onEvent)
                TransactionTypeDropDown(uiState, onEvent)
                CustomClickableOutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    placeHolder = "Category",
                    trailingIcon = Icons.Outlined.Category,
                    value = uiState.category.name,
                    onClick = { onEvent.invoke(AddTransactionEvent.Sheet(shown = true)) }
                )
                CustomClickableOutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    placeHolder = "Date",
                    trailingIcon = Icons.Default.Today,
                    value = uiState.date.toDateWith(format = "dd MMM yyyy"),
                    onClick = { onEvent.invoke(AddTransactionEvent.Dialog(shown = true)) }
                )
                CustomOutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    placeHolder = "Note",
                    value = uiState.note,
                    onValueChange = { onEvent.invoke(AddTransactionEvent.ChangeNote(it)) }
                )
                TakeImageButtonView(onEvent = onEvent)
                ImageReceiptView(uiState, onEvent)
            }
        },
        bottomBar = {
            val buttonTitle = if (uiState.isEditMode) "Save" else "Add"
            CustomButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp, bottom = 24.dp),
                contentPadding = PaddingValues(vertical = 16.dp),
                onClick = { onEvent.invoke(AddTransactionEvent.SaveTransaction) },
                content = {
                    Text(text = buttonTitle)
                }
            )
        }
    )
}

@Composable
private fun TakeImageButtonView(onEvent: (AddTransactionEvent) -> Unit) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text(text = "Image Transaction")
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            TakeImageButtonItemView(
                title = "Camera",
                icon = Icons.Outlined.PhotoCamera,
                onClick = { onEvent.invoke(AddTransactionEvent.TakePhoto) }
            )
            TakeImageButtonItemView(
                title = "Gallery",
                icon = Icons.Outlined.Image,
                onClick = { onEvent.invoke(AddTransactionEvent.PickImage) }
            )
        }
    }
}

@Composable
private fun RowScope.TakeImageButtonItemView(
    title: String,
    icon: ImageVector,
    onClick: () -> Unit
) {
    CustomOutlinedButton(
        modifier = Modifier.Companion.weight(1f),
        contentPadding = PaddingValues(16.dp),
        onClick = onClick,
        content = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = title)
                Icon(imageVector = icon, contentDescription = null)
            }
        }
    )
}

@Composable
private fun AddTransactionCurrencyTextField(
    currency: Currency,
    onEvent: (AddTransactionEvent) -> Unit
) {
    val textFieldConfig = CurrencyTextFieldConfig(
        Locale(currency.languageCode, currency.countryCode),
        currencySymbol = currency.symbol
    )
    val state = rememberCurrencyTextFieldState(textFieldConfig) { amount ->
        onEvent(AddTransactionEvent.ChangedAmount(amount))
    }
    OutlinedCurrencyTextFieldView(
        modifier = Modifier.fillMaxWidth(),
        state = state,
        label = { Text("Amount") }
    )
}

@Composable
private fun TransactionTypeDropDown(
    uiState: AddTransactionState,
    onEvent: (AddTransactionEvent) -> Unit
) {
    CustomDropdownMenu(
        options = TransactionType.entries.map { it.name },
        expanded = uiState.isDropdownExpanded,
        onExpandedChange = { onEvent.invoke(AddTransactionEvent.DropDownTypeIs(expanded = it)) },
        value = uiState.transactionType.name,
        label = "Transaction Type",
        onDismissRequest = { onEvent.invoke(AddTransactionEvent.DropDownTypeIs(expanded = false)) },
        onClick = { onEvent.invoke(AddTransactionEvent.ChangeType(it.asTransactionType())) }
    )
}

