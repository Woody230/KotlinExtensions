package com.bselzer.ktx.compose.ui.background

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.painterResource

/**
 * Lays out a cropped background image across the entirety of the parent.
 */
@Composable
fun BoxScope.BackgroundImage(@DrawableRes drawableId: Int, alignment: Alignment = Alignment.Center) =
    BackgroundImage(
        painter = painterResource(id = drawableId),
        alignment = alignment
    )