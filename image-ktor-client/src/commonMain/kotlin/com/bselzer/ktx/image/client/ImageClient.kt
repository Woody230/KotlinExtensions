package com.bselzer.ktx.image.client

import com.bselzer.ktx.image.model.Image
import com.bselzer.ktx.logging.Logger
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import kotlinx.coroutines.CancellationException

/**
 * The client for [Image]s.
 */
class ImageClient(private val client: HttpClient = HttpClient()) {
    /**
     * Gets the [Image] at the [url], or an [Image] with empty content if it is unable to be retrieved.
     **
     * @param url the image location
     * @return the image
     */
    suspend fun image(url: String): Image {
        val content: ByteArray = client.get(url).body()
        return Image(url, content)
    }

    /**
     * Gets the [Image] at the [url], or null if the [Image] is unable to be retrieved.
     **
     * @param url the image location
     * @return the image
     */
    suspend fun imageOrNull(url: String): Image? = try {
        image(url)
    } catch (ex: Exception) {
        if (ex !is CancellationException) {
            Logger.e(ex, "Failed to retrieve the image at $url.")
        }
        null
    }

    /**
     * Gets the [Image] at the [url], or an [Image] with empty byte content if the [Image] is unable to be retrieved
     **
     * @param url the image location
     * @return the image
     */
    suspend fun imageOrDefault(url: String): Image = imageOrNull(url) ?: Image(url, byteArrayOf())
}