package com.bselzer.ktx.store.sqlite.encoding

/**
 * Encodes the [DecodedValue] as an [EncodedValue] and decodes the [EncodedValue] as a [DecodedValue].
 */
sealed interface DataStoreEncoder<DecodedValue, EncodedValue> {
    fun decode(encodedValue: EncodedValue): DecodedValue
    fun encode(decodedValue: DecodedValue): EncodedValue
}