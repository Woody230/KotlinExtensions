package com.bselzer.ktx.compose.image.cache.operation

import com.bselzer.ktx.compose.image.model.Image
import com.bselzer.ktx.kodein.db.operation.clear
import com.bselzer.ktx.kodein.db.transaction.Transaction

/**
 * Clears the [Image] model.
 */
fun Transaction.clearImage() {
    clear<Image>()
}