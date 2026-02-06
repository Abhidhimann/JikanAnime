package com.abhishek.jikananime.presentation.screens.animedetails

import android.app.Activity
import android.view.ViewGroup
import android.webkit.WebView
import androidx.annotation.DrawableRes
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.view.WindowInsetsControllerCompat
import androidx.core.view.children
import androidx.lifecycle.LifecycleOwner
import coil.compose.AsyncImage
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView
import com.abhishek.jikananime.R

@Composable
fun AnimeYouTubePlayer(
    modifier: Modifier = Modifier,
    videoId: String,
    lifecycleOwner: LifecycleOwner = androidx.lifecycle.compose.LocalLifecycleOwner.current,
) {
    val view = LocalView.current
    val context = view.context
    val activity = context as Activity
    val window = activity.window

    DisposableEffect(Unit) {
        val controller = WindowInsetsControllerCompat(
            window,
            view
        )
        controller.isAppearanceLightStatusBars = false

        onDispose {
            controller.isAppearanceLightStatusBars = true
        }
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.Black)
            .padding(top = WindowInsets.systemBars.asPaddingValues().calculateTopPadding())
            .aspectRatio(16f / 9f)
    ) {
        AndroidView(
            modifier = modifier,
            factory = { context ->
                YouTubePlayerView(context).apply {
                    lifecycleOwner.lifecycle.addObserver(this)
                    disableWebViewsBackground()

                    addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
                        override fun onReady(youTubePlayer: YouTubePlayer) {
                            youTubePlayer.cueVideo(videoId, 0f)
                        }
                    })
                }
            },
            onRelease = { view ->
                view.release()
            }
        )
    }
}

// to handle long descriptions
@Composable
fun ExpandableText(
    text: String,
    modifier: Modifier = Modifier,
    minimizedMaxLines: Int = 3
) {
    var isExpanded by remember { mutableStateOf(false) }
    var textLayoutResult by remember { mutableStateOf<Boolean?>(null) }

    Column(modifier = modifier.animateContentSize()) {
        Text(
            text = text,
            maxLines = if (isExpanded) Int.MAX_VALUE else minimizedMaxLines,
            overflow = TextOverflow.Ellipsis,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            onTextLayout = { textLayoutResult = it.hasVisualOverflow }
        )

        if (textLayoutResult == true || isExpanded) {
            Text(
                text = if (isExpanded) "Show Less" else "Read More",
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.labelLarge,
                modifier = Modifier
                    .clickable { isExpanded = !isExpanded }
                    .padding(top = 4.dp)
            )
        }
    }
}

@Composable
fun AnimePoster(
    imageUrl: String,
    shape: Shape = CardDefaults.shape,
    modifier: Modifier = Modifier,
    @DrawableRes defaultImageId: Int = R.drawable.movie_not_found
) {
    Card(
        modifier = modifier
            .fillMaxWidth(),
        shape = shape,
    ) {
        AsyncImage(
            model = imageUrl.ifEmpty { defaultImageId },
            contentDescription = "movie_image",
            modifier = modifier,
            contentScale = ContentScale.Crop,
            placeholder = painterResource(id = defaultImageId),
            error = painterResource(id = defaultImageId)
        )
    }
}

fun ViewGroup.disableWebViewsBackground() {
    children.forEach { child ->
        when (child) {
            is WebView -> child.setBackgroundColor(android.graphics.Color.TRANSPARENT)
            is ViewGroup -> child.disableWebViewsBackground()
        }
    }
}