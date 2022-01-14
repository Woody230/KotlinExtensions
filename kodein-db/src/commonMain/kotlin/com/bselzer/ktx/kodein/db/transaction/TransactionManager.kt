package com.bselzer.ktx.kodein.db.transaction

interface TransactionManager {
    /**
     * Executes the [block] within a transaction and returns its result.
     *
     * @param R the type of result
     * @return the result of the [block]
     */
    suspend fun <R> transaction(block: suspend DBTransaction.() -> R): R

    /**
     * Executes the [block] within a transaction with the lock and returns its result.
     *
     * @param R the type of result
     * @return the result of the [block]
     */
    suspend fun <R> lockedTransaction(block: suspend DBTransaction.() -> R): R
}