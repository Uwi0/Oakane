package com.kakapo.oakane.presentation.designSystem.component.image

import android.net.Uri
import androidx.annotation.DrawableRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.PhotoCamera
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest


@Composable
fun CustomDynamicAsyncImage(
    imageUrl: Any?,
    contentDescription: String? = null,
    modifier: Modifier = Modifier,
    placeholder: Painter? = null,
    contentScale: ContentScale = ContentScale.Fit
) {
    var isLoading by remember { mutableStateOf(true) }

    Box(modifier = modifier) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(imageUrl)
                .crossfade(true)
                .build(),
            placeholder = placeholder,
            contentDescription = contentDescription,
            modifier = Modifier.matchParentSize(),
            error = placeholder,
            contentScale = contentScale,
            onSuccess = { isLoading = false },
            onError = { isLoading = false }
        )

        if (isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }
}

@Composable
fun CustomImagePicker(
    modifier: Modifier = Modifier,
    @DrawableRes defaultImg: Int,
    selectedImageUri: Uri?,
    onSelectedImage: () -> Unit
) {
    Box(modifier = modifier.clickable { onSelectedImage.invoke() }) {
        val defaultImage = painterResource(id = defaultImg)
        AsyncImage(
            modifier = Modifier.fillMaxSize().clip(CircleShape),
            model = selectedImageUri,
            contentScale = ContentScale.Crop,
            contentDescription = null,
            placeholder = defaultImage,
            error = defaultImage
        )

        IconImagePicker(modifier = Modifier.align(Alignment.BottomEnd))
    }
}

@Composable
private fun IconImagePicker(modifier: Modifier = Modifier) {
    Surface(
        modifier = modifier,
        shape = CircleShape,
        color = MaterialTheme.colorScheme.primary
    ) {
        Column(modifier = Modifier.padding(8.dp)) {
            Icon(
                imageVector = Icons.Outlined.PhotoCamera,
                contentDescription = "",
                tint = MaterialTheme.colorScheme.onPrimary
            )
        }
    }
}
