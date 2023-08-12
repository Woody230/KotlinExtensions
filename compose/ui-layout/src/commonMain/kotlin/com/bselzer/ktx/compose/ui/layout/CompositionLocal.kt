package com.bselzer.ktx.compose.ui.layout

import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.compositionLocalOf

/**
 * Provides the direction of orientation (vertical or horizontal) when layout out a composable.
 */
val LocalLayoutOrientation: ProvidableCompositionLocal<LayoutOrientation> = compositionLocalOf { LayoutOrientation.DEFAULT }