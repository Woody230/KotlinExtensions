package com.bselzer.ktx.compose.resource.ui.intl

import androidx.compose.ui.text.intl.Locale
import com.bselzer.ktx.compose.ui.intl.Localizer
import com.bselzer.ktx.resource.Resources
import dev.icerock.moko.resources.StringResource

/**
 * Converts the [Locale] into a [StringResource].
 */
fun Locale.stringResource(): StringResource = stringResourceOrNull() ?: throw NotImplementedError("StringResource not found for locale $this")

/**
 * Converts the [Locale] into a [StringResource], or null if the conversion does not exist.
 */
fun Locale.stringResourceOrNull() = when (this) {
    Localizer.ENGLISH -> Resources.strings.locale_en
    Localizer.FRENCH -> Resources.strings.locale_fr
    Localizer.GERMAN -> Resources.strings.locale_de
    Localizer.SPANISH -> Resources.strings.locale_es
    else -> null
}