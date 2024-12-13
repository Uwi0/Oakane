package com.kakapo.oakane.presentation.feature.wallets.component

import android.net.Uri
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kakapo.oakane.R
import com.kakapo.oakane.common.toFormatIDR
import com.kakapo.oakane.presentation.designSystem.component.image.CustomDynamicAsyncImage
import com.kakapo.oakane.presentation.designSystem.theme.AppTheme
import com.kakapo.oakane.presentation.ui.component.ColumnWrapper

@Composable
internal fun WalletItemView() {
    ColumnWrapper(
        modifier = Modifier.padding(vertical = 12.dp, horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        WalletTopContent()
        Text("Rp. 20.0000.0000", style = MaterialTheme.typography.headlineMedium)
        HorizontalDivider()
        WalletBottomContent()
    }
}

@Composable
private fun WalletTopContent() {
    val imgUrl by remember { mutableStateOf<Uri?>(null) }
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        CustomDynamicAsyncImage(
            imageUrl = imgUrl,
            modifier = Modifier
                .size(30.dp)
                .clip(CircleShape),
            placeholder = painterResource(R.drawable.mona_empty_wallet)
        )
        Text("My Wallet", style = MaterialTheme.typography.titleMedium)
        Spacer(modifier = Modifier.weight(1f))
        OutlinedCheckmarkRadioButton(
            selected = true,
            onClick = { },
        )
    }
}

@Composable
private fun WalletBottomContent() {
    Row(
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Spacer(Modifier.weight(1f))
        BalanceContent(
            title = "Income This Month",
            amount = 200000.0,
            color = MaterialTheme.colorScheme.primary
        )
        VerticalDivider(Modifier.height(40.dp), thickness = 2.dp)
        BalanceContent(
            title = "Expense This Month",
            amount = 100000.0,
            color = MaterialTheme.colorScheme.error
        )
        Spacer(Modifier.weight(1f))
    }
}

@Composable
private fun BalanceContent(title: String, amount: Double, color: Color) {
    Column(
        modifier = Modifier
            .sizeIn(minWidth = 120.dp)
            .padding(top = 8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(title, color = color, style = MaterialTheme.typography.labelMedium)
        Text(text = "Rp ${amount.toFormatIDR()}")
    }
}

@Composable
fun OutlinedCheckmarkRadioButton(
    selected: Boolean,
    onClick: () -> Unit
) {
    val color = if (selected) MaterialTheme.colorScheme.primary
    else MaterialTheme.colorScheme.outline
    Surface(shape = CircleShape, border = BorderStroke(2.dp, color), onClick = onClick) {
        Column(
            modifier = Modifier.size(24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (selected) {
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = "Selected",
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(16.dp)
                )
            }
        }

    }
}

@Preview
@Composable
private fun WalletItemPreview() {
    AppTheme {
        WalletItemView()
    }

}