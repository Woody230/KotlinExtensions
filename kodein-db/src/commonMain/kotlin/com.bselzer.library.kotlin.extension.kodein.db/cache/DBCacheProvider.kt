package com.bselzer.library.kotlin.extension.kodein.db.cache

import com.bselzer.library.kotlin.extension.kodein.db.transaction.DBTransactionManager
import com.bselzer.library.kotlin.extension.kodein.db.transaction.TransactionFinisher
import com.bselzer.library.kotlin.extension.kodein.db.transaction.TransactionStarter
import kotlin.reflect.KClass

/**
 * The caching abstraction for multiple instances.
 *
 * @param transactionStarter the transaction starter
 * @param transactionFinisher the transaction finisher
 * @param BaseCache the type of cache
 * @param Instance the subclass type of cache provider
 */
abstract class DBCacheProvider<BaseCache : DBCache, Instance : DBCacheProvider<BaseCache, Instance>>(
    transactionStarter: TransactionStarter,
    transactionFinisher: TransactionFinisher
) :
    DBTransactionManager<Instance>(transactionStarter, transactionFinisher) {
    /**
     * The underlying caches.
     */
    @PublishedApi
    internal val caches: MutableMap<KClass<out BaseCache>, BaseCache> = mutableMapOf()

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

