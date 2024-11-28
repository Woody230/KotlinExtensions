package com.bselzer.ktx.store.core.async

interface MutableAsyncDataStore<Key, Model>: AsyncDataStore<Key, Model> {
    suspend fun set(key: Key, model: Model)
    suspend fun remove(key: Key)
    suspend fun removeAll()

    override suspend fun getEntries(): Set<MutableEntry<Key, Model>>

    interface MutableEntry<Key, Model>: AsyncDataStore.Entry<Key, Model> {
        override var model: Model
    }
}