package com.bselzer.ktx.compose.ui.intl

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.text.intl.Locale

/**
 * Provides the current locale.
 */
val LocalLocale: ProvidableCompositionLocal<Locale> = compositionLocalOf { Localizer.locale }

/**
 * Updates the [LocalLocale] with the [Localizer.locale].
 */
@Composable
fun ProvideLocale(content: @Composable () -> Unit) = CompositionLocalProvider(
    // Mutable state is used so recomposition will be handled.
    LocalLocale provides Localizer.locale,
    content = content
)