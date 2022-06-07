package com.bselzer.ktx.intl

interface Localizer {
    var locale: Locale

    /**
     * Adds a listener to be notified of locale changes.
     */
    fun addListener(listener: (Locale) -> Unit)

    /**
     * Removes the listener.
     */
    fun removeListener(listener: (Locale) -> Unit)

    /**
     * Removes all of the listeners.
     */
    fun removeListeners()

    companion object : Localizer by SystemLocalizer() {
        val ENGLISH = Locale("en")
        val FRENCH = Locale("fr")
        val GERMAN = Locale("de")
        val SPANISH = Locale("es")
        val DEFAULT = Locale("")
    }
}

internal class SystemLocalizer : Localizer {
    private val listeners: MutableList<(Locale) -> Unit> = mutableListOf()

    private var _locale: Locale = DefaultLocale
    override var locale: Locale
        get() = _locale
        set(value) {
            _locale = value
            listeners.forEach { listener -> listener(value) }
        }

    override fun addListener(listener: (Locale) -> Unit) {
        listeners.add(listener)
    }

    override fun removeListener(listener: (Locale) -> Unit) {
        listeners.remove(listener)
    }

    override fun removeListeners() {
        listeners.clear()
    }
}