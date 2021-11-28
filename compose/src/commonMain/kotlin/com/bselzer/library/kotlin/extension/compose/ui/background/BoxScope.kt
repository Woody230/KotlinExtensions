package com.bselzer.library.kotlin.extension.compose.ui.background

import androidx.compose.foundation.layout.BoxScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter

/**
 * Displays a cropped background image across the entirety of the parent.
 *
 * @param painter the painter
 * @param alignment where to focus the cropping
 */
// Need to use matchParentSize() so that the image does not participate in sizing and can just fill the resulting size.
@Composable
fun BoxScope.BackgroundImage(painter: Painter, alignment: Alignment = Alignment.Center) =
    BackgroundImage(modifier = Modifier.matchParentSize(), painter = painter, alignment = alignment)