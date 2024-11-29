package com.bselzer.ktx.store.sqlite.encoding

/**
 * Encodes and decodes the [Model] as a [String]
 */
interface ModelToStringEncoder<Model>: DataStoreEncoder<Model, String>