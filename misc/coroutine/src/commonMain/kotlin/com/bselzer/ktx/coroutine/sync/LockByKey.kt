package com.bselzer.ktx.coroutine.sync

import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

class LockByKey<Key> {
    /**
     * The master mutex handling the creation or fetching of locks associated with the [Key]s.
     */
    @PublishedApi
    internal val master: Mutex = Mutex()

    @PublishedApi
    internal val mapped = mutableMapOf<Key, Mutex>()

    /**
     * Executes the given [action] under a mutex's lock that is associated with the [key].
     *
     * @param key the key to find a lock for
     *
     * @param owner Optional owner token for debugging. When `owner` is specified (non-null value) and this mutex
     *        is already locked with the same token (same identity), this function throws [IllegalStateException].
     *
     * @param action the action to perform
     *
     * @return the return value of the action.
     */
    suspend inline fun <T> withLock(key: Key, owner: Any? = null, action: () -> T): T {
        val lock = master.withLock {
            mapped.getOrPut(key) { Mutex() }
        }

        return lock.withLock(owner, action)
    }
}