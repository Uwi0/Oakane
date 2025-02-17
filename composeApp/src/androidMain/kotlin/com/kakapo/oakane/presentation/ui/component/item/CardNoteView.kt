package com.kakapo.oakane.presentation.ui.component.item

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.kakapo.model.system.Theme
import com.kakapo.oakane.presentation.ui.component.ColumnWrapper

@Composable
internal fun CardNoteView(note: String, theme: Theme) {
    ColumnWrapper(modifier = Modifier.padding(vertical = 12.dp, horizontal = 16.dp), theme = theme) {
        Text(text = "Note", style = MaterialTheme.typography.titleMedium)
        Text(text = note)
    }
}