package com.bselzer.ktx.store.core.transaction

interface KeyStoreTransactionResult<Value> {
    val value: Value

    class RolledBack<Value>(override val value: Value): KeyStoreTransactionResult<Value>
    class Committed<Value>(override val value: Value): KeyStoreTransactionResult<Value>
}