package com.bselzer.ktx.openapi.client.model.property

import com.bselzer.ktx.codegen.model.property.CopyableProperty
import com.bselzer.ktx.openapi.model.reference.ReferenceOfOpenApiSchema

sealed class NestedPropertyResolver : PropertyResolver {
    internal fun PropertyResolver.nestedProperty(nestedSchemaReference: ReferenceOfOpenApiSchema, input: PropertyInput): CopyableProperty {
        val nestedSchema = nestedSchemaReference.resolve(
            onValue = { nestedSchema -> nestedSchema },
            onReference = { reference -> input.references[reference.`$ref`.componentName] }
        )
        requireNotNull(nestedSchema) { "Unable to resolve the reference to the nested schema." }

        val nestedInput = input.copy(schema = nestedSchema)
        return resolve(nestedInput)
    }
}