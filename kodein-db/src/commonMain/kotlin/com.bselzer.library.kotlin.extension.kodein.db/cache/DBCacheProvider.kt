package com.bselzer.library.kotlin.extension.kodein.db.cache

import com.bselzer.library.kotlin.extension.kodein.db.transaction.TransactionFinisher
import com.bselzer.library.kotlin.extension.kodein.db.transaction.TransactionStarter
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlin.reflect.KClass

/**
 * The caching abstraction for multiple instances.
 *
 * @param transactionStarter the transaction starter
 * @param transactionFinisher the transaction finisher
 * @param BaseCache the type of cache
 */
open class DBCacheProvider<BaseCache : DBCache>(val transactionStarter: TransactionStarter, val transactionFinisher: TransactionFinisher) {
    /**
     * The underlying caches.
     */
    @PublishedApi
    internal val caches: MutableMap<KClass<out BaseCache>, BaseCache> = mutableMapOf()

    /**
     * The lock instance.
     */
    private val lock = Mutex()

    /**
     * Executes the [block] under a lock.
     */
    suspend fun withLock(block: suspend DBCacheProvider<BaseCache>.() -> Unit): Unit = lock.withLock { block(this) }

    /**
     * Executes the [block] within a transaction.
     */
    suspend fun transaction(block: suspend DBCacheProvider<BaseCache>.() -> Unit) {
        transactionStarter.begin()
        block(this)
        transactionFinisher.end()
    }

    /**
     * Executes the [block] within a transaction with the lock.
     */
    suspend fun lockedTransaction(block: suspend DBCacheProvider<BaseCache>.() -> Unit): Unit = withLock {
        transaction(block)
    }

    /**
     * Gets the cache that is of the same type as [SubCache].
     *
     * @throws NoSuchElementException if the cache does not exist
     * @return the cache
     */
    inline fun <reified SubCache : BaseCache> get(): SubCache = caches[SubCache::class] as? SubCache
        ?: throw NoSuchElementException("Unable to find the cache of type ${SubCache::class}.")

    /**
     * Sets the cache with a type of [SubCache].
     */
    inline fun <reified SubCache : BaseCache> set(cache: SubCache) {
        caches[SubCache::class] = cache
    }
}