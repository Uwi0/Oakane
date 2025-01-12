package com.kakapo.oakane.presentation.ui.component.sheet

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kakapo.model.Currency
import com.kakapo.oakane.presentation.designSystem.component.button.CustomButton
import com.kakapo.oakane.presentation.designSystem.component.button.OutlinedCheckmarkRadioButton
import com.kakapo.oakane.presentation.designSystem.component.textField.SearchTextFieldView
import com.kakapo.oakane.presentation.designSystem.theme.AppTheme
import com.kakapo.oakane.presentation.ui.component.RowWrapper

@Composable
internal fun SelectCurrencyView(currency: Currency,onConfirm: (Currency) -> Unit) {
    var query by remember { mutableStateOf("") }
    var selectedCurrency by remember { mutableStateOf(currency) }
    val currencies = Currency.entries.filter {
        it.countryName.lowercase().contains(query.lowercase(), ignoreCase = true) ||
                it.name.lowercase().contains(query.lowercase(), ignoreCase = true)
    }
    Scaffold(
        topBar = {
            CurrencyTopContentView(
                query = query,
                currency = selectedCurrency,
                onQueryChange = { query = it })
        },
        content = { paddingValues ->
            LazyColumn(
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize(),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(currencies, key = { it.ordinal }) { currency ->
                    val selected = selectedCurrency == currency
                    CurrencyItemView(
                        currency = currency,
                        isSelected = selected,
                        onSelected = { selectedCurrency = it }
                    )
                }
            }
        },
        bottomBar = {
            CustomButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp, start = 16.dp, end = 16.dp),
                contentPadding = PaddingValues(vertical = 16.dp, horizontal = 16.dp),
                content = {
                    Text("Confirm Currency")
                },
                onClick = { onConfirm.invoke(selectedCurrency) }
            )
        }
    )
}

@Composable
private fun CurrencyTopContentView(
    query: String,
    currency: Currency,
    onQueryChange: (String) -> Unit
) {
    Surface {
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Column(modifier = Modifier.padding(top = 24.dp, start = 16.dp, end = 16.dp)) {
                Text(text = "Set Currency", style = MaterialTheme.typography.displaySmall)
                Spacer(Modifier.size(24.dp))
                SearchTextFieldView(
                    modifier = Modifier.fillMaxWidth(),
                    value = query,
                    onValueChange = onQueryChange,
                    placeHolder = "Search Currency...",
                )
                Spacer(Modifier.size(8.dp))
                Text(
                    text = "current currency: ${currency.name}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.primary
                )
            }
            HorizontalDivider()
        }
    }

}

@Composable
private fun CurrencyItemView(
    currency: Currency,
    isSelected: Boolean,
    onSelected: (Currency) -> Unit
) {
    RowWrapper(
        onClick = { onSelected.invoke(currency) },
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp, horizontal = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Column {
            Text(text = currency.name, style = MaterialTheme.typography.titleMedium)
            Text(text = currency.countryName, style = MaterialTheme.typography.bodyMedium)
        }
        OutlinedCheckmarkRadioButton(isSelected, onClick = { onSelected.invoke(currency) })
    }
}

@Preview
@Composable
private fun SelectCurrencyPreview() {
    AppTheme {
        SelectCurrencyView(currency = Currency.IDR,onConfirm = {})
    }
}