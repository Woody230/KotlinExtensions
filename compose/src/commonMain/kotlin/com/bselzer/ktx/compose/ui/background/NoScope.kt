package com.bselzer.ktx.compose.ui.background

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import com.bselzer.ktx.compose.ui.style.Image
import com.bselzer.ktx.compose.ui.style.ImageStyle

/**
 * CompositionLocal containing the preferred BackgroundStyle that will be used by BackgroundImage components by default.
 */
val LocalBackgroundStyle: ProvidableCompositionLocal<ImageStyle> = compositionLocalOf {
    ImageStyle.Default.copy(
        // Fill the entirety of the parent.
        modifier = Modifier.fillMaxSize(),

        alignment = Alignment.Center,
        contentScale = ContentScale.Crop
    )
}

/**
 * Lays out a background image.
 *
 * @param painter the painter
 * @param style the style describing how to lay out the background
 */
@Composable
fun BackgroundImage(
    painter: Painter,
    style: ImageStyle = LocalBackgroundStyle.current
) = Image(
    painter = painter,
    contentDescription = null,
    style = style
)