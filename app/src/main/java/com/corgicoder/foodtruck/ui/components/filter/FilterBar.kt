package com.corgicoder.foodtruck.ui.components.filter

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.corgicoder.foodtruck.data.model.FilterData

@Composable
fun FilterBar(
    filters: List<FilterData>,
    selectedFilterIds: List<String?>,
    onFilterToggled: (String) -> Unit,
    modifier: Modifier,
) {
    LazyRow (
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ){
       items(filters.size) { index ->
           val filter = filters[index]
           FilterButton (
               selected = selectedFilterIds.contains(filter.id),
               onClick = { onFilterToggled(filter.id) },
               imageUrl = filter.imageUrl,
               name = filter.name,
               filterId = filter.id
           )
       }
    }
}