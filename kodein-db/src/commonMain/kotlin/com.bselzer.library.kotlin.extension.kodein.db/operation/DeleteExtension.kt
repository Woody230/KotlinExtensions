package com.bselzer.library.kotlin.extension.kodein.db.operation

import com.bselzer.library.kotlin.extension.kodein.db.transaction.TransactionManager
import org.kodein.db.deleteAll
import org.kodein.db.find

/**
 * Deletes all of the [Model] from the database.
 */
suspend inline fun <reified Model : Any> TransactionManager.clear() = transaction {
    writer.deleteAll(reader.find<Model>().all())
}