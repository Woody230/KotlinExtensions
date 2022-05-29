package com.bselzer.ktx.compose.image.client

import com.bselzer.ktx.compose.image.model.Image
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
    suspend fun getImage(url: String): Image {
        val content: ByteArray = client.get(url).body()
        return Image(url, content)
    }

    /**
     * Gets the [Image] at the [url], or null if the [Image] is unable to be retrieved.
     **
     * @param url the image location
     * @return the image
     */
    suspend fun getImageOrNull(url: String): Image? = try {
        getImage(url)
    } catch (ex: Exception) {
        if (ex !is CancellationException) {
            Logger.e(ex, "Failed to retrieve the image at $url.")
        }
        null
    }
}