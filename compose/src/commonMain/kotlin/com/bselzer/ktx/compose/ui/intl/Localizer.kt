package com.bselzer.ktx.compose.ui.intl

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.text.intl.Locale

interface Localizer {
    /**
     * The default locale.
     */
    var locale: Locale

    companion object : Localizer by SystemLocalizer() {
        val ENGLISH = Locale("en")
        val FRENCH = Locale("fr")
        val GERMAN = Locale("de")
        val SPANISH = Locale("es")
        val DEFAULT = Locale("")
    }
}

internal abstract class BaseSystemLocalizer : Localizer {
    // Use a mutable state so that compositions can be made aware of changes.
    private val state: MutableState<Locale> = mutableStateOf(Locale.current)

    override var locale: Locale
        get() = state.value
        set(value) {
            setSystemLocale(value)
            state.value = value
        }

    /**
     * Sets the platform specific locale from the composable [Locale].
     */
    protected abstract fun setSystemLocale(locale: Locale)
}

internal expect class SystemLocalizer() : BaseSystemLocalizer