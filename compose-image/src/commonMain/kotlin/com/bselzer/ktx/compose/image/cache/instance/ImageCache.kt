package com.bselzer.ktx.compose.image.cache.instance

import com.bselzer.ktx.compose.image.client.ImageClient
import com.bselzer.ktx.compose.image.model.Image
import com.bselzer.ktx.kodein.db.cache.Cache
import com.bselzer.ktx.kodein.db.operation.clear
import com.bselzer.ktx.kodein.db.operation.getById
import com.bselzer.ktx.kodein.db.transaction.Transaction
import org.kodein.db.Value

class ImageCache(private val client: ImageClient = ImageClient()) : Cache {
    /**
     * Gets the [Image] with the same [url].
     *
     * If there is no image in the database, then a call is made to request it through the [client].
     *
     * @param url the image location
     * @return the image
     */
    suspend fun Transaction.getImage(url: String): Image = getById(
        id = Value.of(url),
        requestSingle = { client.getImage(url) },

        // Only write the image if its content was successfully retrieved and not defaulted.
        writeFilter = { image -> image.content.isNotEmpty() }
    )

    /**
     * Clears the [Image] models.
     */
    override fun Transaction.clear() {
        clear<Image>()
    }
}