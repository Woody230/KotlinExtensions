package com.bselzer.ktx.image.model

import kotlinx.serialization.Serializable

@Serializable
data class Image(
    val url: String,
    val content: ByteArray
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || this::class != other::class) return false

        other as Image

        if (url != other.url) return false

        return true
    }

    override fun hashCode(): Int {
        return url.hashCode()
    }
}