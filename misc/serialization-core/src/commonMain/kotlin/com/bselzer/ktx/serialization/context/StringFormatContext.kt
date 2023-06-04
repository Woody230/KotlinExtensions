package com.bselzer.ktx.serialization.context

import kotlinx.serialization.DeserializationStrategy
import kotlinx.serialization.StringFormat
import kotlinx.serialization.serializer

abstract class StringFormatContext constructor(
    open val instance: StringFormat,
) {
    /**
     * Decodes the string as if it is a literally encoded value.
     */
    inline fun <reified T> String.decode(): T = decode(instance.serializersModule.serializer())

    /**
     * Decodes the string as if it is a literally encoded value.
     * If unable to decode, null is returned.
     */
    inline fun <reified T> String.decodeOrNull(): T? = decodeOrNull(instance.serializersModule.serializer())

    /**
     * Decodes the strings as if they are literally encoded values.
     * If unable to decode a value, then it is removed from the collection.
     */
    inline fun <reified T> Collection<String?>.decodeOrEmpty(): List<T> = decodeOrEmpty(instance.serializersModule.serializer())

    /**
     * Decodes the strings as if they are literally encoded values.
     */
    inline fun <reified T> Collection<String>.decode(): List<T> = decode(instance.serializersModule.serializer())

    /**
     * Decodes the string keys as if they are literally encoded values.
     * If unable to decode a key, then it is removed from the collection.
     */
    inline fun <reified Key, Value> Map<String, Value>.decodeKeysOrEmpty(): Map<Key, Value> = decodeKeysOrEmpty(instance.serializersModule.serializer())

    /**
     * Decodes the string values as if they are literally encoded values.
     * If unable to decode a value, then it is removed from the collection.
     */
    inline fun <Key, reified Value> Map<Key, String>.decodeValuesOrEmpty(): Map<Key, Value> = decodeValuesOrEmpty(instance.serializersModule.serializer())

    /**
     * Decodes the string keys as if they are literally encoded values.
     */
    inline fun <reified Key, Value> Map<String, Value>.decodeKeys(): Map<Key, Value> = decodeKeys(instance.serializersModule.serializer())

    /**
     * Decodes the string values as if they are literally encoded values.
     */
    inline fun <Key, reified Value> Map<Key, String>.decodeValues(): Map<Key, Value> = decodeValues(instance.serializersModule.serializer())

    /**
     * Decodes the string as if it is a literally encoded value.
     */
    abstract fun <T> String.decode(deserializer: DeserializationStrategy<T>): T

    /**
     * Decodes the string as if it is a literally encoded value.
     * If unable to decode, null is returned.
     */
    fun <T> String.decodeOrNull(deserializer: DeserializationStrategy<T>): T? = try {
        decode(deserializer)
    } catch (e: Exception) {
        null
    }

    /**
     * Decodes the strings as if they are literally encoded values.
     * If unable to decode a value, then it is removed from the collection.
     */
    fun <T> Collection<String?>.decodeOrEmpty(deserializer: DeserializationStrategy<T>): List<T> = mapNotNull { s -> s?.decodeOrNull(deserializer) }

    /**
     * Decodes the string keys as if they are literally encoded values.
     * If unable to decode a key, then it is removed from the collection.
     */
    @Suppress("UNCHECKED_CAST")
    fun <Key, Value> Map<String, Value>.decodeKeysOrEmpty(deserializer: DeserializationStrategy<Key>): Map<Key, Value> =
        mapKeys { (key, _) -> key.decodeOrNull(deserializer) }.minus(null) as Map<Key, Value>

    /**
     * Decodes the string values as if they are literally encoded values.
     * If unable to decode a value, then it is removed from the collection.
     */
    @Suppress("UNCHECKED_CAST")
    fun <Key, Value> Map<Key, String>.decodeValuesOrEmpty(deserializer: DeserializationStrategy<Value>): Map<Key, Value> =
        mapValues { (_, value) -> value.decodeOrNull(deserializer) }.filterValues { value -> value != null } as Map<Key, Value>

    /**
     * Decodes the strings as if they are literally encoded values.
     */
    fun <T> Collection<String>.decode(deserializer: DeserializationStrategy<T>): List<T> = map { s -> s.decode(deserializer) }

    /**
     * Decodes the string keys as if they are literally encoded values.
     */
    fun <Key, Value> Map<String, Value>.decodeKeys(deserializer: DeserializationStrategy<Key>): Map<Key, Value> = mapKeys { (key, _) -> key.decode(deserializer) }

    /**
     * Decodes the string values as if they are literally encoded values.
     */
    fun <Key, Value> Map<Key, String>.decodeValues(deserializer: DeserializationStrategy<Value>): Map<Key, Value> = mapValues { (_, value) -> value.decode(deserializer) }
}