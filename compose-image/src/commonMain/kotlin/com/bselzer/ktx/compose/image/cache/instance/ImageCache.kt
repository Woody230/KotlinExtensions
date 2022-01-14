package com.bselzer.ktx.compose.image.cache.instance

import com.bselzer.ktx.compose.image.model.Image
import com.bselzer.ktx.kodein.db.cache.DBCache
import com.bselzer.ktx.kodein.db.operation.clear
import com.bselzer.ktx.kodein.db.operation.getById
import com.bselzer.ktx.kodein.db.transaction.TransactionManager
import io.ktor.client.*
import io.ktor.client.request.*
import org.kodein.db.Value

/**
 * The cache for [Image]s.
 */
class ImageCache(transactionManager: TransactionManager, private val client: HttpClient) : DBCache(transactionManager) {
    /**
     * Gets the [Image] with the same [url].
     *
     * If there is no image in the database, then a call is made to request it through the [client].
     *
     * @param url the image location
     * @return the image
     */
    suspend fun getImage(url: String): Image = transaction {
        getById(
            id = Value.of(url),
            requestSingle = {
                val content: ByteArray = try {
                    client.get(url)
                } catch (ex: Exception) {
                    ByteArray(0)
                }

                Image(url = url, content = content)
            },

            // Only write the image if its content was successfully retrieved and not defaulted.
            writeFilter = { image -> image.content.isNotEmpty() }
        )
    }

    /**
     * Clears the [Image] models.
     */
    suspend fun clear() = transaction {
        clear<Image>()
    }
}