package com.bselzer.ktx.db.operation

import com.bselzer.ktx.db.transaction.Transaction
import com.bselzer.ktx.value.identifier.Identifiable
import com.bselzer.ktx.value.identifier.Identifier
import org.kodein.db.DBRead
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
    var stored: Collection<Model> = find<Model>().all().useModels { it.toList() }
    if (stored.count() < minimum) {
        // Clear existing entries and then request the current up-to-date models.
        stored.forEach { model -> deleteFrom(model) }

        val requested = requestAll()
        requested.forEach { model -> put(model) }
        stored = requested
    }

    return stored
}

/**
 * Finds the [Reference] models from the database based on an id found on an [Origin] model.
 *
 * @param origin the models to get an id from
 * @param getId the function for mapping an [Origin] model to a [Reference] [Value]
 * @param Origin the type of the model with the reference id
 * @param Id the type of the id of the [Reference] model
 * @param Reference the type of the model to retrieve
 */
inline fun <Origin, Value, Id : Identifier<Value>, reified Reference : Identifiable<Id, Value>> DBRead.findByReferenceId(
    origin: Origin,
    crossinline getId: Origin.() -> Id
): Collection<Reference> = findByReferenceId(listOf(origin), getId)

/**
 * Finds the [Reference] models from the database based on an id found on an [Origin] model.
 *
 * @param origin the models to get an id from
 * @param getId the function for mapping an [Origin] model to a [Reference] [Value]
 * @param Origin the type of the model with the reference id
 * @param Id the type of the id of the [Reference] model
 * @param Reference the type of the model to retrieve
 */
inline fun <Origin, Value, Id : Identifier<Value>, reified Reference : Identifiable<Id, Value>> DBRead.findByReferenceId(
    origin: Collection<Origin>,
    crossinline getId: Origin.() -> Id
): Collection<Reference> {
    val ids = origin.map(getId)
    return findByIds(ids)
}

/**
 * Finds the [Reference] models from the database based on ids found on an [Origin] model.
 *
 * @param origin the models to get ids from
 * @param getIds the function for mapping an [Origin] model to one or more [Reference] [Value]s
 * @param Origin the type of the model with the reference ids
 * @param Id the type of the id of the [Reference] model
 * @param Reference the type of the model to retrieve
 */
inline fun <Origin, Value, Id : Identifier<Value>, reified Reference : Identifiable<Id, Value>> DBRead.findByReferenceIds(
    origin: Origin,
    crossinline getIds: Origin.() -> Collection<Id>
): Collection<Reference> = findByReferenceIds(listOf(origin), getIds)

/**
 * Finds the [Reference] models from the database based on ids found on an [Origin] model.
 *
 * @param origin the models to get ids from
 * @param getIds the function for mapping an [Origin] model to one or more [Reference] [Value]s
 * @param Origin the type of the model with the reference ids
 * @param Id the type of the id of the [Reference] model
 * @param Reference the type of the model to retrieve
 */
inline fun <Origin, Value, Id : Identifier<Value>, reified Reference : Identifiable<Id, Value>> DBRead.findByReferenceIds(
    origin: Collection<Origin>,
    crossinline getIds: Origin.() -> Collection<Id>
): Collection<Reference> {
    val ids = origin.flatMap(getIds)
    return findByIds(ids)
}

/**
 * Finds the [Model]s from the database with an id matching one of the [ids].
 *
 * @param ids the ids
 * @param Id the type of the id of the [Model]
 * @param Model the type of the model to retrieve
 */
inline fun <Value, Id : Identifier<Value>, reified Model : Identifiable<Id, Value>> DBRead.findByIds(
    ids: Collection<Id>,
): Collection<Model> = find<Model>().all().useModels { models ->
    models.filter { model -> ids.contains(model.id) }.toList()
}
