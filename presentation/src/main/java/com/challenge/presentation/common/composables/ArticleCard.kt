package com.challenge.presentation.common.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.challenge.domain.model.Media

@Composable
fun ArticleCard(
    title: String,
    source: String,
    byline: String,
    publishedDate: String,
    media: Media? = null,
    onClick: () -> Unit,
) {
    Card(
        modifier =
            Modifier
                .size(width = 330.dp, height = 260.dp),
        colors =
            CardDefaults.outlinedCardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant,
            ),
        shape = RoundedCornerShape(20.dp),
        onClick = onClick,
    ) {
        Column {
            Box {
                media?.mediaMetadata?.lastOrNull()?.let { mData ->
                    ImageItem(
                        modifier =
                            Modifier
                                .fillMaxWidth()
                                .height(200.dp)
                                .align(Alignment.TopCenter),
                        url = mData.url,
                    )
                }

                Box(
                    modifier =
                        Modifier
                            .matchParentSize()
                            .background(Color.Black.copy(alpha = 0.4f)),
                )

                Text(
                    text = title,
                    style =
                        MaterialTheme.typography.bodyLarge.copy(
                            fontSize = 22.sp,
                        ),
                    color = Color.White,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    modifier =
                        Modifier
                            .align(Alignment.BottomStart)
                            .padding(16.dp),
                )
            }

            Column(
                modifier = Modifier.padding(16.dp),
            ) {
                Text(
                    text = byline,
                    style =
                        MaterialTheme.typography.labelSmall.copy(
                            fontStyle = FontStyle.Italic,
                        ),
                    maxLines = 1,
                    fontSize = 12.sp,
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        text = source,
                        style = MaterialTheme.typography.labelSmall,
                        fontSize = 12.sp,
                    )
                    Text(
                        text = " - ",
                        style = MaterialTheme.typography.labelSmall,
                        fontSize = 12.sp,
                    )
                    Text(
                        text = publishedDate,
                        style = MaterialTheme.typography.labelSmall,
                        fontSize = 12.sp,
                    )
                }
            }
        }
    }
}

@Composable
fun ImageItem(
    modifier: Modifier = Modifier,
    url: String,
) {
    Image(
        painter = rememberAsyncImagePainter(url),
        contentDescription = null,
        modifier = modifier,
        contentScale = ContentScale.Crop,
    )
}
