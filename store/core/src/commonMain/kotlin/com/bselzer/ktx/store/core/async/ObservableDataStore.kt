package com.bselzer.ktx.store.core.async

import kotlinx.coroutines.flow.Flow
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

interface ObservableDataStore<Key, Model> {
    fun observeKeys(context: CoroutineContext = EmptyCoroutineContext): Flow<Set<Key>>
    fun observeEntries(context: CoroutineContext = EmptyCoroutineContext): Flow<Set<AsyncDataStore.Entry<Key, Model>>>
    fun observeOrNull(key: Key, context: CoroutineContext = EmptyCoroutineContext): Flow<Model?>
    fun observeAll(context: CoroutineContext = EmptyCoroutineContext): Flow<Collection<Model>>
    fun observeExists(key: Key, context: CoroutineContext = EmptyCoroutineContext): Flow<Boolean>
}