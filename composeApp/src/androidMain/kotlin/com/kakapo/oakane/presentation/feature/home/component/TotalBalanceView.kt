package com.kakapo.oakane.presentation.feature.home.component

import android.net.Uri
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.kakapo.oakane.R
import com.kakapo.oakane.presentation.designSystem.component.button.CustomIconButton
import com.kakapo.oakane.presentation.designSystem.component.image.CustomDynamicAsyncImage
import com.kakapo.oakane.presentation.designSystem.theme.AppTheme

@Composable
internal fun TotalBalanceView() {
    Surface(shape = MaterialTheme.shapes.medium, shadowElevation = 2.dp) {
        ConstraintLayout(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(vertical = 14.dp, horizontal = 16.dp)
        ) {
            val (currency, balance, visibility, bottomContent) = createRefs()
            Text(
                text = "Rp",
                style = MaterialTheme.typography.labelMedium,
                modifier = Modifier.constrainAs(currency) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                }
            )
            Text(
                text = "20.000.000.000",
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.constrainAs(balance) {
                    top.linkTo(currency.bottom, margin = (-12).dp)
                    start.linkTo(currency.end, margin = 4.dp)
                }
            )
            CustomIconButton(
                icon = Icons.Default.Visibility,
                onClick = {},
                modifier = Modifier.constrainAs(visibility) {
                    top.linkTo(balance.top)
                    bottom.linkTo(balance.bottom)
                    start.linkTo(balance.end, margin = 8.dp)
                }
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.constrainAs(bottomContent){
                    start.linkTo(parent.start)
                    top.linkTo(balance.bottom, margin = 6.dp)
                    end.linkTo(parent.end)
                }
            ) {
                WalletIcon(modifier = Modifier)
                Text(
                    text = "My Wallet",
                    style = MaterialTheme.typography.titleMedium,

                )
                HorizontalDivider(
                    color = MaterialTheme.colorScheme.primaryContainer,
                    thickness = 2.dp
                )
            }
        }
    }
}

@Composable
private fun WalletIcon(modifier: Modifier) {
    val imageUrl by remember { mutableStateOf<Uri?>(null) }
    CustomDynamicAsyncImage(
        imageUrl = imageUrl,
        modifier = modifier
            .size(30.dp)
            .clip(CircleShape),
        placeholder = painterResource(R.drawable.mona_empty_wallet)
    )
}

@Composable
@Preview
private fun TotalBalancePreview() {
    AppTheme {
        TotalBalanceView()
    }

}