package com.bselzer.ktx.kodein.db.transaction

import org.kodein.db.Batch
import org.kodein.db.DBRead
import org.kodein.db.Options
import org.kodein.memory.Closeable
import org.kodein.memory.use

/**
 * Represents an abstraction for writing a batch.
 */
interface Transaction : Closeable {
    /**
     * The reader into the database.
     */
    val reader: DBRead

    /**
     * The batch to write the transaction with.
     */
    val writer: Batch

    /**
     * Executes the [block], commits the changes, and closes the writer.
     */
    suspend fun <R> use(vararg options: Options.BatchWrite, block: suspend Transaction.() -> R): R = writer.use {
        val result = block(this)
        writer.write(*options)
        result
    }

    override fun close() = writer.close()
}