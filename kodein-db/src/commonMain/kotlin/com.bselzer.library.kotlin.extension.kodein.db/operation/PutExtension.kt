package com.bselzer.library.kotlin.extension.kodein.db.operation

import com.bselzer.library.kotlin.extension.kodein.db.transaction.DBTransaction
import org.kodein.db.getById

/**
 * Finds missing models based on their id and puts them in the database.
 *
 * @param requestIds a block for retrieving all of the ids
 * @param requestById a block for mapping ids to their associated models
 */
inline fun <reified Model : Any, Id : Any> DBTransaction.putMissingById(requestIds: () -> Collection<Id>, requestById: (Collection<Id>) -> Collection<Model>) {
    val allIds = requestIds()
    val missingIds = allIds.filter { id -> reader.getById<Model>(id) == null }
    requestById(missingIds).forEach { model -> writer.put(model) }
}

/**
 * Finds missing models based on their id and puts them in the database.
 * If a model is missing from the [requestById] result, then the [default] block is called.
 *
 * @param requestIds a block for retrieving all of the ids
 * @param requestById a block for mapping ids to their associated models
 * @param getId a block for mapping models to their associated ids
 * @param default a block for mapping ids to their default models
 */
inline fun <reified Model : Any, Id : Any> DBTransaction.putMissingById(
    requestIds: () -> Collection<Id>,
    requestById: (Collection<Id>) -> Collection<Model>,
    getId: (Model) -> Id,
    default: (Id) -> Model
) {
    val allIds = requestIds().toHashSet()
    val missingIds = allIds.filter { id -> reader.getById<Model>(id) == null }

    val newModels = requestById(missingIds)
    newModels.forEach { model -> writer.put(model) }

    // Add the models for any ids that are still missing by using the default.
    val defaultModels = allIds.minus(newModels.map { model -> getId(model) }).map { id -> default(id) }
    defaultModels.forEach { model -> writer.put(model) }
}