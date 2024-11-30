package com.bselzer.ktx.store.sqlite.context

import app.cash.sqldelight.db.QueryResult
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.db.SqlSchema

expect class SqlitePlatformContext {
    internal fun createDriver(schema: SqlSchema<QueryResult.AsyncValue<Unit>>, file: SqliteFileContext): SqlDriver
    internal fun createInMemoryDriver(schema: SqlSchema<QueryResult.AsyncValue<Unit>>): SqlDriver
}