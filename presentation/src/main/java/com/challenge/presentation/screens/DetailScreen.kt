package com.challenge.presentation.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.challenge.domain.model.Article
import com.challenge.presentation.R
import com.challenge.presentation.common.composables.ImageItem
import com.challenge.presentation.common.composables.TopicsList
import com.challenge.presentation.utils.toNonEmptyListOrNull
import com.challenge.presentation.viewmodels.MainViewModel

@Composable
fun DetailScreen(
    itemId: String?,
    mainViewModel: MainViewModel,
) {
    val article by mainViewModel.article.collectAsState()

    LaunchedEffect(Unit) {
        mainViewModel.getArticleById(itemId?.toLongOrNull() ?: 0L)
    }

    article?.let { art ->
        ArticleDetail(
            article = art,
            openUrl = mainViewModel::openLink,
        )
    }
}

@Composable
fun ArticleDetail(
    article: Article,
    openUrl: (String) -> Unit,
) {
    LazyColumn(
        modifier =
            Modifier
                .fillMaxSize()
                .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(15.dp),
    ) {
        item {
            Text(
                text = article.title,
                style = MaterialTheme.typography.headlineMedium,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
            )
            Text(
                text = stringResource(R.string.detail_byline, article.byline, article.publishedDate),
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(top = 4.dp),
            )
            Text(
                text = article.abstract,
                style = MaterialTheme.typography.bodyLarge,
            )
        }

        article.media.firstOrNull()?.let { media ->
            media.mediaMetadata.lastOrNull()?.let { mediaMetadata ->
                item {
                    ImageItem(
                        modifier =
                            Modifier
                                .fillMaxWidth()
                                .height(230.dp),
                        url = mediaMetadata.url,
                        shape = RoundedCornerShape(4.dp),
                    )
                }
            }
        }

        article.adxKeywords.toNonEmptyListOrNull()?.let { topics ->
            item {
                TopicsList(topics = topics)
            }
        }

        item {
            HorizontalDivider()
            DetailItem(label = stringResource(R.string.detail_section_label), value = article.section)
            DetailItem(label = stringResource(R.string.detail_subsection_label), value = article.subsection)
            DetailItem(label = stringResource(R.string.detail_keywords_label), value = article.adxKeywords)
        }

        item {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                OutlinedButton(
                    modifier = Modifier.weight(0.8f).wrapContentHeight(),
                    onClick = { openUrl(article.url) },
                ) {
                    Text(
                        text =
                            buildAnnotatedString {
                                withStyle(style = SpanStyle(textDecoration = TextDecoration.Underline)) {
                                    append(stringResource(R.string.detail_see_more))
                                }
                            },
                    )
                }
            }
        }
    }
}

@Composable
fun DetailItem(
    label: String,
    value: String,
) {
    Column(modifier = Modifier.padding(vertical = 4.dp)) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.primary,
        )
        Text(text = value, style = MaterialTheme.typography.bodyLarge)
    }
}
