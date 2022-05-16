package com.bselzer.ktx.kodein.db.transaction

import org.kodein.db.Batch
import org.kodein.db.DB
import org.kodein.db.DBRead

/**
 * Represents an abstraction for writing a batch.
 *
 * @param reader the reader
 * @param writer the batch writer
 */
class DBTransaction(
    override val reader: DBRead,
    override val writer: Batch
) : Transaction {
    /**
     * Initializes a new instance of the [DBTransaction] class with the [database] as the [reader] and a new batch as the [writer].
     */
    constructor(database: DB) : this(database, database.newBatch())
}

/**
 * Creates a new [DBTransaction] for the given database.
 */
fun DB.transaction() = DBTransaction(this)