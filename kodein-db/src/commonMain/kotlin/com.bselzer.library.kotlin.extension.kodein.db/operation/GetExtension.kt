package com.bselzer.library.kotlin.extension.kodein.db.operation

import com.bselzer.library.kotlin.extension.kodein.db.transaction.DBTransaction
import org.kodein.db.getById

/**
 * Gets a model from the database by its id.
 *
 * If the model does not exist, then the [requestSingle] block is called followed by the [postRequestSingle] block.
 *
 * @param id the id to search for
 * @param requestSingle the block for retrieving the model
 * @param postRequestSingle the block for resolving a request being made
 * @return the model with the [id]
 */
inline fun <reified Model : Any, Id : Any> DBTransaction.getById(id: Id, requestSingle: () -> Model, postRequestSingle: (Model) -> Unit = {}): Model {
    var model = reader.getById<Model>(id)
    if (model == null) {
        model = requestSingle()
        writer.put(model)
        postRequestSingle(model)
    }
    return model
}