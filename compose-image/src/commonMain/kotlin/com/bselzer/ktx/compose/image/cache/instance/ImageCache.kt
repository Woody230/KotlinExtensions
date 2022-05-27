package com.bselzer.ktx.compose.image.cache.instance

import com.bselzer.ktx.compose.image.model.Image
import com.bselzer.ktx.kodein.db.cache.Cache
import com.bselzer.ktx.kodein.db.operation.clear
import com.bselzer.ktx.kodein.db.transaction.Transaction
import org.kodein.db.*

interface ImageCache : Cache {
    /**
     * Gets the [Image] associated with the [url], or null if it does not exist.
     */
    fun DBRead.getImageOrNull(url: String, vararg options: Options.Get): Image? = getById(Value.of(url), *options)

    /**
     * Gets the [Image] associated with the [url], or an [Image] with no content if it does not exist.
     */
    fun DBRead.getImage(url: String, vararg options: Options.Get): Image = getImageOrNull(url, *options) ?: Image(url, byteArrayOf())

    /**
     * Puts the [image] into the database if the content exists.
     */
    fun DBWrite.putImage(image: Image, vararg options: Options.Puts) {
        // Only write the image if its content was successfully retrieved and not defaulted.
        if (image.content.isNotEmpty()) {
            put(image, *options)
        }
    }

    /**
     * Clears the [Image] models.
     */
    override fun Transaction.clear() {
        clear<Image>()
    }

    companion object : ImageCache
}