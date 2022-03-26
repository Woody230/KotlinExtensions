package com.bselzer.ktx.value.identifier

interface Identifier<T> : Comparable<Identifier<T>> {
    val value: T
    val isDefault: Boolean
}