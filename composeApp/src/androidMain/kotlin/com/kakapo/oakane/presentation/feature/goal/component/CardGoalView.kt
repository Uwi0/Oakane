package com.kakapo.oakane.presentation.feature.goal.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountBalanceWallet
import androidx.compose.material.icons.outlined.Flag
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kakapo.oakane.R
import com.kakapo.oakane.common.utils.getSavedImageUri
import com.kakapo.oakane.presentation.designSystem.component.image.CustomDynamicAsyncImage
import com.kakapo.oakane.presentation.designSystem.component.progressIndicator.CustomProgressIndicatorView
import com.kakapo.oakane.presentation.designSystem.theme.AppTheme
import com.kakapo.oakane.presentation.ui.component.RowWrapper
import com.kakapo.oakane.presentation.viewModel.goal.GoalState

@Composable
internal fun CardGoalView(uiState: GoalState) {
    RowWrapper {
        CardImageView(fileUrl = uiState.fileName)
        CardContentView(modifier = Modifier.weight(1f), uiState = uiState)
    }
}

@Composable
private fun CardImageView(fileUrl: String) {
    val context = LocalContext.current
    val imgUri = context.getSavedImageUri(fileUrl)
    CustomDynamicAsyncImage(
        modifier = Modifier
            .size(48.dp)
            .clip(CircleShape),
        imageUrl = imgUri,
        placeholder = painterResource(R.drawable.fubuki_stare)
    )
}

@Composable
private fun CardContentView(modifier: Modifier, uiState: GoalState) {
    Column(modifier = modifier, verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text(text = uiState.goalName, style = MaterialTheme.typography.titleMedium)
        SavingProgressView(uiState = uiState)
        CustomProgressIndicatorView(uiState.progress)
    }
}

@Composable
private fun SavingProgressView(uiState: GoalState) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        CardContentWithIconView(
            modifier = Modifier.weight(1f),
            icon = Icons.Outlined.AccountBalanceWallet,
            title = "Saved",
            content = "Rp ${uiState.saved}"
        )
        VerticalDivider(modifier = Modifier.height(30.dp))
        CardContentWithIconView(
            modifier = Modifier.weight(1f),
            icon = Icons.Outlined.Flag,
            title = "Target",
            content = "Rp ${uiState.target}"
        )
    }
}


@Preview
@Composable
private fun CardGoalPreview() {
    val state = GoalState(
        fileName = "",
        goalName = "Some Goal",
        saved = 500.0,
        target = 1000.0
    )
    AppTheme {
        CardGoalView(state)
    }
}