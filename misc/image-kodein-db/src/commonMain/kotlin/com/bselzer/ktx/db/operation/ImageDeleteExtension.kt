package com.bselzer.ktx.db.operation

import com.bselzer.ktx.db.transaction.Transaction
import com.bselzer.ktx.image.model.Image

/**
 * Clears the [Image] model.
 */
fun Transaction.clearImage() {
    clear<Image>()
}