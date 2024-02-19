package com.app.presentation.common.composables

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerDefaults
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay

/**
 * Displays an animated carousel that cycles through a list of composable.
 *
 * @param items A list of composable to display in the carousel.
 * @param delayDuration The time in milliseconds to wait before transitioning to the next item.
 */
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AnimatedCarousel(
    items: List<@Composable () -> Unit>,
    delayDuration: Long = 2000L,
) {
    val pagerState = rememberPagerState(pageCount = { items.size })

    HorizontalPager(
        state = pagerState,
        modifier =
            Modifier
                .fillMaxWidth()
                .height(200.dp),
        contentPadding = PaddingValues(horizontal = 62.dp),
        flingBehavior = PagerDefaults.flingBehavior(state = pagerState),
    ) { page ->
        val isCurrentPage = page == pagerState.currentPage
        val scale by animateFloatAsState(targetValue = if (isCurrentPage) 1f else 0.9f, label = "")
        val alpha by animateFloatAsState(targetValue = if (isCurrentPage) 1f else 0.7f, label = "")

        Card(
            modifier =
                Modifier
                    .graphicsLayer {
                        scaleX = scale
                        scaleY = scale
                        this.alpha = alpha
                    }
                    .padding(vertical = 8.dp, horizontal = 4.dp)
                    .fillMaxHeight(),
            shape = RoundedCornerShape(25.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = if (page == pagerState.currentPage) 6.dp else 3.dp),
        ) {
            items[page % items.size].invoke()
        }
    }

    LaunchedEffect(pagerState) {
        while (true) {
            delay(delayDuration)
            with(pagerState) {
                val nextPage = (currentPage + 1) % items.size
                if (nextPage == 0) {
                    animateScrollToPage(0)
                } else {
                    animateScrollToPage(nextPage)
                }
            }
        }
    }
}
