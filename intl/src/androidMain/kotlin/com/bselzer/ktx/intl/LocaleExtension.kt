package com.bselzer.ktx.intl

actual var DefaultLocale: Locale
    get() = Locale(java.util.Locale.getDefault().toLanguageTag())
    set(value) {
        val javaLocale = java.util.Locale.forLanguageTag(value.languageTag)
        java.util.Locale.setDefault(javaLocale)
    }