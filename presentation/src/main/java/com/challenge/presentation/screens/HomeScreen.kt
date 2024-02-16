package com.challenge.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.challenge.domain.model.Article
import com.challenge.presentation.R
import com.challenge.presentation.common.composables.ArticleCard
import com.challenge.presentation.common.composables.ShimmerEffect
import com.challenge.presentation.viewmodels.MainViewModel
import kotlin.random.Random

@Composable
fun HomeScreen(
    navigateToDetail: (String) -> Unit,
    mainViewModel: MainViewModel,
) {
    val articles by mainViewModel.articles.collectAsState()
    val isLoading by mainViewModel.isLoading.collectAsState()

    val articlesBySection = articles.groupBy(Article::section)
    val sectionColors =
        remember(articlesBySection.keys) {
            articlesBySection.keys.map { getRandomColor() }.toList()
        }

    if (isLoading) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(15.dp),
        ) {
            items(3) {
                ShimmerSectionEffect()
            }
        }
    } else {
        if (articlesBySection.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize(),
            ) {
                Text(
                    modifier = Modifier.align(Alignment.Center),
                    text = stringResource(id = R.string.no_data_to_display),
                )
            }
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(15.dp),
            ) {
                articlesBySection.entries.forEachIndexed { index, (section, articlesInSection) ->
                    val sectionColor = sectionColors.getOrElse(index) { Color.Black }

                    item {
                        Column {
                            Row(
                                modifier =
                                    Modifier
                                        .fillMaxWidth()
                                        .padding(horizontal = 20.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(4.dp),
                            ) {
                                Box(
                                    modifier =
                                        Modifier
                                            .size(6.dp)
                                            .clip(CircleShape)
                                            .background(sectionColor),
                                )
                                Text(
                                    text = section.uppercase(),
                                    textAlign = TextAlign.Start,
                                    style = MaterialTheme.typography.titleMedium,
                                )
                            }

                            LazyRow(
                                modifier = Modifier.fillMaxWidth(),
                                contentPadding = PaddingValues(horizontal = 8.dp),
                                horizontalArrangement = Arrangement.spacedBy(12.dp),
                            ) {
                                items(articlesInSection) { article ->
                                    ArticleCard(
                                        title = article.title,
                                        source = article.source,
                                        byline = article.byline,
                                        publishedDate = article.publishedDate,
                                        media = article.media.firstOrNull(),
                                    ) {
                                        navigateToDetail(article.id.toString())
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun ShimmerSectionEffect() {
    Column(
        modifier = Modifier.padding(16.dp),
    ) {
        ShimmerEffect(
            color = MaterialTheme.colorScheme.primary.copy(alpha = 0.3f),
            modifier = Modifier.size(80.dp, 20.dp),
            shape = RoundedCornerShape(10.dp),
        )

        Spacer(Modifier.height(8.dp))

        LazyRow(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            items(3) {
                ShimmerEffect(
                    color = MaterialTheme.colorScheme.primary.copy(alpha = 0.3f),
                    modifier = Modifier.size(310.dp, 200.dp),
                    shape = RoundedCornerShape(25.dp),
                )
                Spacer(modifier = Modifier.height(15.dp))
            }
        }
    }
}

private fun getRandomColor() =
    Color(
        red = Random.nextFloat(),
        green = Random.nextFloat(),
        blue = Random.nextFloat(),
    )
