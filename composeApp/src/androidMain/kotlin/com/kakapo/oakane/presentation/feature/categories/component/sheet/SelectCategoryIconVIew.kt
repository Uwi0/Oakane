package com.kakapo.oakane.presentation.feature.categories.component.sheet

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kakapo.oakane.common.utils.saveImageUri
import com.kakapo.oakane.common.utils.showToast
import com.kakapo.oakane.model.category.CategoryIconName
import com.kakapo.oakane.model.category.ParentCategory
import com.kakapo.oakane.model.category.categoryMap
import com.kakapo.oakane.presentation.designSystem.component.button.CustomButton
import com.kakapo.oakane.presentation.designSystem.component.button.CustomOutlinedButton
import com.kakapo.oakane.presentation.designSystem.theme.AppTheme
import com.kakapo.oakane.presentation.feature.categories.component.CategoryIconView
import com.kakapo.oakane.presentation.ui.model.asIcon
import com.kakapo.oakane.presentation.viewModel.categories.CategoriesEvent
import com.kakapo.oakane.presentation.viewModel.categories.CategoriesState

@Composable
internal fun SelectCategoryIconView(
    uiState: CategoriesState,
    onEvent: (CategoriesEvent) -> Unit
) {

    val context = LocalContext.current
    val mediaType = ActivityResultContracts.PickVisualMedia.ImageOnly
    val singlePhotoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { uri ->
            uri?.let {
                context.saveImageUri(it).fold(
                    onSuccess = { file ->
                        onEvent.invoke(CategoriesEvent.PickImage(file))
                    },
                    onFailure = {
                        context.showToast("Failed to save image")
                    }
                )
            }
        }
    )

    Scaffold(
        topBar = {
            Text(
                text = "Choose Icon",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
        },
        content = { paddingValues ->
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(paddingValues),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                contentPadding = PaddingValues(16.dp)
            ) {
                items(categoryMap.keys.toList(), key = { it.name }) { parentCategory ->
                    SelectionIconItemView(
                        uiState = uiState,
                        parentCategory = parentCategory,
                        onEvent = { iconName ->
                            onEvent.invoke(CategoriesEvent.SelectedIcon(iconName))
                        }
                    )
                }
            }
        },
        bottomBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                CustomOutlinedButton(
                    onClick = {
                        singlePhotoPickerLauncher.launch(PickVisualMediaRequest(mediaType))
                    }
                ) {
                    Text(text = "Take From Gallery")
                }
                CustomButton(
                    modifier = Modifier.weight(1f),
                    onClick = { onEvent.invoke(CategoriesEvent.ConfirmIcon) }
                ) {
                    Text(text = "Select Image")
                }
            }
        }
    )
}

@Composable
private fun SelectionIconItemView(
    uiState: CategoriesState,
    parentCategory: ParentCategory,
    onEvent: (CategoryIconName) -> Unit
) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        SelectionHeaderView(parentCategory)
        ContentItemView(
            uiState = uiState,
            parentCategory = parentCategory,
            onEvent = onEvent
        )
    }
}

@Composable
private fun SelectionHeaderView(parentCategory: ParentCategory) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        HorizontalDivider(
            modifier = Modifier.weight(1f)
        )
        Text(
            text = parentCategory.displayName,
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(horizontal = 8.dp)
        )
        HorizontalDivider(
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
@OptIn(ExperimentalLayoutApi::class)
private fun ContentItemView(
    uiState: CategoriesState,
    parentCategory: ParentCategory,
    onEvent: (CategoryIconName) -> Unit
) {
    FlowRow {
        categoryMap[parentCategory]?.forEach { category ->
            SelectionIconView(uiState = uiState, category, onEvent = onEvent)
        }
    }
}

@Composable
private fun SelectionIconView(
    uiState: CategoriesState,
    category: CategoryIconName,
    onEvent: (CategoryIconName) -> Unit
) {
    val isSelected = uiState.defaultIcon == category
    val color: Color = if (isSelected) {
        Color(uiState.defaultSelectedColor)
    } else {
        MaterialTheme.colorScheme.outline
    }
    val icon = category.asIcon()
    CategoryIconView(
        icon = icon,
        color = color,
        onClick = { onEvent.invoke(category) }
    )
}


@Composable
@Preview
private fun SelectCategoryIconViewPrev() {
    AppTheme {
        Surface {
            SelectCategoryIconView(uiState = CategoriesState()) {

            }
        }
    }
}