package com.bselzer.library.kotlin.extension.kodein.db.transaction

import org.kodein.db.Batch
import org.kodein.db.DB
import org.kodein.db.DBRead
import org.kodein.db.Options
import org.kodein.memory.Closeable
import org.kodein.memory.use

/**
 * Represents an abstraction for writing a batch.
 *
 * @param reader the reader
 * @param writer the batch writer
 */
data class DBTransaction(
    val reader: DBRead,
    val writer: Batch
) : Closeable {
    /**
     * Initializes a new instance of the [DBTransaction] class with the [database] as the [reader] and a new batch as the [writer].
     */
    constructor(database: DB) : this(database, database.newBatch())

    /**
     * Executes the [block], commits the changes, and closes the writer.
     */
    suspend fun <R> use(vararg options: Options.BatchWrite, block: suspend DBTransaction.() -> R): R = writer.use {
        val result = block(this)
        writer.write(*options)
        result
    }

    override fun close() = writer.close()
}