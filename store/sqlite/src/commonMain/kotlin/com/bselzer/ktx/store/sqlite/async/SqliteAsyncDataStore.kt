package com.bselzer.ktx.store.sqlite.async

import com.bselzer.ktx.store.core.async.MutableAsyncDataStore
import com.bselzer.ktx.store.core.async.ObservableDataStore

sealed interface SqliteAsyncDataStore<Key, Model>: MutableAsyncDataStore<Key, Model>, ObservableDataStore<Key, Model>