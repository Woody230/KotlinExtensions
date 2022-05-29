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
     * Gets the [Image] at the [url], or an [Image] with empty content if it is unable to be retrieved.
     **
     * @param url the image location
     * @return the image
     */
    suspend fun getImage(url: String): Image = client.get(url).body()

    /**
     * Gets the [Image] at the [url], or null if the [Image] is unable to be retrieved.
     **
     * @param url the image location
     * @return the image
     */
    suspend fun getImageOrNull(url: String): Image? = try {
        getImage(url)
    } catch (ex: Exception) {
        null
    }
}