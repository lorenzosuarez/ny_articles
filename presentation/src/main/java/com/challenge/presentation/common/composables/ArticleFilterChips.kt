package com.challenge.presentation.common.composables

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.challenge.domain.model.ArticleFilter
import com.challenge.presentation.R

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
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        items(ArticleFilter.entries) { filter ->
            val title =
                stringResource(
                    when (filter) {
                        ArticleFilter.EMAILED -> R.string.filter_emailed
                        ArticleFilter.SHARED -> R.string.filter_shared
                        ArticleFilter.VIEWED -> R.string.filter_viewed
                    },
                )

            FilterChip(
                modifier = Modifier.padding(all = 2.dp),
                selected = filter == selectedFilter,
                onClick = { onFilterSelected(filter) },
                label = {
                    Text(
                        text = title,
                        color = MaterialTheme.colorScheme.primary,
                        fontSize = 22.sp,
                    )
                },
                leadingIcon = {
                    AnimatedVisibility(visible = filter == selectedFilter) {
                        Icon(
                            imageVector = Icons.Filled.Done,
                            contentDescription = "Selected Icon",
                        )
                    }
                },
            )
        }
    }
}
