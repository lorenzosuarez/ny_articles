package com.app.presentation.common.composables

import androidx.annotation.DrawableRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.app.domain.model.ArticleFilter
import com.app.presentation.R

/**
 * Displays a horizontal list of filter chips for selecting article filters.
 *
 * Each chip represents a filter for the articles, such as most emailed, shared, or viewed.
 * When a chip is selected, it visually indicates its selected state and triggers the provided
 * `onFilterSelected` callback with the selected filter.
 *
 * @param modifier The [Modifier] to be applied to the chip layout.
 * @param selectedFilter The currently selected [ArticleFilter], if any. This filter will be highlighted.
 * @param onFilterSelected A callback invoked when a filter chip is selected, passing the selected [ArticleFilter].
 */
@Composable
fun ArticleFilterChips(
    modifier: Modifier = Modifier,
    selectedFilter: ArticleFilter? = null,
    onFilterSelected: (ArticleFilter) -> Unit,
) {
    LazyRow(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(2.dp),
    ) {
        items(ArticleFilter.entries) { filter ->
            val (title, icon) =
                when (filter) {
                    ArticleFilter.EMAILED -> R.string.filter_emailed to R.drawable.ic_email
                    ArticleFilter.SHARED -> R.string.filter_shared to R.drawable.ic_shared
                    ArticleFilter.VIEWED -> R.string.filter_viewed to R.drawable.ic_viewed
                }

            SelectableFilter(
                text = stringResource(id = title),
                isSelected = filter == selectedFilter,
                icon = icon,
                onClick = { onFilterSelected(filter) },
            )
        }
    }
}

@Composable
private fun SelectableFilter(
    text: String,
    isSelected: Boolean,
    @DrawableRes icon: Int,
    onClick: () -> Unit,
) {
    Card(
        modifier =
            Modifier.height(IntrinsicSize.Min),
        shape = RoundedCornerShape(50),
        border = BorderStroke(1.dp, if (isSelected) MaterialTheme.colorScheme.secondaryContainer else Color.Transparent),
        colors = CardDefaults.outlinedCardColors(),
        onClick = onClick,
    ) {
        Row(
            modifier =
                Modifier.fillMaxHeight().padding(horizontal = 15.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp),
        ) {
            Box(
                modifier =
                    Modifier
                        .clip(CircleShape)
                        .border(BorderStroke(0.dp, Color.Transparent), CircleShape)
                        .background(MaterialTheme.colorScheme.secondaryContainer)
                        .padding(2.dp),
            ) {
                Icon(
                    modifier =
                        Modifier
                            .size(24.dp)
                            .padding(5.dp),
                    painter = painterResource(id = icon),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSurface,
                )
            }
            Text(
                text = text,
                style = MaterialTheme.typography.bodyLarge,
            )
        }
    }
}
