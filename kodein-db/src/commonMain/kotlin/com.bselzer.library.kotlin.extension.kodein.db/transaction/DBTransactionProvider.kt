package com.bselzer.library.kotlin.extension.kodein.db.transaction

import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import org.kodein.db.DB
import org.kodein.db.Options

/**
 * Represents a transaction manager.
 */
class DBTransactionProvider(private val database: DB, private vararg val writeOptions: Options.BatchWrite) : TransactionStarter, TransactionFinisher {
    /**
     * The lock instance.
     */
    private val lock = Mutex()

    /**
     * The backing transaction to provide.
     */
    private var transaction: DBTransaction? = null

    /**
     * Creates a new transaction if one does not exist, otherwise the existing transaction is retrieved.
     *
     * @return the transaction
     */
    override suspend fun begin(): DBTransaction = lock.withLock {
        var transaction = transaction
        return@withLock if (transaction == null) {
            transaction = DBTransaction(database)
            this.transaction = transaction
            transaction
        } else {
            transaction
        }
    }

    /**
     * Closes the transaction if one exists.
     */
    override suspend fun end(): Unit = lock.withLock {
        transaction?.let {
            it.commit(*writeOptions)
            it.close()
            transaction = null
        }
    }
}