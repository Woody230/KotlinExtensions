package com.bselzer.library.kotlin.extension.kodein.db.operation

import com.bselzer.library.kotlin.extension.kodein.db.transaction.DBTransaction
import org.kodein.db.asModelSequence
import org.kodein.db.deleteFrom
import org.kodein.db.find

/**
 * Find models stored in the database.
 *
 * If there are no models in the database, then the [requestAll] block is called.
 *
 * @param requestAll the block for retrieving all of the models
 * @return all the models
 */
inline fun <reified Model : Any> DBTransaction.findAllOnce(requestAll: () -> Collection<Model>): Sequence<Model> = findAllByCount(1, requestAll)

/**
 * Finds models stored in the database.
 *
 * If there are fewer than [minimum] number of models in the database, then the [requestAll] block is called.
 *
 * @param minimum the minimum number of database models required to not call the [requestAll] block
 * @param requestAll the block for retrieving all of the models
 * @return all the models
 */
inline fun <reified Model : Any> DBTransaction.findAllByCount(minimum: Int, requestAll: () -> Collection<Model>): Sequence<Model> {
    var stored = reader.find<Model>().all().asModelSequence()
    if (stored.count() < minimum) {
        // Clear existing entries and then request the current up-to-date models.
        stored.forEach { model -> writer.deleteFrom(model) }

        val requested = requestAll()
        requested.forEach { model -> writer.put(model) }
        stored = requested.asSequence()
    }
    return stored
}