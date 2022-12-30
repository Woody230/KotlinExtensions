package com.bselzer.ktx.openapi.model.reference

import com.bselzer.ktx.openapi.serialization.OpenApiReferenceOfSerializer

/**
 * A wrapper of a value of type [T] or a reference to the value.
 */
@kotlinx.serialization.Serializable(OpenApiReferenceOfSerializer::class)
class OpenApiReferenceOf<T> {
    @PublishedApi
    internal val value: Any?

    /**
     * Initializes a new instance of the [OpenApiReferenceOf] class.
     *
     * @param value an actual value
     */
    constructor(value: T) {
        this.value = value
    }

    /**
     * Initializes a new instance of the [OpenApiReferenceOf] class.
     *
     * @param reference a reference to a value of type [T]
     */
    constructor(reference: OpenApiReference) {
        this.value = reference
    }

    /**
     * Resolve the cases where the value is either of type [T], or is a reference.
     */
    inline fun <reified T> resolve(
        onValue: (T) -> Unit,
        onReference: (OpenApiReference) -> Unit
    ) = when (value) {
        is T -> onValue(value)
        is OpenApiReference -> onReference(value)
        else -> {}
    }
}