package com.kakapo.oakane.presentation.ui.component.sheet

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
import com.kakapo.common.toColorInt
import com.kakapo.common.saveImageUri
import com.kakapo.common.showToast
import com.kakapo.oakane.model.category.CategoryIconName
import com.kakapo.oakane.model.category.ParentCategory
import com.kakapo.oakane.model.category.categoryMap
import com.kakapo.oakane.presentation.designSystem.component.button.CustomButton
import com.kakapo.oakane.presentation.designSystem.component.button.CustomOutlinedButton
import com.kakapo.oakane.presentation.designSystem.theme.AppTheme
import com.kakapo.oakane.presentation.ui.component.item.category.CategoryIconView
import com.kakapo.oakane.presentation.ui.model.asIcon

@Composable
internal fun SelectIconView(
    defaultColor: Int,
    selectionIcon: CategoryIconName,
    onSelectedIcon: (CategoryIconName) -> Unit,
    onPickImage: (String) -> Unit,
    onConfirm: () -> Unit,
) {

    val context = LocalContext.current
    val mediaType = ActivityResultContracts.PickVisualMedia.ImageOnly
    val singlePhotoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { uri ->
            uri?.let {
                context.saveImageUri(it).fold(
                    onSuccess = { file ->
                        onPickImage.invoke(file)
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
                    val categoryContent = CategoryContentModel(
                        parentCategory = parentCategory,
                        defaultColor = defaultColor,
                        selectedIcon = selectionIcon,
                    )
                    SelectionIconItemView(
                        categoryContent = categoryContent,
                        onSelectedIcon = onSelectedIcon
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
                    onClick = { onConfirm.invoke() }
                ) {
                    Text(text = "Select Image")
                }
            }
        }
    )
}

data class CategoryContentModel(
    val parentCategory: ParentCategory,
    val defaultColor: Int,
    val selectedIcon: CategoryIconName,
)

@Composable
private fun SelectionIconItemView(
    categoryContent: CategoryContentModel,
    onSelectedIcon: (CategoryIconName) -> Unit
) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        SelectionHeaderView(categoryContent.parentCategory)
        ContentItemView(
            categoryContent = categoryContent,
            onSelectedIcon = onSelectedIcon
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
    categoryContent: CategoryContentModel,
    onSelectedIcon: (CategoryIconName) -> Unit
) {
    FlowRow(
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        categoryMap[categoryContent.parentCategory]?.forEach { category ->
            val isSelected = categoryContent.selectedIcon == category
            val selectionIcon = SelectionIconModel(
                icon = category,
                isSelected = isSelected,
                defaultColor = categoryContent.defaultColor
            )
            SelectionIconView(selectionIcon = selectionIcon, onSelectedIcon = onSelectedIcon)
        }
    }
}

data class SelectionIconModel(
    val icon: CategoryIconName,
    val isSelected: Boolean,
    val defaultColor: Int
)

@Composable
private fun SelectionIconView(
    selectionIcon: SelectionIconModel,
    onSelectedIcon: (CategoryIconName) -> Unit
) {
    val color: Color = if (selectionIcon.isSelected) {
        Color(selectionIcon.defaultColor)
    } else {
        MaterialTheme.colorScheme.outline
    }
    val icon = selectionIcon.icon.asIcon()
    CategoryIconView(
        icon = icon,
        color = color,
        onClick = { onSelectedIcon.invoke(selectionIcon.icon) }
    )
}


@Composable
@Preview
private fun SelectCategoryIconViewPrev() {
    AppTheme {
        Surface {
            SelectIconView(
                defaultColor = "0xFF4CAF50".toColorInt(),
                selectionIcon = CategoryIconName.SALARY,
                onSelectedIcon = {},
                onPickImage = {},
                onConfirm = {}
            )
        }
    }
}