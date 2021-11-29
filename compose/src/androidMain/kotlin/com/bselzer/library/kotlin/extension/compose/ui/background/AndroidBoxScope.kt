package com.bselzer.library.kotlin.extension.compose.ui.background

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.painterResource

/**
 * Displays a cropped background image across the entirety of the parent.
 */
@Composable
fun BoxScope.BackgroundImage(@DrawableRes drawableId: Int, alignment: Alignment = Alignment.Center) =
    BackgroundImage(
        painter = painterResource(id = drawableId),
        alignment = alignment
    )