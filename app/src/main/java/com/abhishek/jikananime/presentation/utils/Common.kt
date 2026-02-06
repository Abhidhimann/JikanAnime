package com.abhishek.jikananime.presentation.utils

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import coil.compose.AsyncImage
import com.abhishek.jikananime.R

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