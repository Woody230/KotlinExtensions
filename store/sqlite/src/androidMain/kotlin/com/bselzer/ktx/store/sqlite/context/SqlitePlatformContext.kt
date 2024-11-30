package com.bselzer.ktx.store.sqlite.context

import android.content.Context
import app.cash.sqldelight.async.coroutines.synchronous
import app.cash.sqldelight.db.QueryResult
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.db.SqlSchema
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import java.io.File

actual class SqlitePlatformContext(
    private val context: Context
) {
    internal actual fun createDriver(schema: SqlSchema<QueryResult.AsyncValue<Unit>>, file: SqliteFileContext): SqlDriver {
        val path = File(file.directory, file.name).absolutePath
        return AndroidSqliteDriver(schema.synchronous(), context, name = path)
    }

    internal actual fun createInMemoryDriver(schema: SqlSchema<QueryResult.AsyncValue<Unit>>): SqlDriver {
       return AndroidSqliteDriver(schema.synchronous(), context, name = null)
    }
}