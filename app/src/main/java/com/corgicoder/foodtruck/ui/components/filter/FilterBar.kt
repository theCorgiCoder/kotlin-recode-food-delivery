package com.corgicoder.foodtruck.ui.components.filter

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.corgicoder.foodtruck.data.model.Filter

@Composable
fun FilterBar(filters: List<Filter>, onFilterClick: (Filter) -> Unit){
    Row (modifier = Modifier.padding(8.dp)){
        filters.forEach { filter ->
            FilterButton(
                name = filter.name,
                imageUrl = filter.imageUrl,
                onClick = { onFilterClick(filter)}
            )
        }
    }
}