package com.bselzer.library.kotlin.extension.kodein.db.operation

import com.bselzer.library.kotlin.extension.kodein.db.transaction.TransactionManager
import org.kodein.db.getById

/**
 * Gets a model from the database by its id.
 *
 * If the model does not exist, then the [requestSingle] block is called.
 *
 * @param id the id to search for
 * @param requestSingle the block for retrieving the model
 * @return the model with the [id]
 */
suspend inline fun <reified Model : Any, Id : Any> TransactionManager.getById(id: Id, crossinline requestSingle: suspend () -> Model): Model = transaction {
    var model = reader.getById<Model>(id)
    if (model == null) {
        model = requestSingle()
        writer.put(model)
    }
    model
}