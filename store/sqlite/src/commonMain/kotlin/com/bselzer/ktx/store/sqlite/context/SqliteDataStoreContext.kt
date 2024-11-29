package com.bselzer.ktx.store.sqlite.context

interface SqliteDataStoreContext {
    val file: SqliteFileContext
    val platform: SqlitePlatformContext
}