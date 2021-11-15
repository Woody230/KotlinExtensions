package com.bselzer.library.kotlin.extension.kodein.db.transaction

/**
 * Represents an object that can start transactions.
 */
interface TransactionStarter {
    /**
     * Starts a transaction.
     *
     * @return the transaction
     */
    suspend fun begin(): DBTransaction
}