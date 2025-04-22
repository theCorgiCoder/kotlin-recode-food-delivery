package com.corgicoder.foodtruck.ui.components.filter

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.corgicoder.foodtruck.ui.theme.SubtitleText

@Composable
fun FiltersRow(
    filters: List<String>,
    modifier: Modifier = Modifier
) {

    if (filters.isEmpty()) return

    val text = buildAnnotatedString {
        filters.forEachIndexed { index, filter ->
            if (index > 0) {
                append(" • ")
            }
            append(filter)
        }
    }

    Text(
        text = text,
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        style = MaterialTheme.typography.titleSmall,
        maxLines = 2,
        overflow = TextOverflow.Ellipsis,
        color = SubtitleText,
    )
}