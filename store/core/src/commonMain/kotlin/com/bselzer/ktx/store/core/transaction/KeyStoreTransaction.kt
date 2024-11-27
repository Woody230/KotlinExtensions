package com.bselzer.ktx.store.core.transaction

interface KeyStoreTransaction: KeyStoreTransactionCallbacks {
    fun commit()
    fun rollback()
}