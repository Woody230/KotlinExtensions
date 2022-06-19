package com.bselzer.ktx.resource.strings

import com.bselzer.ktx.intl.Locale
import com.bselzer.ktx.intl.Localizer
import com.bselzer.ktx.resource.KtxResources
import dev.icerock.moko.resources.StringResource

/**
 * Converts the [Locale] into a [StringResource].
 */
fun Locale.stringResource(): StringResource = stringResourceOrNull() ?: throw NotImplementedError("StringResource not found for locale $this")

/**
 * Converts the [Locale] into a [StringResource], or null if the conversion does not exist.
 */
fun Locale.stringResourceOrNull() = when (this) {
    Localizer.ENGLISH -> KtxResources.strings.locale_en
    Localizer.FRENCH -> KtxResources.strings.locale_fr
    Localizer.GERMAN -> KtxResources.strings.locale_de
    Localizer.SPANISH -> KtxResources.strings.locale_es
    else -> null
}