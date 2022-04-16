package com.bselzer.ktx.compose.ui.background

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import com.bselzer.ktx.compose.ui.style.Image
import com.bselzer.ktx.compose.ui.style.ImageStyle
import com.bselzer.ktx.compose.ui.style.LocalImageStyle


/**
 * Creates a localized [ImageStyle] for the [BackgroundImage].
 */
@Composable
fun backgroundStyle(): ImageStyle = ImageStyle(
    modifier = Modifier.fillMaxSize(),
    alignment = Alignment.Center,
    contentScale = ContentScale.Crop
).with(LocalImageStyle.current)

/**
 * Lays out a background image.
 *
 * @param painter the painter
 * @param style the style describing how to lay out the background
 */
@Composable
fun BackgroundImage(
    painter: Painter,
    style: ImageStyle = backgroundStyle()
) = Image(
    painter = painter,
    contentDescription = null,
    style = style
)