package com.bselzer.ktx.kodein.db.operation

import com.bselzer.ktx.kodein.db.transaction.Transaction
import com.bselzer.ktx.value.identifier.Identifiable
import com.bselzer.ktx.value.identifier.Identifier
import org.kodein.db.getById
import kotlin.jvm.JvmName

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
    if (missingIds.isNotEmpty()) {
        requestById(missingIds).forEach { model -> put(model) }
    }
}

/**
 * Finds missing models based on their id and puts them in the database.
 *
 * Note that the batch **MUST** be written before you attempt to find the models.
 * Consequently, the result of this method should be used instead if you do not want to immediately commit.
 *
 * @param requestIds a block for retrieving all of the ids
 * @param requestById a block for mapping ids to their associated models
 * @return the ids and models that already existed or retrieved by [requestById]
 */
@Suppress("UNCHECKED_CAST")
@JvmName("putMissingIdentifiableById")
suspend inline fun <reified Model : Identifiable<Id, Value>, Id : Identifier<Value>, Value> Transaction.putMissingById(
    crossinline requestIds: suspend () -> Collection<Id>,
    crossinline requestById: suspend (Collection<Id>) -> Collection<Model>
): Map<Id, Model> {
    val allIds = requestIds().toHashSet()
    val models = allIds.associateWith { id -> getById<Model>(id) }.toMutableMap()

    val missingIds = allIds.filter { id -> getById<Model>(id) == null }
    if (missingIds.isNotEmpty()) {
        requestById(missingIds).forEach { model ->
            models[model.id] = model
            put(model)
        }
    }

    // Ensure all non-existent models are purged before casting.
    return models.filterValues { value -> value != null } as Map<Id, Model>
}