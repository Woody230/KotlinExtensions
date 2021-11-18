package com.bselzer.library.kotlin.extension.kodein.db.transaction

import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

/**
 * An abstraction over a transaction.
 *
 * @param transactionProvider the transaction provider
 */
abstract class DBTransactionManager(
    private val transactionProvider: DBTransactionProvider,
    private val lock: Mutex = Mutex()
) : TransactionManager {
    /**
     * Starts the transaction.
     */
    override suspend fun begin(): DBTransaction = transactionProvider.begin()

    /**
     * Finishes the transaction.
     */
    override suspend fun end(): Unit = transactionProvider.end()

    /**
     * Executes the [block] within a transaction and returns its result.
     *
     * @param R the type of result
     * @return the result of the [block]
     */
    override suspend fun <R> transaction(block: suspend DBTransaction.() -> R): R {
        val transaction = begin()
        val result = block(transaction)
        end()
        return result
    }

    /**
     * Executes the [block] within a transaction with the lock and returns its result.
     *
     * @param R the type of result
     * @return the result of the [block]
     */
    override suspend fun <R> lockedTransaction(block: suspend DBTransaction.() -> R): R = lock.withLock { transaction(block) }
}