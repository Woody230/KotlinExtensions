package com.bselzer.library.kotlin.extension.moshi.android.adapter

import com.squareup.moshi.*
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

/**
 * The factory for creating map array adapters.
 */
class MapArrayFactory : JsonAdapter.Factory
{
    override fun create(type: Type, annotations: MutableSet<out Annotation>, moshi: Moshi): JsonAdapter<*>?
    {
        // Only handle creating adapters for maps with the map array annotation.
        if (!annotations.any { annotation -> annotation.annotationClass == MapArrayAdapter.MapArray::class } || Types.getRawType(type) != Map::class.java)
        {
            return null
        }

        val typeArguments = (type as ParameterizedType).actualTypeArguments
        return MapArrayAdapter<Any, Any>(moshi, typeArguments[0], typeArguments[1]).nullSafe()
    }
}