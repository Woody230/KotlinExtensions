package com.bselzer.ktx.store.sqlite.async

interface KeyEncoder<Key> {
    fun decode(value: String): Key
    fun encode(key: Key): String
}