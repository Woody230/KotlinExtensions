package com.bselzer.ktx.compose.ui.style

import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.unit.Dp

/**
 * CompositionLocal containing the preferred spacing.
 */
val LocalSpacing: ProvidableCompositionLocal<Dp> = compositionLocalOf { Dp.Unspecified }

/**
 * CompositionLocal containing the preferred thickness.
 */
val LocalThickness: ProvidableCompositionLocal<Dp> = compositionLocalOf { Dp.Unspecified }

/**
 * CompositionLocal containing the preferred indent.
 */
val LocalIndent: ProvidableCompositionLocal<Dp> = compositionLocalOf { Dp.Unspecified }