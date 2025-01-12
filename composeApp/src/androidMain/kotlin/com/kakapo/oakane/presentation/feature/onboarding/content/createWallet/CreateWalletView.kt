package com.kakapo.oakane.presentation.feature.onboarding.content.createWallet

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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kakapo.common.toColorLong
import com.kakapo.oakane.R
import com.kakapo.oakane.presentation.designSystem.component.button.CustomButton
import com.kakapo.oakane.presentation.designSystem.component.button.CustomOutlinedButton
import com.kakapo.oakane.presentation.designSystem.theme.AppTheme
import com.kakapo.oakane.presentation.model.WalletSheetContent
import com.kakapo.oakane.presentation.ui.component.sheet.CreateWalletSheetContentView
import com.kakapo.oakane.presentation.ui.component.sheet.SelectColorView
import com.kakapo.oakane.presentation.ui.component.sheet.SelectIconSheetView
import com.kakapo.oakane.presentation.viewModel.onboarding.OnBoardingEvent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun CreateWalletView(onEvent: (OnBoardingEvent) -> Unit) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val createWalletState = rememberCreateWalletState {
        onEvent(OnBoardingEvent.ConfirmWallet(it))
    }

    LaunchedEffect(createWalletState.isSheetVisible) {
        if(!createWalletState.isSheetVisible) {
            sheetState.hide()
        }
    }

    CreateWalletContentView(state = createWalletState,onEvent = onEvent)

    if (createWalletState.isSheetVisible) {
        WalletSheetView(
            createWalletState = createWalletState,
            sheetState = sheetState
        )
    }
}

@Composable
private fun CreateWalletContentView(state: CreateWalletState,onEvent: (OnBoardingEvent) -> Unit) {
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
                onClick = { state.isSheetVisible = true},
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun WalletSheetView(
    createWalletState: CreateWalletState,
    sheetState: SheetState,
) {
    ModalBottomSheet(
        sheetState = sheetState,
        onDismissRequest = { createWalletState.isSheetVisible = false }
    ) {
        when (createWalletState.sheetContent) {
            WalletSheetContent.Create -> CreateWalletSheetContentView(
                walletName = createWalletState.walletName,
                selectedIcon = createWalletState.selectedIcon,
                colorSelector = createWalletState.colorSelector,
                isEditMode = false,
                onEvent = createWalletState::walletSheetEvent
            )

            WalletSheetContent.SelectIcon -> SelectIconSheetView(
                defaultColor = createWalletState.color.toColorLong(),
                selectionIcon = createWalletState.selectedIcon.defaultIcon,
                onSelectedIcon = createWalletState::onSelectedIcon,
                onPickImage = createWalletState::onPickImage,
                onConfirm = createWalletState::confirmIcon
            )

            WalletSheetContent.SelectColor -> SelectColorView(
                defaultColor = createWalletState.color.toColorLong(),
                onSelectedColor = createWalletState::changeColor
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
