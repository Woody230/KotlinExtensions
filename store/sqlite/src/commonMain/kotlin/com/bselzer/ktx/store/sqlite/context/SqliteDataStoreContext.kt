package com.bselzer.ktx.store.sqlite.context

class SqliteDataStoreContext internal constructor(
    internal val file: SqliteFileContext,
    internal val platform: SqlitePlatformContext
)