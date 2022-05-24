package com.bselzer.ktx.compose.ui.intl

import androidx.compose.ui.text.intl.Locale

internal actual class SystemLocalizer actual constructor() : BaseSystemLocalizer() {
    override fun setSystemLocale(locale: Locale) {
        val javaLocale = java.util.Locale(locale.language, locale.script, locale.region)
        java.util.Locale.setDefault(javaLocale)
    }
}