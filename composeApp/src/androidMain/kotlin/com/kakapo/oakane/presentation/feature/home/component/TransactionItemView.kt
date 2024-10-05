package com.kakapo.oakane.presentation.feature.home.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kakapo.oakane.R
import com.kakapo.oakane.common.asTextEllipsis
import com.kakapo.oakane.model.TransactionModel
import com.kakapo.oakane.model.dummyTransaction
import com.kakapo.oakane.presentation.ui.component.RowWrapper
import com.kakapo.oakane.presentation.ui.component.transactionColor

@Composable
internal fun TransactionItemView(transaction: TransactionModel, onClick: () -> Unit) {
    RowWrapper(
        modifier = Modifier.clickable { onClick.invoke() },
    ) {
        Image(
            painter = painterResource(R.drawable.fubuki_stare),
            contentDescription = null,
            modifier = Modifier.size(48.dp).clip(CircleShape),
            contentScale = ContentScale.Crop
        )
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Text(
                text = transaction.title.asTextEllipsis(15),
                style = MaterialTheme.typography.titleMedium
            )
            Text(text = transaction.tag)
        }
        Spacer(modifier = Modifier.weight(1f))
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.End
        ) {
            Text(
                text = "Rp. 0",
                color = transactionColor(transaction.category).second,
                style = MaterialTheme.typography.titleMedium
            )
            Text(text = transaction.date)
        }
    }
}

@Composable
@Preview
private fun TransactionItemViewPrev() {
    Surface {
        TransactionItemView(transaction = dummyTransaction().first()) { }
    }
}