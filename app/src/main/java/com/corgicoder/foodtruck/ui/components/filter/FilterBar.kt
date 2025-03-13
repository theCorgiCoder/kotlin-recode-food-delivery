package com.corgicoder.foodtruck.ui.components.filter

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.corgicoder.foodtruck.data.model.FilterData
import com.corgicoder.foodtruck.feature.home.HomeViewModel

@Composable
fun FilterBar(
    filters: List<FilterData>,
    selectedFilterId: String?,
    onFilterToggled: (String) -> Unit,
    viewModel: HomeViewModel
) {
    LazyRow (
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ){
       items(filters.size) { index ->
           val filter = filters[index]
           FilterButton (
               selected = selectedFilterId == filter.id,
               onClick = { onFilterToggled(filter.id) },
               imageUrl = filter.imageUrl,
               name = filter.name
           )
       }
    }
}