package com.bselzer.library.kotlin.extension.kodein.db.operation

import com.bselzer.library.kotlin.extension.kodein.db.transaction.DBTransaction
import org.kodein.db.deleteAll
import org.kodein.db.find

/**
 * Deletes all of the [Model] from the database.
 */
inline fun <reified Model : Any> DBTransaction.clear() = writer.deleteAll(reader.find<Model>().all())