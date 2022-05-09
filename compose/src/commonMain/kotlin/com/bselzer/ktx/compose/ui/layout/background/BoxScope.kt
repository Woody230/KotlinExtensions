package com.bselzer.ktx.compose.ui.layout.background

import androidx.compose.foundation.layout.BoxScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import com.bselzer.ktx.compose.ui.style.ImageStyle

/**
 * Lays out a background image that matches the parent size.
 *
 * @param painter the painter
 * @param style the style describing how to lay out the background
 */
@Composable
fun BoxScope.BackgroundImage(painter: Painter, style: ImageStyle = backgroundStyle()) = com.bselzer.ktx.compose.ui.layout.background.BackgroundImage(
    painter = painter,

    // Need to use matchParentSize() so that the image does not participate in sizing and can just fill the resulting size.
    style = style prioritize Modifier.matchParentSize()
)