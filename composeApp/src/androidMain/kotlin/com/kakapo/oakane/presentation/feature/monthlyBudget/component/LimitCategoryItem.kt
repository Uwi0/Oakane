package com.kakapo.oakane.presentation.feature.monthlyBudget.component

import android.net.Uri
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.kakapo.oakane.R
import com.kakapo.oakane.presentation.designSystem.component.image.CustomDynamicAsyncImage
import com.kakapo.oakane.presentation.designSystem.component.progressIndicator.CustomProgressIndicatorView

@Composable
internal fun LimitCategoryItemView() {
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