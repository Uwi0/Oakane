package com.kakapo.oakane.presentation.feature.wallets.component.sheet.content

import android.net.Uri
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.kakapo.oakane.R
import com.kakapo.oakane.presentation.designSystem.component.button.CustomButton
import com.kakapo.oakane.presentation.designSystem.component.image.CustomDynamicAsyncImage
import com.kakapo.oakane.presentation.ui.component.ColorSelector
import com.kakapo.oakane.presentation.ui.component.HorizontalColorSelectorView
import com.kakapo.oakane.presentation.viewModel.wallets.WalletsState
import java.text.NumberFormat
import java.util.Locale

@Composable
internal fun CreateWalletContentView(uiState: WalletsState) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp, bottom = 24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        CreateWalletContent()
        StartBalanceContent()
        CurrencyContent()
        ColorContent(uiState = uiState)
        Spacer(Modifier.size(48.dp))
        CustomButton(
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(16.dp),
            onClick = {},
            content = { Text("Save Wallet")}
        )
    }
}

@Composable
private fun CreateWalletContent() {
    val imageUrls by remember { mutableStateOf<Uri?>(null) }
    ColumnContent(title = "Create Wallet") {
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            CustomDynamicAsyncImage(
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape),
                imageUrl = imageUrls,
                placeholder = painterResource(R.drawable.mona_empty_wallet)
            )
            OutlinedTextField(
                modifier = Modifier.weight(1f),
                value = "",
                onValueChange = {},
                shape = MaterialTheme.shapes.medium,
                placeholder = { Text(text = "Wallet Name") }
            )
        }
    }
}

@Composable
private fun StartBalanceContent() {
    ColumnContent(title = "Start Balance") {
        StartBalanceTextField(value = "", onValueChange = {})
    }
}

@Composable
private fun CurrencyContent() {
    ColumnContent(title = "Currency") {
        Surface(
            shape = MaterialTheme.shapes.medium,
            border = BorderStroke(2.dp, color = MaterialTheme.colorScheme.outline)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("IDR", style = MaterialTheme.typography.bodyLarge)
                Icon(imageVector = Icons.Default.ChevronRight, contentDescription = null)
            }
        }
    }
}

@Composable
private fun ColorContent(uiState: WalletsState){
    val colorSelector = ColorSelector(defaultColor = uiState.defaultColor, colorsHex = uiState.colors)
    ColumnContent(title = "Wallet Color") {
        HorizontalColorSelectorView(
            colorSelector = colorSelector,
            onClickBrush = {},
            onClickColor = {}
        )
    }
}

@Composable
private fun ColumnContent(title: String, content: @Composable ColumnScope.() -> Unit) {
    Column(modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text(text = title, style = MaterialTheme.typography.titleMedium)
        content.invoke(this)
    }
}

@Composable
internal fun StartBalanceTextField(value: String, onValueChange: (String) -> Unit) {

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
        value = formattedValue,
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
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        placeholder = {
            Text("0")
        },
        prefix = {
            Text("Rp.")
        },
        shape = MaterialTheme.shapes.medium,
    )
}
