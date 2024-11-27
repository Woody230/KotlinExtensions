package com.bselzer.ktx.store.core.transaction

interface KeyStoreTransactionCallbacks {
    fun afterCommit(block: () -> Unit)
    fun afterRollback(block: () -> Unit)
}