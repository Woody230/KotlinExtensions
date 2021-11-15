package com.bselzer.library.kotlin.extension.kodein.db.transaction

/**
 * Represents an object that can finish transactions.
 */
interface TransactionFinisher {
    /**
     * Finishes a transaction.
     */
    suspend fun end()
}