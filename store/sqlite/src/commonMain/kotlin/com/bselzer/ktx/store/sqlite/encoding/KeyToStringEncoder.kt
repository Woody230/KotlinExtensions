package com.bselzer.ktx.store.sqlite.encoding

/**
 * Encodes and decodes the [Key] as a [String].
 */
interface KeyToStringEncoder<Key>: DataStoreEncoder<Key, String>