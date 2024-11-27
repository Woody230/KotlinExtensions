package com.bselzer.ktx.store.core.async

interface MutableAsyncKeyStore<Key, Model>: AsyncKeyStore<Key, Model> {
    suspend fun set(key: Key, model: Model)
    suspend fun remove(key: Key)
    suspend fun removeAll()

    override suspend fun getEntries(): Set<MutableEntry<Key, Model>>

    interface MutableEntry<Key, Model>: AsyncKeyStore.Entry<Key, Model> {
        override var model: Model
    }
}