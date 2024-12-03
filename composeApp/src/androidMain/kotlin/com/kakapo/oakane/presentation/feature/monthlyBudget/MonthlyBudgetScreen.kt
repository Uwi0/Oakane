package com.kakapo.oakane.presentation.feature.monthlyBudget

import android.net.Uri
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.Assignment
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.kakapo.oakane.R
import com.kakapo.oakane.presentation.designSystem.component.button.CustomButton
import com.kakapo.oakane.presentation.designSystem.component.button.CustomIconButton
import com.kakapo.oakane.presentation.designSystem.component.image.CustomDynamicAsyncImage
import com.kakapo.oakane.presentation.designSystem.component.progressIndicator.CustomProgressIndicatorView
import com.kakapo.oakane.presentation.designSystem.component.topAppBar.CustomNavigationTopAppBarView
import java.text.NumberFormat
import java.util.Locale

@Composable
internal fun MonthlyBudgetRoute() {
    MonthlyBudgetScreen()
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun MonthlyBudgetScreen() {
    Scaffold(
        topBar = {
            CustomNavigationTopAppBarView(title = "Monthly Budget") { }
        },
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .padding(paddingValues)
                    .padding(vertical = 24.dp, horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text(
                        text = "What is you’re Monthly budget ?",
                        style = MaterialTheme.typography.titleLarge
                    )
                    Text(
                        text = "enter the amount of you’re budget, you can change the amount if necessary",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.outline
                    )
                    MonthlyTextField("") { }
                }
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    stickyHeader {
                        Surface {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "Limited Category",
                                    style = MaterialTheme.typography.bodyLarge
                                )
                                CustomIconButton(icon = Icons.Default.Add, onClick = {})
                            }
                        }
                    }
                    items(10) {
                        Surface(shape = MaterialTheme.shapes.medium, shadowElevation = 2.dp) {
                            val context = LocalContext.current
                            val imageUrl by remember { mutableStateOf<Uri?>(null) }
                            Row(
                                modifier = Modifier.padding(vertical = 12.dp, horizontal = 24.dp),
                                horizontalArrangement = Arrangement.spacedBy(8.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                CustomDynamicAsyncImage(
                                    modifier = Modifier
                                        .size(48.dp)
                                        .clip(CircleShape),
                                    imageUrl = imageUrl,
                                    placeholder = painterResource(R.drawable.fubuki_stare),
                                    contentScale = ContentScale.Crop
                                )
                                Column(
                                    modifier = Modifier.padding(16.dp),
                                    verticalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Text(
                                            "Groceries",
                                            style = MaterialTheme.typography.titleMedium
                                        )
                                        Text(
                                            "Rp 200.000",
                                            style = MaterialTheme.typography.titleMedium
                                        )
                                    }
                                    CustomProgressIndicatorView(0.5f)
                                    Text(
                                        text = "Spent: Rp. 100.000/50%",
                                        style = MaterialTheme.typography.labelSmall,
                                        color = MaterialTheme.colorScheme.outline
                                    )
                                }

                            }
                        }
                    }
                }
            }
        },
        bottomBar = {
            CustomButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 24.dp, start = 16.dp, end = 16.dp),
                onClick = {}) {
                Text("Save Budget")
            }
        }
    )
}

@Composable
private fun MonthlyTextField(value: String, onValueChange: (String) -> Unit) {


    var formattedValue by remember { mutableStateOf(value) }

    LaunchedEffect(value) {
        val unformattedValue = value.filter { it.isDigit() }
        formattedValue = if (unformattedValue.isNotEmpty()) {
            val number = unformattedValue.toLongOrNull() ?: 0L
            NumberFormat.getInstance(Locale("in", "ID")).format(number)
        } else {
            ""
        }
    }

    OutlinedTextField(
        modifier = Modifier.fillMaxWidth(),
        value = value,
        onValueChange = { newValue ->
            val unformattedValue = newValue.filter { it.isDigit() }
            val formattedText = if (unformattedValue.isNotEmpty()) {
                val number = unformattedValue.toLongOrNull() ?: 0L
                NumberFormat.getInstance(Locale("in", "ID")).format(number)
            } else {
                ""
            }
            formattedValue = formattedText
            onValueChange(unformattedValue)
        },
        placeholder = {
            Text("0")
        },
        prefix = {
            Text("Rp.")
        },
        leadingIcon = {
            Icon(
                imageVector = Icons.AutoMirrored.Outlined.Assignment,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.outline
            )
        },
        shape = MaterialTheme.shapes.medium,
    )
}