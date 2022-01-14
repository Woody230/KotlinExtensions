package com.bselzer.ktx.kodein.db.cache

import com.bselzer.ktx.kodein.db.transaction.DBTransaction
import com.bselzer.ktx.kodein.db.transaction.DBTransactionManager
import org.kodein.db.DB
import kotlin.reflect.KClass

/**
 * The caching abstraction for multiple instances.
 *
 * @param database the database
 * @param BaseCache the type of cache
 * @param Instance the subclass type of cache provider
 */
abstract class DBCacheProvider<BaseCache : DBCache, Instance : DBCacheProvider<BaseCache, Instance>>(database: DB) : DBTransactionManager(database) {
    /**
     * The subclass instance.
     */
    protected abstract val instance: Instance

    /**
     * The underlying caches.
     */
    @PublishedApi
    internal val caches: MutableMap<KClass<out BaseCache>, BaseCache> = mutableMapOf()

    /**
     * Executes the [block] within a transaction and returns its result.
     *
     * @param R the type of result
     * @return the result of the [block]
     */
    suspend fun <R> instance(block: suspend Instance.(DBTransaction) -> R): R = transaction {
        block(instance, this)
    }

    /**
     * Executes the [block] within a transaction with the lock and returns its result.
     *
     * @param R the type of result
     * @return the result of the [block]
     */
    suspend fun <R> lockedInstance(block: suspend Instance.(DBTransaction) -> R): R = lockedTransaction { block(instance, this) }

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

