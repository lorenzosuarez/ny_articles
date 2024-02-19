package com.app.presentation.common.composables

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun TopicsList(topics: List<String>) {
    Column(modifier = Modifier.fillMaxWidth().padding(1.dp)) {
        Text(
            text = "Topics",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
        )
        Spacer(modifier = Modifier.height(8.dp))
        FlowRow(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.Top,
            horizontalArrangement = Arrangement.spacedBy(1.dp),
        ) {
            topics.forEach { topic ->
                SuggestionChip(
                    modifier = Modifier.padding(end = 1.dp),
                    border =
                        BorderStroke(
                            width = 1.5.dp,
                            color = MaterialTheme.colorScheme.secondary,
                        ),
                    shape = RoundedCornerShape(10.dp),
                    label = { Text(text = "#$topic", fontSize = 10.sp) },
                    onClick = {},
                )
            }
        }
    }
}
