package com.bselzer.ktx.store.sqlite.context

import app.cash.sqldelight.db.QueryResult
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.db.SqlSchema
import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver
import java.io.File

actual class SqlitePlatformContext {
    internal actual fun createDriver(schema: SqlSchema<QueryResult.Value<Unit>>, file: SqliteFileContext): SqlDriver {
        val path = File(file.directory, file.name).absolutePath
        return JdbcSqliteDriver("jdbc:sqlite:${path}")
    }
}