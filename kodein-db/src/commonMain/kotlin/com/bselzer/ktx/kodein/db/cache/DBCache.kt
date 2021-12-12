package com.bselzer.ktx.kodein.db.cache

import com.bselzer.ktx.kodein.db.transaction.DBTransaction
import com.bselzer.ktx.kodein.db.transaction.TransactionManager

/**
 * The base cache instance.
 */
abstract class DBCache(private val transactionManager: TransactionManager) {
    /**
     * Executes the [block] for the current transition.
     *
     * @param block the transaction function
     * @return the result of the [block]
     */
    protected suspend fun <R> transaction(block: suspend TransactionManager.(DBTransaction) -> R): R = transactionManager.transaction {
        block(transactionManager, this)
    }

    /**
     * Executes the [block] within a transaction with the lock and returns its result.
     *
     * @param R the type of result
     * @return the result of the [block]
     */
    protected suspend fun <R> lockedTransaction(block: suspend TransactionManager.(DBTransaction) -> R): R = transactionManager.lockedTransaction {
        block(transactionManager, this)
    }
}