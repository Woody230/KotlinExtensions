package com.bselzer.ktx.intl

class Locale(val languageTag: String) {
    override fun toString(): String = languageTag

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || this::class != other::class) return false

        other as Locale

        if (languageTag != other.languageTag) return false

        return true
    }

    override fun hashCode(): Int {
        return languageTag.hashCode()
    }
}