package com.app.presentation.common.composables

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.app.presentation.R

/**
 * Displays an image from a URL with a shimmer effect while loading.
 * The shimmer effect is shown until the image is successfully loaded or an error occurs.
 *
 * @param modifier
 * @param url The URL of the image to be displayed.
 */
@Composable
fun ImageItem(
    modifier: Modifier = Modifier,
    contentDescription: String? = null,
    url: String,
) {
    var isLoading by remember { mutableStateOf(true) }

    Box(modifier = modifier) {
        if (isLoading) {
            ShimmerEffect(modifier = Modifier.matchParentSize())
        }

        AsyncImage(
            model =
                ImageRequest.Builder(LocalContext.current)
                    .data(url)
                    .crossfade(true)
                    .placeholder(R.drawable.ic_newspaper)
                    .error(R.drawable.ic_broken_image)
                    .listener(
                        onStart = {
                            isLoading = true
                        },
                        onSuccess = { _, _ ->
                            isLoading = false
                        },
                        onError = { _, _ ->
                            isLoading = false
                        },
                    )
                    .build(),
            contentDescription = contentDescription,
            contentScale = ContentScale.Crop,
            modifier = modifier.then(Modifier.visible(!isLoading)),
        )
    }
}

/**
 * Extension function on Modifier to conditionally apply zero size if not visible.
 *
 * @param visible Boolean flag indicating whether the modifier should make the component visible.
 * @return Modifier either with original dimensions or zero size based on visibility.
 */
@Composable
fun Modifier.visible(visible: Boolean): Modifier = if (visible) this else this.then(Modifier.size(0.dp))
