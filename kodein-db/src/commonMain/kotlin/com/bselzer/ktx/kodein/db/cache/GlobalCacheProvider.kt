package com.bselzer.ktx.kodein.db.cache

import org.kodein.db.DB

/**
 * A caching abstraction designed to provide any type of [DBCache].
 */
class GlobalCacheProvider(database: DB) : DBCacheProvider<DBCache, GlobalCacheProvider>(database) {
    override val instance: GlobalCacheProvider = this

    /**
     * Injects the caches from another provider into this provider.
     */
    fun inject(provider: DBCacheProvider<DBCache, *>) = provider.caches.values.forEach { set(it) }
}