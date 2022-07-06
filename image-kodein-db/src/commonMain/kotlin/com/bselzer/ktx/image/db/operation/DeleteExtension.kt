package com.bselzer.ktx.image.db.operation

import com.bselzer.ktx.image.model.Image
import com.bselzer.ktx.kodein.db.operation.clear
import com.bselzer.ktx.kodein.db.transaction.Transaction

/**
 * Clears the [Image] model.
 */
fun Transaction.clearImage() {
    clear<Image>()
}