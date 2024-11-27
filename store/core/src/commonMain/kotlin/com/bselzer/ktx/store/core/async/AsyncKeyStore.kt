package com.bselzer.ktx.store.core.async

interface AsyncKeyStore<Key, Model> {
    suspend fun getKeys(): Set<Key>
    suspend fun getEntries(): Set<Entry<Key, Model>>
    suspend fun getAll(): Collection<Model>
    suspend fun getOrNull(key: Key): Model?
    suspend fun exists(key: Key): Boolean

    interface Entry<out Key, out Model> {
        val key: Key
        val model: Model
    }
}