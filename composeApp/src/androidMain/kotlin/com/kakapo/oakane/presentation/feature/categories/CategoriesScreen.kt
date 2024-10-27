package com.kakapo.oakane.presentation.feature.categories

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.kakapo.oakane.model.category.CategoryModel
import com.kakapo.oakane.presentation.ui.model.asIconCategory
import com.kakapo.oakane.presentation.viewModel.categories.CategoriesViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
internal fun CategoriesRoute() {
    val viewModel = koinViewModel<CategoriesViewModel>()
    val categories by viewModel.categories.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.initializeData()
    }

    CategoriesScreen(categories)
}

@Composable
private fun CategoriesScreen(categories: List<CategoryModel>) {
    Scaffold(
        content = { paddingValues ->
            LazyColumn(
                modifier = Modifier
                    .padding(paddingValues)
                    .padding(vertical = 24.dp, horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(categories) { category ->
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        val (icon, color) = category.icon.asIconCategory()
                        val contentColor = if (color.luminance() < 0.4f) Color.White else Color.Black
                        Surface(color = color, shape = CircleShape) {
                            Icon(
                                modifier = Modifier.padding(16.dp),
                                painter = painterResource(id = icon),
                                contentDescription = null,
                                tint = contentColor
                            )
                        }
                        Spacer(modifier = Modifier.width(16.dp))
                        Text(text = category.name)
                    }
                }
            }
        }
    )
}