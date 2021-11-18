package com.bselzer.library.kotlin.extension.kodein.db.transaction

import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import org.kodein.db.DB

/**
 * An abstraction over a transaction for managing logic within commit blocks.
 *
 * @param database the database
 */
abstract class DBTransactionManager(
    private val database: DB,
    private val lock: Mutex = Mutex()
) : TransactionManager {
    /**
     * Executes the [block] within a transaction and returns its result.
     *
     * @param R the type of result
     * @param block the function to call within the transaction
     * @return the result of the [block]
     */
    override suspend fun <R> transaction(block: suspend DBTransaction.() -> R): R = DBTransaction(database).use { block(this) }

    /**
     * Executes the [block] within a transaction with the lock and returns its result.
     *
     * @param R the type of result
     * @param block the function to within the transaction
     * @return the result of the [block]
     */
    override suspend fun <R> lockedTransaction(block: suspend DBTransaction.() -> R): R = lock.withLock { transaction(block) }
}