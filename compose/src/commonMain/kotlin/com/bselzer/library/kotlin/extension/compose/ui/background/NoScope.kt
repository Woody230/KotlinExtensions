package com.bselzer.library.kotlin.extension.compose.ui.background

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale

/**
 * Displays a cropped background image.
 *
 * @param painter the painter
 * @param modifier the modifier for handling size
 * @param alignment where to focus the cropping
 */
@Composable
internal fun BackgroundImage(painter: Painter, modifier: Modifier, alignment: Alignment = Alignment.Center) = Image(
    painter = painter,
    contentDescription = null,
    modifier = modifier,
    contentScale = ContentScale.Crop,
    alignment = alignment
)

/**
 * Displays a cropped background image across the entirety of the parent.
 *
 * @param painter the painter
 * @param alignment where to focus the cropping
 */
@Composable
fun BackgroundImage(painter: Painter, alignment: Alignment = Alignment.Center) =
    BackgroundImage(painter = painter, modifier = Modifier.fillMaxSize(), alignment = alignment)