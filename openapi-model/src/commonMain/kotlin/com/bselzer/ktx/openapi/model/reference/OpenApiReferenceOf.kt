package com.bselzer.ktx.openapi.model.reference

import com.bselzer.ktx.openapi.model.reference.path.OpenApiReferencePath

/**
 * A wrapper of a value of type [TValue] or a reference to the value.
 */
sealed class OpenApiReferenceOf<TValue, TReferencePath> where TReferencePath : OpenApiReferencePath {
    @PublishedApi
    internal val value: TValue?

    @PublishedApi
    internal val reference: OpenApiReference<TReferencePath>?

    /**
     * Initializes a new instance of the [OpenApiReferenceOf] class.
     *
     * @param value an actual value
     */
    constructor(value: TValue) {
        this.value = value
        this.reference = null
    }

    /**
     * Initializes a new instance of the [OpenApiReferenceOf] class.
     *
     * @param reference a reference to a value of type [TValue]
     */
    constructor(reference: OpenApiReference<TReferencePath>) {
        this.value = null
        this.reference = reference
    }

    /**
     * Resolve the cases where the value is either of type [TValue], or is a reference.
     */
    fun resolve(
        onValue: (TValue) -> Unit,
        onReference: (OpenApiReference<TReferencePath>) -> Unit
    ) {
        when {
            value != null -> onValue(value)
            reference != null -> onReference(reference)
        }
    }
}