package com.bselzer.ktx.value.identifier

interface StringIdentifier : Identifier<String> {
    override val isDefault: Boolean
        get() = value.isBlank()

    operator fun compareTo(other: String): Int = value.compareTo(other)
    override operator fun compareTo(other: Identifier<String>): Int = value.compareTo(other.value)
}