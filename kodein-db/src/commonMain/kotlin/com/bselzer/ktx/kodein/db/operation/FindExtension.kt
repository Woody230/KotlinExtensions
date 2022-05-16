package com.bselzer.ktx.kodein.db.operation

import com.bselzer.ktx.kodein.db.transaction.Transaction
import org.kodein.db.deleteFrom
import org.kodein.db.find
import org.kodein.db.useModels

/**
 * Find models stored in the database.
 *
 * If there are no models in the database, then the [requestAll] block is called.
 *
 * @param requestAll the block for retrieving all of the models
 * @return all the models
 */
suspend inline fun <reified Model : Any> Transaction.findAllOnce(crossinline requestAll: suspend () -> Collection<Model>): Collection<Model> =
    findAllByCount(1, requestAll)

/**
 * Finds models stored in the database.
 *
 * If there are fewer than [minimum] number of models in the database, then the [requestAll] block is called.
 *
 * @param minimum the minimum number of database models required to not call the [requestAll] block
 * @param requestAll the block for retrieving all of the models
 * @return all the models
 */
suspend inline fun <reified Model : Any> Transaction.findAllByCount(minimum: Int, crossinline requestAll: suspend () -> Collection<Model>): Collection<Model> {
    var stored: Collection<Model> = reader.find<Model>().all().useModels { it.toList() }
    if (stored.count() < minimum) {
        // Clear existing entries and then request the current up-to-date models.
        stored.forEach { model -> writer.deleteFrom(model) }

        val requested = requestAll()
        requested.forEach { model -> writer.put(model) }
        stored = requested
    }

    return stored
}