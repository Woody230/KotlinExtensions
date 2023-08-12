package com.bselzer.ktx.db.transaction

import org.kodein.db.*
import org.kodein.memory.use
import kotlin.reflect.KClass

/**
 * Represents an abstraction for writing a batch.
 *
 * @param reader the reader
 * @param writer the batch writer
 */
class DBTransaction(
    private val reader: DBRead,
    private val writer: Batch
) : Transaction, DBRead by reader, Batch by writer {
    /**
     * Initializes a new instance of the [DBTransaction] class with the [database] as the [reader] and a new batch as the [writer].
     */
    constructor(database: DB) : this(database, database.newBatch())

    /**
     * Executes the [block], commits the changes, and closes the writer.
     */
    inline fun <R> use(vararg options: Options.BatchWrite, block: Transaction.() -> R): R = (this as Batch).use {
        val result = block(this)
        write(*options)
        result
    }

    // Both DBRead and DBWrite are KeyMaker.
    override fun <M : Any> keyById(type: KClass<M>, vararg id: Any): Key<M> = reader.keyById(type, *id)
    override fun <M : Any> keyFrom(model: M, vararg options: Options.Puts): Key<M> = reader.keyFrom(model, *options)
    override fun <M : Any> keyFromB64(type: KClass<M>, b64: String): Key<M> = reader.keyFromB64(type, b64)
}

/**
 * Creates a new [DBTransaction] for the given database.
 */
fun DB.transaction() = DBTransaction(this)