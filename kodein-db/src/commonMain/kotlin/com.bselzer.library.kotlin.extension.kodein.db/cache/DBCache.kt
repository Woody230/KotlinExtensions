package com.bselzer.library.kotlin.extension.kodein.db.cache

import com.bselzer.library.kotlin.extension.kodein.db.transaction.DBTransaction
import com.bselzer.library.kotlin.extension.kodein.db.transaction.TransactionStarter

/**
 * The base cache instance.
 */
abstract class DBCache(private val transactionStarter: TransactionStarter) {
    /**
     * Executes the [block] for the current transaction.
     *
     * @param block the transaction function
     */
    protected suspend fun transaction(block: suspend DBTransaction.() -> Unit): Unit = runTransaction(block)

    /**
     * Executes the [block] for the current transition.
     *
     * @param block the transaction function
     * @return the result of the [block]
     */
    protected suspend fun <R> runTransaction(block: suspend DBTransaction.() -> R): R {
        val transaction = transactionStarter.begin()
        return block(transaction)
    }
}