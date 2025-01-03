package com.kakapo.oakane.presentation.ui.component.item

import android.net.Uri
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.kakapo.oakane.R
import com.kakapo.common.formatDateWith
import com.kakapo.common.toFormatIDRWithCurrency
import com.kakapo.common.getSavedImageUri
import com.kakapo.oakane.model.GoalModel
import com.kakapo.oakane.presentation.designSystem.component.image.CustomDynamicAsyncImage
import com.kakapo.oakane.presentation.designSystem.component.progressIndicator.CustomProgressIndicatorView
import com.kakapo.oakane.presentation.ui.component.RowWrapper

@Composable
internal fun GoalItemView(goal: GoalModel, onClicked: () -> Unit) {
    val context = LocalContext.current
    val imageUri: Uri? = context.getSavedImageUri(goal.fileName).getOrNull()
    RowWrapper(
        modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp),
        onClick = onClicked
    ) {
        CustomDynamicAsyncImage(
            modifier = Modifier
                .size(64.dp)
                .clip(CircleShape),
            imageUrl = imageUri,
            contentDescription = null,
            placeholder = painterResource(id = R.drawable.img_goal_default)
        )
        Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
            Text(text = goal.goalName, style = MaterialTheme.typography.titleMedium)
            Text(
                text = goal.amount.toFormatIDRWithCurrency(),
                style = MaterialTheme.typography.titleMedium
            )
            CustomProgressIndicatorView(value = goal.progress)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Rp. ${goal.savedMoney}/${goal.progress}%",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.outline
                )
                Text(
                    text = goal.endDate.formatDateWith(pattern = "dd MMM yyyy"),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.outline
                )
            }
        }

    }
}