package com.bselzer.ktx.db.operation

import com.bselzer.ktx.db.transaction.Transaction
import org.kodein.db.deleteAll
import org.kodein.db.find

/**
 * Deletes all of the [Model] from the database.
 */
inline fun <reified Model : Any> Transaction.clear() = deleteAll(find<Model>().all())