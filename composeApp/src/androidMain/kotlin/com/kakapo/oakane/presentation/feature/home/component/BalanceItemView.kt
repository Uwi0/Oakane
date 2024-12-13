package com.kakapo.oakane.presentation.feature.home.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.kakapo.oakane.common.toFormatIDRWithCurrency
import com.kakapo.oakane.model.transaction.TransactionType
import com.kakapo.oakane.presentation.ui.component.TransactionTypeIcon

@Composable
internal fun BalanceItemView(
    modifier: Modifier = Modifier,
    balance: Double,
    type: TransactionType
) {
    ConstraintLayout(modifier = modifier) {
        val (icon, detail) = createRefs()
        TransactionTypeIcon(
            modifier =
            Modifier.constrainAs(icon) {
                top.linkTo(parent.top)
                end.linkTo(parent.end)
            },
            type = type,
            iconSize = 16.dp
        )
        Column(
            modifier = Modifier.constrainAs(detail) {
                top.linkTo(icon.bottom, 8.dp)
                start.linkTo(parent.start)
                bottom.linkTo(parent.bottom)
            },
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            val title = if (type == TransactionType.Income) "Total Income" else "Total Expense"
            Text(text = title, color = MaterialTheme.colorScheme.outline)
            Text(
                text = balance.toFormatIDRWithCurrency(),
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}