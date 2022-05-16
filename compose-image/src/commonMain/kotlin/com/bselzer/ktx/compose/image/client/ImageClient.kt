package com.bselzer.ktx.compose.image.client

import com.bselzer.ktx.compose.image.model.Image
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*

/**
 * The client for [Image]s.
 */
class ImageClient(private val client: HttpClient = HttpClient()) {
    /**
     * Gets the [Image] with the same [url].
     **
     * @param url the image location
     * @return the image
     */
    suspend fun getImage(url: String): Image {
        val content: ByteArray = try {
            client.get(url).body()
        } catch (ex: Exception) {
            ByteArray(0)
        }

        return Image(url = url, content = content)
    }
}