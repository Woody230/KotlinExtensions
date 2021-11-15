package com.bselzer.library.kotlin.extension.kodein.db.transaction

import org.kodein.db.DB
import org.kodein.db.Options

/**
 * Creates a transaction, executes the [block], and then commits the changes.
 *
 * @param block the changes to perform
 * @param R the result of the transaction
 * @return the result of the block
 */
suspend fun <R> DB.transaction(vararg options: Options.BatchWrite, block: suspend DBTransaction.() -> R): R = DBTransaction(this).use(*options, block = block)