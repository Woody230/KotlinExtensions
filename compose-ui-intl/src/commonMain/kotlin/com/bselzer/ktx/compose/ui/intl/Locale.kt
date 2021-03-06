package com.bselzer.ktx.compose.ui.intl

import androidx.compose.runtime.*
import com.bselzer.ktx.intl.Locale
import com.bselzer.ktx.intl.Localizer

fun com.bselzer.ktx.intl.Locale.toComposeLocale() = androidx.compose.ui.text.intl.Locale(languageTag)

/**
 * Provides the current locale.
 */
val LocalLocale: ProvidableCompositionLocal<androidx.compose.ui.text.intl.Locale> = compositionLocalOf { Localizer.locale.toComposeLocale() }

/**
 * Updates the [LocalLocale] with the [Localizer.locale].
 */
@Composable
fun ProvideLocale(localizer: Localizer = Localizer, content: @Composable () -> Unit) {
    var locale by remember { mutableStateOf(localizer.locale.toComposeLocale()) }
    CompositionLocalProvider(
        LocalLocale provides locale,
        content = content
    )

    val listener: (Locale) -> Unit = remember { { locale = it.toComposeLocale() } }
    DisposableEffect(listener) {
        locale = localizer.locale.toComposeLocale()
        Localizer.addListener(listener)
        onDispose { localizer.removeListener(listener) }
    }
}