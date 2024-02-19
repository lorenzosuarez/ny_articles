package com.app.presentation.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.app.domain.model.Article
import com.app.presentation.R
import com.app.presentation.common.composables.AnimatedCarousel
import com.app.presentation.common.composables.ArticleLargeCard
import com.app.presentation.common.composables.ArticleSmallCard
import com.app.presentation.common.composables.ShimmerEffect
import com.app.presentation.viewmodels.MainViewModel

@Composable
fun HomeScreen(
    navigateToDetail: (String) -> Unit,
    mainViewModel: MainViewModel,
) {
    val articles by mainViewModel.articles.collectAsState()
    val isLoading by mainViewModel.isLoading.collectAsState()

    when {
        isLoading -> LoadingContent()
        articles.isNotEmpty() ->
            ArticlesContent(
                articles = articles,
                navigateToDetail = navigateToDetail,
            )

        else -> EmptyContent()
    }
}

@Composable
private fun ArticlesContent(
    articles: List<Article>,
    navigateToDetail: (String) -> Unit,
) {
    val articlesBySection = articles.groupBy(Article::section)

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        val singleArticleSections = articlesBySection.filter { it.value.size == 1 }
        if (singleArticleSections.isNotEmpty()) {
            item {
                AnimatedCarousel(
                    items =
                        singleArticleSections.flatMap { (_, articlesInSection) ->
                            articlesInSection.map { article ->
                                @Composable {
                                    ArticleLargeCard(
                                        section = article.section,
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
                        },
                    delayDuration = 2000L,
                )
            }
        }

        val multipleArticleSections = articlesBySection.filter { it.value.size > 1 }
        multipleArticleSections.forEach { (section, articlesInSection) ->
            item {
                Text(
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .padding(start = 10.dp, top = 20.dp),
                    text = section,
                    style =
                        MaterialTheme.typography.titleMedium.copy(
                            textAlign = TextAlign.Start,
                        ),
                )
            }
            items(articlesInSection) { article ->
                ArticleSmallCard(
                    title = article.title,
                    source = article.source,
                    publishedDate = article.publishedDate,
                    media = article.media.firstOrNull(),
                ) {
                    navigateToDetail(article.id.toString())
                }
            }
            item { HorizontalDivider(thickness = 1.dp) }
        }
    }
}

@Composable
fun LoadingContent() {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(15.dp),
    ) {
        items(3) {
            ShimmerSectionEffect()
        }
    }
}

@Composable
fun EmptyContent() {
    Box(modifier = Modifier.fillMaxSize()) {
        Text(
            modifier = Modifier.align(Alignment.Center),
            text = stringResource(id = R.string.no_data_to_display),
            style = MaterialTheme.typography.bodyLarge,
        )
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
            items(3) { // Representa el número de tarjetas de artículos en la fila
                ShimmerEffect(
                    color = MaterialTheme.colorScheme.primary.copy(alpha = 0.3f),
                    modifier = Modifier.size(310.dp, 200.dp),
                    shape = RoundedCornerShape(25.dp),
                )
            }
        }
    }
}
