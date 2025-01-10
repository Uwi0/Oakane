package com.kakapo.oakane.presentation.feature.onboarding.content

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowForwardIos
import androidx.compose.material.icons.outlined.AccountBalanceWallet
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kakapo.common.toColorLong
import com.kakapo.model.category.CategoryIconName
import com.kakapo.oakane.R
import com.kakapo.oakane.presentation.designSystem.component.button.CustomButton
import com.kakapo.oakane.presentation.designSystem.component.button.CustomOutlinedButton
import com.kakapo.oakane.presentation.designSystem.component.textField.currency.CurrencyTextFieldConfig
import com.kakapo.oakane.presentation.designSystem.component.textField.currency.rememberCurrencyTextFieldState
import com.kakapo.oakane.presentation.designSystem.theme.AppTheme
import com.kakapo.oakane.presentation.designSystem.theme.colorSelector
import com.kakapo.oakane.presentation.ui.component.ColorSelector
import com.kakapo.oakane.presentation.ui.component.SelectedIconModel
import com.kakapo.oakane.presentation.viewModel.onboarding.OnBoardingEvent

@Composable
internal fun CreateWalletView(onEvent: (OnBoardingEvent) -> Unit) {
    var walletName by remember { mutableStateOf("") }
    var startingBalance by remember { mutableStateOf("") }
    var color by remember { mutableStateOf("0xFF4CAF50") }

    val currencyConfig = CurrencyTextFieldConfig(currencySymbol = "RP")
    val currencyState = rememberCurrencyTextFieldState(currencyConfig) {
        startingBalance = it
    }
    var selectedIcon by remember {
        mutableStateOf(SelectedIconModel("", CategoryIconName.WALLET, color.toColorLong()))
    }
    val colorSelector = ColorSelector(
        defaultColor = color.ifEmpty { "0xFF4CAF50" }.toColorLong(),
        colorsHex = colorSelector.map { "$it" }.distinct()
    )

    Surface {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(vertical = 24.dp, horizontal = 16.dp),
        ) {
            Text("Create your wallet", style = MaterialTheme.typography.displayMedium)
            Spacer(Modifier.size(149.dp))
            Image(
                modifier = Modifier
                    .size(220.dp)
                    .clip(CircleShape)
                    .align(Alignment.CenterHorizontally),
                painter = painterResource(id = R.drawable.mona_wallet),
                contentScale = ContentScale.FillBounds,
                contentDescription = null
            )
            Spacer(Modifier.size(20.dp))
            Text(
                text = "You can create your own wallet or skip this step, and the system will automatically create a default wallet for you.",
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(Modifier.weight(1f))
            CustomOutlinedButton(
                modifier = Modifier.fillMaxWidth(),
                contentPadding = PaddingValues(vertical = 12.dp, horizontal = 16.dp),
                onClick = {},
                content = {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.AccountBalanceWallet,
                            contentDescription = null
                        )
                        Text(text = "Create Wallet")
                        Spacer(Modifier.size(24.dp))
                    }
                }
            )
            Spacer(Modifier.size(16.dp))
            CustomButton(
                modifier = Modifier.fillMaxWidth(),
                contentPadding = PaddingValues(vertical = 12.dp, horizontal = 16.dp),
                onClick = { onEvent.invoke(OnBoardingEvent.SkippWallet) },
                content = {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Spacer(Modifier.size(24.dp))
                        Text(text = "Skip")
                        Icon(
                            imageVector = Icons.AutoMirrored.Outlined.ArrowForwardIos,
                            contentDescription = "Skip"
                        )
                    }
                }
            )
        }
    }
}

@Composable
@Preview
private fun CreateWalletPreview() {
    AppTheme {
        CreateWalletView(onEvent = {})
    }
}
