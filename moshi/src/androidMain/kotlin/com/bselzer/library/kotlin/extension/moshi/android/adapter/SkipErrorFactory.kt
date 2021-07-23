package com.bselzer.library.kotlin.extension.moshi.android.adapter

import com.squareup.moshi.*
import java.lang.reflect.Type

/**
 * A json adapter factory for skipping errors in order to not completely stop the retrieval of data.
 */
class SkipErrorFactory : JsonAdapter.Factory
{
    override fun create(type: Type, annotations: MutableSet<out Annotation>, moshi: Moshi): JsonAdapter<*>?
    {
        // If there is an annotation, let it be handled by another adapter.
        // Only handle errors for lists. Default to list for collections.
        val rawType = Types.getRawType(type)
        if (annotations.isNotEmpty())
        {
            return null
        }
        else if (rawType != List::class.java && rawType != Collection::class.java)
        {
            return null
        }

        return try
        {
            val valueType = Types.collectionElementType(type, List::class.java)
            return SkipErrorListAdapter<Any>(moshi, valueType).nullSafe()
        }
        catch (ex: IllegalArgumentException)
        {
            // Need this catch to skip generics that won't have an adapter.
            null
        }
    }
}