package com.bselzer.ktx.store.sqlite.async

import com.bselzer.ktx.store.core.async.MutableAsyncDataStore
import com.bselzer.ktx.store.core.async.MutableAsyncDataStore.MutableEntry
import com.bselzer.ktx.store.sqlite.Model.Tag
import com.bselzer.ktx.store.sqlite.ModelQueries

class SqliteMutableAsyncDataStore<Key, Model> internal constructor(
    private val tag: Tag,
    private val keyEncoder: KeyEncoder<Key>,
    private val modelEncoder: ModelEncoder<Model>,
    private val queries: ModelQueries
): MutableAsyncDataStore<Key, Model> {
    override suspend fun getKeys(): Set<Key> {
        return queries.selectKeysByTag(tag).executeAsList().map(keyEncoder::decode).toSet()
    }

    override suspend fun getEntries(): Set<MutableEntry<Key, Model>> {
        val entries = queries.selectByTag(tag).executeAsList().map { dbModel ->
            SqliteEntry(
                key = keyEncoder.decode(dbModel.Key),
                model = modelEncoder.decode(dbModel.Content)
            )
        }

        return entries.toSet()
    }

    override suspend fun getAll(): Collection<Model> {
        return queries.selectByTag(tag).executeAsList().map { dbModel -> modelEncoder.decode(dbModel.Content) }
    }

    override suspend fun exists(key: Key): Boolean {
        return queries.selectByTagAndKey(tag, keyEncoder.encode(key)).executeAsOneOrNull() != null
    }

    override suspend fun getOrNull(key: Key): Model? {
        val content = queries.selectByTagAndKey(tag, keyEncoder.encode(key)).executeAsOneOrNull()?.Content ?: return null
        return modelEncoder.decode(content)
    }

    override suspend fun set(key: Key, model: Model) {
        queries.insertOrReplace(com.bselzer.ktx.store.sqlite.Model(
            Key = keyEncoder.encode(key),
            Content = modelEncoder.encode(model),
            Tag = tag
        ))
    }

    override suspend fun remove(key: Key) {
        queries.deleteByTagAndKey(tag, keyEncoder.encode(key))
    }

    override suspend fun removeAll() {
        queries.deleteByTag(tag)
    }

    private data class SqliteEntry<Key, Model>(
        override val key: Key,
        override var model: Model
    ) : MutableEntry<Key, Model>
}