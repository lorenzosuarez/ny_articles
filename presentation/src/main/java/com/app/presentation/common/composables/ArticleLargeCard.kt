package com.app.presentation.common.composables

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.app.domain.model.Media

@Composable
fun ArticleLargeCard(
    title: String,
    section: String,
    source: String,
    byline: String,
    publishedDate: String,
    media: Media? = null,
    onClick: () -> Unit,
) {
    val cardShape = RoundedCornerShape(25.dp)

    Card(
        modifier = Modifier.size(width = 360.dp, height = 260.dp),
        colors = CardDefaults.outlinedCardColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
        shape = cardShape,
        onClick = onClick,
        elevation = CardDefaults.cardElevation(defaultElevation = 12.dp),
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            media?.mediaMetadata?.lastOrNull()?.url?.let { imageUrl ->
                ArticleImage(
                    imageUrl = imageUrl,
                )
            }
            Surface(
                modifier =
                    Modifier
                        .align(Alignment.TopStart)
                        .padding(all = 16.dp),
                shape = RoundedCornerShape(10.dp),
                color = MaterialTheme.colorScheme.surface.copy(alpha = 0.82f),
            ) {
                Text(
                    modifier = Modifier.padding(vertical = 5.dp, horizontal = 6.5.dp),
                    style = MaterialTheme.typography.labelSmall,
                    text = section,
                )
            }
            GradientOverlay()
            ArticleContent(
                modifier = Modifier.align(Alignment.BottomStart),
                title = title,
                byline = byline,
                publishedDate = publishedDate,
            )
        }
    }
}

@Composable
fun ArticleImage(imageUrl: String) {
    Image(
        painter = rememberAsyncImagePainter(imageUrl),
        contentDescription = null,
        modifier = Modifier.fillMaxSize(),
        contentScale = ContentScale.Crop,
    )
}

@Composable
fun GradientOverlay() {
    Box(
        modifier =
            Modifier
                .fillMaxSize()
                .background(
                    brush =
                        Brush.verticalGradient(
                            colors =
                                listOf(
                                    Color.Transparent,
                                    Color.Black.copy(alpha = 0.65f),
                                ),
                        ),
                ),
    )
}

@Composable
fun ArticleContent(
    modifier: Modifier,
    title: String,
    byline: String,
    publishedDate: String,
) {
    Column(
        modifier = modifier.padding(all = 16.dp),
        verticalArrangement = Arrangement.spacedBy(5.dp),
    ) {
        Text(
            text = "$byline - $publishedDate",
            style = MaterialTheme.typography.labelSmall,
            maxLines = 1,
            fontSize = 12.sp,
            color = Color.White,
        )

        Text(
            text = title,
            style = MaterialTheme.typography.bodyLarge.copy(fontSize = 22.sp),
            color = Color.White,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
        )
    }
}

@Preview(name = "Light Mode", showBackground = true)
@Preview(name = "Dark Mode", showBackground = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
fun PreviewArticleLargeCard() {
    MaterialTheme {
        ArticleLargeCard(
            title = "Title: Exploring Compose UI Exploring Compose UI Exploring Compose UI Exploring Compose UI",
            source = "Source: Compose Magazine",
            byline = "By: John Doe",
            publishedDate = "2024-02-18",
            onClick = {},
            section = "section",
        )
    }
}
