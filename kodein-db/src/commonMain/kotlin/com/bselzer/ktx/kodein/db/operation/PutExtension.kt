package com.bselzer.ktx.kodein.db.operation

import com.bselzer.ktx.kodein.db.transaction.Transaction
import org.kodein.db.getById

/**
 * Finds missing models based on their id and puts them in the database.
 *
 * Note that the batch **MUST** be written before you attempt to find the models.
 *
 * @param requestIds a block for retrieving all of the ids
 * @param requestById a block for mapping ids to their associated models
 */
suspend inline fun <reified Model : Any, Id : Any> Transaction.putMissingById(
    crossinline requestIds: suspend () -> Collection<Id>,
    crossinline requestById: suspend (Collection<Id>) -> Collection<Model>
) {
    val allIds = requestIds().toHashSet()
    val missingIds = allIds.filter { id -> getById<Model>(id) == null }
    requestById(missingIds).forEach { model -> put(model) }
}