package com.bselzer.ktx.store.sqlite.async

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import app.cash.sqldelight.coroutines.mapToOneOrNull
import com.bselzer.ktx.store.core.async.AsyncDataStore
import com.bselzer.ktx.store.core.async.MutableAsyncDataStore
import com.bselzer.ktx.store.core.async.MutableAsyncDataStore.MutableEntry
import com.bselzer.ktx.store.core.async.ObservableDataStore
import com.bselzer.ktx.store.sqlite.db.Model as DbModel
import com.bselzer.ktx.store.sqlite.db.Model.Tag
import com.bselzer.ktx.store.sqlite.db.ModelQueries
import com.bselzer.ktx.store.sqlite.encoding.KeyToStringEncoder
import com.bselzer.ktx.store.sqlite.encoding.ModelToStringEncoder
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlin.coroutines.CoroutineContext

class SqliteDataStore<Key, Model> internal constructor(
    private val tag: Tag,
    private val keyEncoder: KeyToStringEncoder<Key>,
    private val modelEncoder: ModelToStringEncoder<Model>,
    private val queries: ModelQueries
): MutableAsyncDataStore<Key, Model>, ObservableDataStore<Key, Model> {
    override suspend fun getKeys(): Set<Key> {
        return queries.selectKeysByTag(tag).executeAsList().mapKeys()
    }

    override suspend fun getEntries(): Set<MutableEntry<Key, Model>> {
        return queries.selectByTag(tag).executeAsList().mapEntries()
    }

    override suspend fun getAll(): Collection<Model> {
        return queries.selectByTag(tag).executeAsList().mapModels()
    }

    override suspend fun exists(key: Key): Boolean {
        return queries.selectByTagAndKey(tag, keyEncoder.encode(key)).executeAsOneOrNull() != null
    }

    override suspend fun getOrNull(key: Key): Model? {
        return queries.selectByTagAndKey(tag, keyEncoder.encode(key)).executeAsOneOrNull().toModelOrNull()
    }

    override suspend fun set(key: Key, model: Model) {
        queries.insertOrReplace(
            DbModel(
                Key = keyEncoder.encode(key),
                Content = modelEncoder.encode(model),
                Tag = tag
            )
        )
    }

    override suspend fun remove(key: Key) {
        queries.deleteByTagAndKey(tag, keyEncoder.encode(key))
    }

    override suspend fun removeAll() {
        queries.deleteByTag(tag)
    }

    override fun observeKeys(context: CoroutineContext): Flow<Set<Key>> {
        return queries.selectKeysByTag(tag).asFlow().mapToList(context).map { keys -> keys.mapKeys() }
    }

    override fun observeEntries(context: CoroutineContext): Flow<Set<AsyncDataStore.Entry<Key, Model>>> {
        return queries.selectByTag(tag).asFlow().mapToList(context).map { dbModels -> dbModels.mapEntries() }
    }

    override fun observeAll(context: CoroutineContext): Flow<Collection<Model>> {
        return queries.selectByTag(tag).asFlow().mapToList(context).map { dbModels -> dbModels.mapModels() }
    }

    override fun observeExists(key: Key, context: CoroutineContext): Flow<Boolean> {
        return queries.selectByTagAndKey(tag, keyEncoder.encode(key)).asFlow().mapToOneOrNull(context).map { dbModel -> dbModel != null }
    }

    override fun observeOrNull(key: Key, context: CoroutineContext): Flow<Model?> {
        return queries.selectByTagAndKey(tag, keyEncoder.encode(key)).asFlow().mapToOneOrNull(context).map { dbModel -> dbModel.toModelOrNull() }
    }

    private fun List<String>.mapKeys() = map(keyEncoder::decode).toSet()

    private fun List<DbModel>.mapEntries(): Set<SqliteEntry<Key, Model>> {
        val entries = map { dbModel ->
            SqliteEntry(
                key = keyEncoder.decode(dbModel.Key),
                model = modelEncoder.decode(dbModel.Content)
            )
        }

        return entries.toSet()
    }

    private fun List<DbModel>.mapModels() = map { dbModel -> modelEncoder.decode(dbModel.Content) }

    private fun DbModel?.toModelOrNull(): Model? {
        val content = this?.Content ?: return null
        return modelEncoder.decode(content)
    }

    private data class SqliteEntry<Key, Model>(
        override val key: Key,
        override var model: Model
    ) : MutableEntry<Key, Model>
}