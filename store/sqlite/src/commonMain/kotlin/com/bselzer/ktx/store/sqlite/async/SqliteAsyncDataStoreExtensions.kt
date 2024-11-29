package com.bselzer.ktx.store.sqlite.async

import com.bselzer.ktx.store.sqlite.context.SqliteDataStoreContext
import com.bselzer.ktx.store.sqlite.db.DataStoreDatabase
import com.bselzer.ktx.store.sqlite.encoding.KeyToStringEncoder
import com.bselzer.ktx.store.sqlite.encoding.ModelToStringEncoder
import com.bselzer.ktx.store.sqlite.db.Model.Tag

fun <Key, Model> SqliteDataStoreContext.asyncDataStore(
    name: String,
    keyEncoder: KeyToStringEncoder<Key>,
    modelEncoder: ModelToStringEncoder<Model>
): SqliteAsyncDataStore<Key, Model> = SqlDelightAsyncDataStore(
    tag = Tag(name),
    keyEncoder = keyEncoder,
    modelEncoder = modelEncoder,
    queries = DataStoreDatabase(
        driver = platform.createDriver(DataStoreDatabase.Schema, file)
    ).modelQueries
)