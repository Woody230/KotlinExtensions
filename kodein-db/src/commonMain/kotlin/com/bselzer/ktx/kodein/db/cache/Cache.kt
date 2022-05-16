package com.bselzer.ktx.kodein.db.cache

import com.bselzer.ktx.kodein.db.transaction.Transaction

interface Cache {
    /**
     * Clears the database of all relevant models.
     */
    fun Transaction.clear()
}