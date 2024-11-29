package com.bselzer.ktx.store.sqlite.context

interface SqliteDataStoreContext {
    val database: SqliteDatabaseContext
    val file: SqliteFileContext
}