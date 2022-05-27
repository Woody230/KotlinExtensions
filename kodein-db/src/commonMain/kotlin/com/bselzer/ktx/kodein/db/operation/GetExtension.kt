package com.bselzer.ktx.kodein.db.operation

import com.bselzer.ktx.kodein.db.transaction.Transaction
import org.kodein.db.getById

/**
 * Gets a model from the database by its id.
 *
 * If the model does not exist, then the [requestSingle] block is called and is written to the database if the [writeFilter] returns true.
 *
 * @param id the id to search for
 * @param requestSingle the block for retrieving the model
 * @param writeFilter the block for determining whether to write the [requestSingle] model to the database. Returning true writes the model.
 * @return the model with the [id]
 */
suspend inline fun <reified Model : Any, Id : Any> Transaction.getById(
    id: Id,
    crossinline requestSingle: suspend () -> Model,
    crossinline writeFilter: (Model) -> Boolean = { true }
): Model {
    var model = getById<Model>(id)
    if (model == null) {
        model = requestSingle()
        if (writeFilter(model)) {
            put(model)
        }
    }

    return model
}