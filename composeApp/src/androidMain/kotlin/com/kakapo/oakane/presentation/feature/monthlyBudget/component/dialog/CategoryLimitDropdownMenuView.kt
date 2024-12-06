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
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.PopupProperties
import com.kakapo.oakane.common.utils.getSavedImageUri
import com.kakapo.oakane.model.category.CategoryModel
import com.kakapo.oakane.presentation.designSystem.component.image.CustomDynamicAsyncImage
import com.kakapo.oakane.presentation.ui.model.asIcon
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryLimitDropdownMenuView(expenseCategories: List<CategoryModel>) {
    val categories by remember { mutableStateOf(expenseCategories) }
    var expanded by remember { mutableStateOf(false) }
    var selectedCategory by remember { mutableStateOf(categories.first()) }
    var selectedOptionText by remember { mutableStateOf(selectedCategory.name) }
    var filteredOptions by remember { mutableStateOf(categories) }
    var userStartedTyping by remember { mutableStateOf(false) }

    LaunchedEffect(selectedOptionText) {
        if (userStartedTyping) {
            delay(500)
            filteredOptions = if (selectedOptionText.isNotEmpty()) {
                categories.filter {
                    it.name.contains(selectedOptionText, ignoreCase = true)
                }
            } else {
                categories
            }
            expanded = true
        }
    }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded }
    ) {
        OutlinedTextField(
            modifier = Modifier.menuAnchor(),
            value = selectedOptionText,
            onValueChange = {
                selectedOptionText = it
                userStartedTyping = true
            },
            shape = MaterialTheme.shapes.medium,
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded) },
            leadingIcon = { LeadingIcon(selectedCategory) }
        )

        if (filteredOptions.isNotEmpty()) {
            DropdownMenu(
                modifier = Modifier.heightIn(max = 180.dp),
                properties = PopupProperties(focusable = false),
                expanded = expanded,
                onDismissRequest = { expanded = false },
            ) {
                filteredOptions.forEach { categoryOption ->
                    DropdownMenuItem(
                        modifier = Modifier.fillMaxWidth(),
                        text = { Text(categoryOption.name) },
                        leadingIcon = { LeadingIcon(categoryOption) },
                        onClick = {
                            selectedCategory = categoryOption
                            selectedOptionText = categoryOption.name
                            expanded = false
                            userStartedTyping = false
                        }
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
            imageUrl = context.getSavedImageUri(category.icon).getOrNull(),
            contentScale = ContentScale.Crop
        )
    }
}