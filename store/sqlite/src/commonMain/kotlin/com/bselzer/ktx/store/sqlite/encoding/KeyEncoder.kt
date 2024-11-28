package com.bselzer.ktx.store.sqlite.encoding

interface KeyEncoder<Key> {
    fun decode(value: String): Key
    fun encode(key: Key): String
}