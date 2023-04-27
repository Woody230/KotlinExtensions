package com.bselzer.ktx.base64

import kotlin.io.encoding.Base64
import kotlin.io.encoding.ExperimentalEncodingApi

/**
 * @return the byte array with base64 encoding
 */
@OptIn(ExperimentalEncodingApi::class)
fun ByteArray.encodeBase64(): ByteArray = Base64.encodeToByteArray(this, 0, size)

/**
 * @return the byte array without base encoding
 */
@OptIn(ExperimentalEncodingApi::class)
fun ByteArray.decodeBase64(): ByteArray = Base64.decode(this, 0, size)

/**
 * @return the string without base64 encoding
 */
fun String.decodeBase64(): String = this.encodeToByteArray().decodeBase64().decodeToString()

/**
 * @return the string with base64 encoding
 */
fun String.encodeBase64(): String = this.encodeToByteArray().encodeBase64().decodeToString()

/**
 * @return a byte array from a base64 encoded string
 */
fun String.decodeBase64ToByteArray(): ByteArray = this.encodeToByteArray().decodeBase64()

/**
 * @return a string from a base64 encoded byte array
 */
fun ByteArray.decodeBase64ToString(): String = this.decodeBase64().decodeToString()

/**
 * @return a string with base64 encoding
 */
fun ByteArray.encodeBase64ToString(): String = this.encodeBase64().decodeToString()
