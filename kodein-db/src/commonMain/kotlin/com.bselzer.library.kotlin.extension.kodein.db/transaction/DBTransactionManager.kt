package com.bselzer.library.kotlin.extension.kodein.db.transaction

import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

/**
 * An abstraction over a transaction.
 *
 * @param transactionStarter the transaction starter
 * @param transactionFinisher the transaction finisher
 * @param Instance the subclass type
 */
abstract class DBTransactionManager<Instance>(
    private val transactionStarter: TransactionStarter,
    private val transactionFinisher: TransactionFinisher,
    private val lock: Mutex = Mutex()
) {
    /**
     * The subclass instance.
     */
    protected abstract val instance: Instance

    /**
     * Starts the transaction.
     */
    suspend fun beginTransaction() = transactionStarter.begin()

    /**
     * Ends the transaction.
     */
    suspend fun endTransaction() = transactionFinisher.end()

    /**
     * Executes the [block] within a transaction and returns its result.
     *
     * @param R the type of result
     * @return the result of the [block]
     */
    suspend fun <R> transaction(block: suspend Instance.() -> R): R {
        beginTransaction()
        val result = block(instance)
        endTransaction()
        return result
    }

    /**
     * Executes the [block] within a transaction with the lock and returns its result.
     *
     * @param R the type of result
     * @return the result of the [block]
     */
    suspend fun <R> lockedTransaction(block: suspend Instance.() -> R): R = lock.withLock { transaction(block) }
}