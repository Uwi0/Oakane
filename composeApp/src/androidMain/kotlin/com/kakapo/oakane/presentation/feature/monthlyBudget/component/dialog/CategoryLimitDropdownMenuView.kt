package com.kakapo.oakane.presentation.feature.monthlyBudget.component.dialog

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.PopupProperties
import com.kakapo.common.getImageUriFromFileName
import com.kakapo.model.category.CategoryModel
import com.kakapo.oakane.presentation.designSystem.component.image.CustomDynamicAsyncImage
import com.kakapo.oakane.presentation.ui.model.asIcon

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryLimitDropdownMenuView(state: AddCategoryLimitState) {

    LaunchedEffect(state.selectedOptionText) {
        state.filterCategories(state.selectedOptionText)
    }

    ExposedDropdownMenuBox(
        expanded = state.expanded,
        onExpandedChange = state::changeExpanded
    ) {
        OutlinedTextField(
            modifier = Modifier.menuAnchor(MenuAnchorType.PrimaryEditable),
            value = state.selectedOptionText,
            onValueChange = state::changeSelectedOptionText,
            shape = MaterialTheme.shapes.small,
            placeholder = { Text(state.selectedCategory.name) },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(state.expanded) },
            leadingIcon = { LeadingIcon(state.selectedCategory) },
            singleLine = true
        )

        if (state.filteredOptions.isNotEmpty()) {
            DropdownMenu(
                modifier = Modifier.heightIn(max = 180.dp),
                properties = PopupProperties(focusable = false),
                expanded = state.expanded,
                onDismissRequest = { state.expanded = false },
            ) {
                state.filteredOptions.forEach { categoryOption ->
                    DropdownMenuItem(
                        modifier = Modifier.fillMaxWidth(),
                        text = { Text(categoryOption.name) },
                        leadingIcon = { LeadingIcon(categoryOption) },
                        onClick = { state.onClickedCategory(categoryOption) }
                    )
                }
            }
        }
    }
}

@Composable
private fun LeadingIcon(category: CategoryModel) {
    if (category.isDefault) {
        val icon = category.iconName.asIcon()
        Icon(
            modifier = Modifier.size(24.dp),
            painter = painterResource(icon),
            contentDescription = null
        )
    } else {
        val context = LocalContext.current
        CustomDynamicAsyncImage(
            modifier = Modifier
                .size(24.dp)
                .clip(CircleShape),
            imageUrl = context.getImageUriFromFileName(category.icon).getOrNull(),
            contentScale = ContentScale.Crop
        )
    }
}