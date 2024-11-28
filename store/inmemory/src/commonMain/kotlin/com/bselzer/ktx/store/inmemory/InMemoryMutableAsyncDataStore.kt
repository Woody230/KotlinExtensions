package com.bselzer.ktx.store.inmemory

import com.bselzer.ktx.store.core.async.MutableAsyncDataStore
import com.bselzer.ktx.store.core.async.MutableAsyncDataStore.MutableEntry as MutableEntry

class InMemoryMutableAsyncDataStore<Key, Model>: MutableAsyncDataStore<Key, Model> {
    private val map = mutableMapOf<Key, Model>()

    override suspend fun getKeys(): Set<Key> = map.keys

    override suspend fun getEntries(): Set<MutableEntry<Key, Model>> {
        val set = mutableSetOf<MutableEntry<Key, Model>>()
        for (mapEntry in map.entries) {
            val storeEntry = InMemoryEntry(mapEntry.key, mapEntry.value)
            set.add(storeEntry)
        }

        return set
    }

    override suspend fun getAll(): Collection<Model> = map.values

    override suspend fun exists(key: Key): Boolean = map.containsKey(key)

    override suspend fun getOrNull(key: Key): Model? = map[key]

    override suspend fun set(key: Key, model: Model) {
        map[key] = model
    }

    override suspend fun remove(key: Key) {
        map.remove(key)
    }

    override suspend fun removeAll() {
        map.clear()
    }

    private data class InMemoryEntry<Key, Model>(
        override val key: Key,
        override var model: Model
    ) : MutableEntry<Key, Model>
}