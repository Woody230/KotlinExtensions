package com.bselzer.ktx.function.collection

/**
 * Puts the entries from this map into the [other] map.
 */
fun <Key, Value> Map<Key, Value>.putInto(other: MutableMap<Key, Value>) = other.putAll(this)