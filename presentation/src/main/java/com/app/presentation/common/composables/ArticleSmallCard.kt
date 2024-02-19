package com.app.presentation.common.composables

import android.content.res.Configuration
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.app.domain.model.Media
import com.app.presentation.R

@Composable
fun ArticleSmallCard(
    title: String,
    source: String,
    publishedDate: String,
    media: Media? = null,
    onClick: () -> Unit,
) {
    var isFavorite by remember { mutableStateOf(false) }

    Card(
        modifier =
            Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
        shape = RectangleShape,
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
        colors =
            CardDefaults.outlinedCardColors(
                containerColor = Color.Transparent,
            ),
        onClick = onClick,
    ) {
        Column(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(all = 12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(6.dp),
        ) {
            media?.mediaMetadata?.lastOrNull()?.url?.let { imageUrl ->
                Card(
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .height(220.dp),
                    shape = RoundedCornerShape(25.dp),
                    colors =
                        CardDefaults.outlinedCardColors(
                            containerColor = Color.Black,
                        ),
                ) {
                    ImageItem(
                        url = imageUrl,
                        modifier = Modifier.fillMaxSize(),
                        contentDescription = null,
                    )
                }
            }

            Column(
                modifier = Modifier.padding(5.dp),
                verticalArrangement = Arrangement.spacedBy(6.dp),
            ) {
                Text(
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .padding(end = 80.dp),
                    text = title,
                    style =
                        MaterialTheme.typography.bodyLarge.copy(fontSize = 18.sp).copy(
                            fontWeight = FontWeight.SemiBold,
                            textAlign = TextAlign.Start,
                        ),
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(3.2.dp),
                    ) {
                        Box(
                            modifier =
                                Modifier
                                    .clip(CircleShape)
                                    .background(Color.Transparent)
                                    .border(BorderStroke(0.5.dp, Color.Black), CircleShape)
                                    .padding(0.5.dp),
                        ) {
                            Icon(
                                modifier =
                                    Modifier
                                        .size(13.dp)
                                        .padding(1.9.dp),
                                painter = painterResource(id = R.drawable.ic_nyt),
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.onSurface,
                            )
                        }
                        Text(
                            text = "$source - $publishedDate",
                            color = MaterialTheme.colorScheme.secondary.copy(alpha = 0.8f),
                            style = MaterialTheme.typography.labelSmall,
                            maxLines = 1,
                            fontSize = 12.sp,
                        )
                    }

                    IconButton(onClick = { isFavorite = !isFavorite }) {
                        Icon(
                            modifier = Modifier.size(16.dp),
                            imageVector = if (isFavorite) Icons.Rounded.Favorite else Icons.Rounded.FavoriteBorder,
                            contentDescription = "like",
                            tint = MaterialTheme.colorScheme.secondary.copy(alpha = 0.8f),
                        )
                    }
                }
            }
        }
    }
}

@Preview(name = "Light Mode", showBackground = true)
@Preview(name = "Dark Mode", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun PreviewArticleSmallCard() {
    MaterialTheme {
        ArticleSmallCard(
            title = "Title: Exploring Compose UI Exploring Compose UI Exploring Compose UI Exploring Compose UI",
            source = "Source: Compose Magazine",
            publishedDate = "2024-02-18",
            onClick = {},
        )
    }
}
