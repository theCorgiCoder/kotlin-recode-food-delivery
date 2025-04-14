package com.corgicoder.foodtruck.ui.components.filter

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicText
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp

@Composable
fun FiltersRow(
    filters: List<String>,
    modifier: Modifier = Modifier
) {

    if (filters.isEmpty()) return

    val textStyle = MaterialTheme.typography.bodySmall

    val text = buildAnnotatedString {
        filters.forEachIndexed { index, filter ->
            if (index > 0) {
                append(" • ")
            }
            append(filter)
        }
    }

    BasicText(
        text = text,
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        style = textStyle,
        maxLines = 2,
        overflow = TextOverflow.Ellipsis
    )
}