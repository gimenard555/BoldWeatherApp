package com.jimenard.boldweatherapp.presentation.ui.screens.search

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jimenard.boldweatherapp.domain.model.Location

@Suppress("ktlint:standard:function-naming")
@Composable
fun LocationItemList(
    location: Location,
    onItemTap: (Location) -> Unit,
) {
    Card(
        modifier =
            Modifier
                .padding(8.dp)
                .fillMaxWidth()
                .clickable { onItemTap.invoke(location) },
        shape = RoundedCornerShape(8.dp),
    ) {
        Column(
            modifier =
                Modifier
                    .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.05f))
                    .padding(16.dp),
        ) {
            Text(
                text = location.name,
                style =
                    MaterialTheme.typography.bodyLarge.copy(
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                    ),
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = location.region,
                style =
                    MaterialTheme.typography.bodyMedium.copy(
                        color = Color.Gray,
                        fontSize = 16.sp,
                    ),
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = location.country,
                style =
                    MaterialTheme.typography.bodySmall.copy(
                        color = Color.DarkGray,
                        fontSize = 14.sp,
                    ),
            )
        }
    }
}
