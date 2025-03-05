package com.corgicoder.foodtruck.ui.components.filter

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.corgicoder.foodtruck.data.model.Filter

@Composable
fun FilterBar(filterIds: List<String>, onFilterClick: (Filter) -> Unit){
    Row (modifier = Modifier.padding(8.dp)){
        filterIds.forEach { filterId ->
            FilterButton(
                name = filterId.name,
                imageUrl = filterId.imageUrl,
                onClick = { onFilterClick(filterId)}
            )
        }
    }
}