package com.corgicoder.foodtruck.ui.components.button

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.corgicoder.foodtruck.R

@Composable
fun BackButton(
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier
) {
        IconButton(
            onClick = onNavigateBack,
            modifier = modifier
                .background(Color(0x80F8F8F8), CircleShape)
        ) {
            Box(
                modifier = Modifier.fillMaxSize().padding(top = 8.dp),
                contentAlignment = Alignment.Center
            ){
                Icon(
                    painter = painterResource(id = R.drawable.back_arrow_icon),
                    contentDescription = "Navigate back arrow icon",
                    modifier = Modifier.size(24.dp)
                )
            }

        }
}